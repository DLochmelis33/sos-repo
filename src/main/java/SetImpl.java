import java.util.Iterator;

public class SetImpl<T extends Comparable<T>> implements Set<T> {

    private final SetImplKt<T> delegate = new SetImplKt<>();

    @Override
    public boolean add(T value) {
        return delegate.add(value);
    }

    @Override
    public boolean remove(T value) {
        return delegate.remove(value);
    }

    @Override
    public boolean contains(T value) {
        return delegate.contains(value);
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.iterator();
    }
}
