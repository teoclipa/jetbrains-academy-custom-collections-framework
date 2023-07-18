package collections;

import java.util.ArrayList;
import java.util.Objects;

public final class ImmutableCollection<E> {
    private final ArrayList<E> elements;

    private ImmutableCollection(ArrayList<E> elements) {
        this.elements = elements;
    }

    public static <E> ImmutableCollection<E> of() {
        return new ImmutableCollection<>(new ArrayList<>());
    }

    @SafeVarargs
    public static <E> ImmutableCollection<E> of(E... elements) {
        ArrayList<E> list = new ArrayList<>();
        if (elements != null) {
            for (E e : elements) {
                Objects.requireNonNull(e, "Elements cannot be null");
                list.add(e);
            }
        }
        return new ImmutableCollection<>(list);
    }

    public boolean contains(E element) {
        return elements.contains(element);
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
