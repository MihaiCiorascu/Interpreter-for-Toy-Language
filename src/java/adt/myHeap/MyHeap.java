package src.java.adt.myHeap;

import src.java.exceptions.AdtException;

import java.util.*;

public class MyHeap<K, V> implements MyIHeap<K, V>{
    private Integer firstFreeAddress;
    private Map<K, V> heap;

    public MyHeap() {
        this.firstFreeAddress = 1;
        this.heap = new HashMap<>();
    }

    @Override
    public V get(K key) throws AdtException {
        if (!this.isDefined(key)) {
            throw new AdtException("!EXCEPTION! The key provided for get doesn't exist.");
        }
        return this.heap.get(key);
    }

    @Override
    public void put(K key, V value) {
        this.heap.put(key, value);
    }

    @Override
    public boolean isDefined(K key) {
        return this.heap.containsKey(key);
    }

    @Override
    public void remove(K key) {
        this.heap.remove(key);
    }

    @Override
    public Map<K, V> getContent() {
        return this.heap;
    }

    @Override
    public void setContent(Map<K, V> dictionary) {
        this.heap = dictionary;
    }

    @Override
    public List<V> getValues() {
        return new LinkedList<V>(this.heap.values());
    }

    @Override
    public Integer allocate() {
        return this.firstFreeAddress++;
    }

    @Override
    public String toString() {
        if (this.heap.isEmpty())
            return "(the heap is empty)\n";

        StringBuilder s = new StringBuilder();
        for (K key: this.heap.keySet()) {
            s.append(key.toString()).append(" -> ");
            s.append(this.heap.get(key).toString());
            s.append("\n");
        }
        return s.toString();
    }
}
