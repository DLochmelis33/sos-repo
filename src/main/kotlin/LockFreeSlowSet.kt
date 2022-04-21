import java.util.concurrent.atomic.AtomicReference

// set based on a lock-free list
interface LockFreeSlowSet<T> : Iterable<T> {
    fun lookup(t: T): Boolean
    fun add(t: T): Boolean
    fun remove(t: T): Boolean
}

fun <T> LockFreeSlowSet(): LockFreeSlowSet<T> = LockFreeSlowSetImpl()

// --------- implementation ----------

private val unreachable: Nothing
    get() = throw IllegalStateException("reached unreachable")

private class LockFreeSlowSetImpl<T> : LockFreeSlowSet<T> {

    private inner class Node(val t: T?, nodeInfo: NodeInfo) {
        val infoRef = AtomicReference(nodeInfo)
    }

    private inner class NodeInfo(
        val next: Node?,
        val valid: Boolean = true
    ) {
        operator fun component1() = next
        operator fun component2() = valid
    }

    private val tail = Node(null, NodeInfo(null))
    private val head = Node(null, NodeInfo(tail))

    private fun lookupInternal(t: T): Pair<Node, Node> {
        retry@ while (true) {
            var pred: Node = head
            var curr: Node = head.infoRef.get().next ?: unreachable
            do {
                val predInfo = pred.infoRef.get()
                var (succ, valid) = curr.infoRef.get()
                while (!valid) {
                    if (!pred.infoRef.compareAndSet(predInfo, NodeInfo(succ, true))) continue@retry
                    curr = succ ?: unreachable
                    val currInfo = curr.infoRef.get()
                    succ = currInfo.next
                    valid = currInfo.valid
                }
                if (curr.t == t || curr.t == null) return Pair(pred, curr)
                pred = curr
                curr = succ ?: unreachable
            } while (true)
        }
    }

    override fun lookup(t: T): Boolean {
        val (_, curr) = lookupInternal(t)
        return curr.infoRef.get().valid && curr.t == t
    }

    override fun add(t: T): Boolean {
        do {
            val (pred, curr) = lookupInternal(t)
            if (curr.t == t && curr.infoRef.get().valid) return false
            val predInfo = pred.infoRef.get()
            val new = Node(t, NodeInfo(curr))
            if (pred.infoRef.compareAndSet(predInfo, NodeInfo(new))) return true
        } while (true)
    }

    override fun remove(t: T): Boolean {
        do {
            val (pred, curr) = lookupInternal(t)
            if (curr.t != t || !curr.infoRef.get().valid) return false
            val currInfo = curr.infoRef.get()
            val predInfo = pred.infoRef.get()
            if (!curr.infoRef.compareAndSet(currInfo, NodeInfo(currInfo.next, false))) continue
            pred.infoRef.compareAndSet(predInfo, NodeInfo(currInfo.next))
            return true
        } while (true)
    }

    override fun iterator(): Iterator<T> {
        TODO("Not yet implemented")
    }

}
