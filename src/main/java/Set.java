
public interface Set<T extends Comparable<T>> {

    boolean add(T value);

    boolean remove(T value);

    boolean contains(T value);

    boolean isEmpty();

    java.util.Iterator<T> iterator();
}
