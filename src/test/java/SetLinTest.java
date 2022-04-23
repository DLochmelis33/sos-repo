import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlinx.lincheck.LinChecker;
import org.jetbrains.kotlinx.lincheck.annotations.Operation;
import org.jetbrains.kotlinx.lincheck.annotations.Param;
import org.jetbrains.kotlinx.lincheck.paramgen.IntGen;
import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.ModelCheckingCTest;
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressCTest;
import org.jetbrains.kotlinx.lincheck.verifier.VerifierState;
import org.junit.Test;

import java.util.Iterator;

@ModelCheckingCTest
@Param(name = "v", gen = IntGen.class)
public class SetLinTest extends VerifierState {

    static {
        System.setProperty("--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED");
        System.setProperty("--add-exports", "java.base/jdk.internal.util=ALL-UNNAMED");
    }

    @Test
    public void test() {
        LinChecker.check(SetLinTest.class);
    }

    private final Set<Integer> set = new SetImpl<>();

    @Operation
    public boolean add(@Param(name = "v") int v) {
        return set.add(v);
    }

    @Operation
    public boolean remove(@Param(name = "v") int v) {
        return set.remove(v);
    }

    @Operation
    public boolean contains(@Param(name = "v") int v) {
        return set.contains(v);
    }

    @Operation
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Operation
    public Iterator<Integer> iterator() {
        return set.iterator();
    }

    @NotNull
    @Override
    protected Object extractState() {
        return set;
    }

}