
class SetImplKt<T : Comparable<T>>(capacity: Int = 1_000_001) : Set<T> {

    // allocate array of size `capacity`
    // i-th element is a lock-free list of elements with (hash % capacity)==i
    // for sake of simplicity no reallocations

    override fun add(value: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(value: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(value: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<T> {
        TODO("Not yet implemented")
    }
}