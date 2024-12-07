package adt.myHeap;

import exceptions.AdtException;

import java.util.List;
import java.util.Map;

public interface MyIHeap<K, V> {
    V get(K key) throws AdtException;
    void put(K key, V value);
    void remove(K key);
    boolean isDefined(K key);
    String toString();
    Map<K, V> getContent();
    List<V> getValues();
    void setContent(Map<K, V> dictionary);
    Integer allocate();
}
