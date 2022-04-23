import kotlin.math.abs

// uses LockFreeSlowSet with sharding
class SetImplKt<T : Comparable<T>>(capacity: Int = 1000) : Set<T> {

    private val sets = Array<LockFreeSlowSet<T>>(capacity) { LockFreeSlowSet() }

    private fun hashIndex(t: T) = abs(t.hashCode() % sets.size)

    override fun add(value: T): Boolean = sets[hashIndex(value)].add(value)

    override fun remove(value: T): Boolean = sets[hashIndex(value)].remove(value)

    override fun contains(value: T): Boolean = sets[hashIndex(value)].contains(value)

    override fun isEmpty(): Boolean = !iterator().hasNext()

    override fun iterator(): Iterator<T> {
        fun globalVersionedList(): List<Pair<T, Long>> = sets.flatMap {
            object : Iterable<Pair<T, Long>> {
                override fun iterator(): Iterator<Pair<T, Long>> = it.versionedIterator()
            }
        }

        // take two snapshots, compare version vectors
        var verList = globalVersionedList()
        while (true) {
            val curVerList = globalVersionedList()
            val v1 = verList.map { it.second }
            val v2 = curVerList.map { it.second }
            if (v1 == v2)
                return verList.map { it.first }.iterator()
            else
                verList = curVerList
        }
    }
}