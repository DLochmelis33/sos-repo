import org.junit.Test;

import static org.junit.Assert.*;

public class SetManualTest {

    @Test
    public void test() {
        Set<Integer> set = new SetImpl<>();
        assertTrue(set.isEmpty());
        assertTrue(set.add(1));
        assertFalse(set.contains(3));
        assertTrue(set.contains(1));
        assertFalse(set.isEmpty());
        assertFalse(set.remove(2));
        assertFalse(set.isEmpty());
        assertTrue(set.add(3));
        assertTrue(set.remove(3));
        assertFalse(set.contains(3));
    }

}
