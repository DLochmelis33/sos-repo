import org.junit.Assert.*
import org.junit.Test

class LockFreeSlowSetTest {

    @Test//(timeout = 1000)
    fun testSimple() {
        val set = LockFreeSlowSet<Int>()
        assertFalse(set.lookup(5))
        assertTrue(set.add(5))
        assertTrue(set.lookup(5))
        assertTrue(set.remove(5))
        assertFalse(set.lookup(5))
        assertFalse(set.remove(5))
    }

    @Test
    fun testIterator() {
        val set = LockFreeSlowSet<Int>()
        val values = listOf(0, 1, 2, 3, 4)
        for (value in values) set.add(value)
        val iter = set.versionedIterator()
        for (value in values) {
            assertTrue(iter.hasNext())
            assertEquals(value, iter.next().first)
        }
        assertFalse(iter.hasNext())
    }

}