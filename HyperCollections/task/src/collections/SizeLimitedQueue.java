package collections;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.NoSuchElementException;

public class SizeLimitedQueue<E> {

    private final int limit;
    private final Deque<E> deque = new ArrayDeque<>();

    public SizeLimitedQueue(int limit) {
        if(limit<=0){
            throw new IllegalArgumentException();
        }
        this.limit = limit;
    }

    public void add(E element){
        if(element==null){
            throw new NullPointerException();
        }
        if (deque.size() == limit) {
            deque.removeFirst();
        }
        deque.add(element);
    }

    public void clear() {
        deque.clear();
    }

    public boolean isAtFullCapacity() {
        return deque.size() == limit;
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public int maxSize() {
        return limit;
    }

    public E peek() {
        return deque.peek();
    }

    public E remove() {
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        return deque.remove();
    }

    public int size() {
        return deque.size();
    }

    public E[] toArray(E[] e) {
        return deque.toArray(e);
    }

    public Object[] toArray() {
        return deque.toArray();
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
}
