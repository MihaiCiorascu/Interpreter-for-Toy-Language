package adt.myHeap;

import exceptions.AdtExceptions.MyIHeapException;
import model.values.IValue;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MyIHeap<K, V> {
    V get(K key) throws MyIHeapException;
    void put(K key, V value);
    void remove(K key);
    boolean isDefined(K key);
    String toString();
    Map<K, V> getHeap();
    List<V> getValues();
    void setHeap(Map<K, V> dictionary);
    Map<Integer, IValue> safeGarbageCollector(Set<Integer> unionSetOfUsedAddr, Map<Integer, IValue> heap);
    Integer allocate();
}
