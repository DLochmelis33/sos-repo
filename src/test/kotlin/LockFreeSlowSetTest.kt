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

}