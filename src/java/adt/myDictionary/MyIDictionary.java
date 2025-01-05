package src.java.adt.myDictionary;

import src.java.exceptions.AdtException;

import java.util.List;
import java.util.Map;

public interface MyIDictionary<T,V> {
    boolean isDefined(T id);
    V lookup(T id) throws AdtException;
    void update(T id, V val) throws AdtException;
    void put(T varName, V o);
    String toString();
    List<V> getValues();
    Map<T,V> getContent();

    MyIDictionary<T, V> deepCopy();
}
