package collections;

import java.util.*;

public class Multiset<E> {
    private final Map<E, Integer> map;

    public Multiset() {
        this.map = new HashMap<>();
    }

    public void add(E element) {
        this.map.put(element, this.map.getOrDefault(element, 0) + 1);
    }

    public void add(E element, int occurrences) {
        if (occurrences > 0) {
            this.map.put(element, this.map.getOrDefault(element, 0) + occurrences);
        }
    }

    //	Removes a single occurrence of the specified element from the current multiset, if present
    public void remove(E element) {
        this.remove(element, 1);
    }

    //Removes a number of occurrences of the specified element from the current multiset
    public void remove(E element, int occurrences) {
        if (occurrences > 0) {
            int count = this.map.getOrDefault(element, 0);
            if (count > 0) {
                if (count > occurrences) {
                    this.map.put(element, count - occurrences);
                } else {
                    this.map.remove(element);
                }
            }
        }
    }

    public boolean contains(E element) {
        return this.map.containsKey(element);
    }

    public int count(E element) {
        return this.map.getOrDefault(element, 0);
    }

    public void setCount(E element, int count) {
        if (this.map.containsKey(element) && count >= 0) {
            this.map.put(element, count);
        }
    }

    public void setCount(E element, int oldCount, int newCount) {
        if (this.map.getOrDefault(element, 0) == oldCount) {
            setCount(element, newCount);
        }
    }

    public Set<E> elementSet() {
        Set<E> set = new HashSet<>();
        this.map.forEach((k, v) -> {
            if (v > 0) {
                set.add(k);
            }
        });
        return set;
    }

    @Override
    public String toString() {
        List<E> list = new ArrayList<>();
        for (Map.Entry<E, Integer> entry : this.map.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                list.add(entry.getKey());
            }
        }
        return list.toString();
    }
}
