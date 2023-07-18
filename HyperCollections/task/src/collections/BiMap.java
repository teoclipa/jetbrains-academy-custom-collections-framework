package collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BiMap<K, V> {
    private final Map<K, V> forwardMap;
    private final Map<V, K> reverseMap;


    //public noargs constructor
    public BiMap() {
        this(new HashMap<>(), new HashMap<>());
    }


    public V put(K key, V value) {
        if (forwardMap.containsKey(key) || reverseMap.containsKey(value)) {
            throw new IllegalArgumentException();
        }
        forwardMap.put(key, value);
        reverseMap.put(value, key);
        return value;
    }

    public void putAll(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public Set<V> values() {
        return new HashSet<>(forwardMap.values());
    }

    public V forcePut(K key, V value) {
        if (forwardMap.containsKey(key)) {
            V oldValue = forwardMap.get(key);
            reverseMap.remove(oldValue);
        }
        if (reverseMap.containsKey(value)) {
            K oldKey = reverseMap.get(value);
            forwardMap.remove(oldKey);
        }
        forwardMap.put(key, value);
        reverseMap.put(value, key);
        return value;
    }

    public BiMap<V, K> inverse() {
        return new BiMap<>(reverseMap, forwardMap);
    }

    private BiMap(Map<K, V> forwardMap, Map<V, K> reverseMap) {
        this.forwardMap = forwardMap;
        this.reverseMap = reverseMap;
    }

    @Override
    public String toString() {
        return forwardMap.toString();
    }
}
