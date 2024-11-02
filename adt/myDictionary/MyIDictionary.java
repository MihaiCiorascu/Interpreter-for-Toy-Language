package adt.myDictionary;

import exceptions.AdtExceptions.MyIDictionaryException;
import exceptions.MyException;

public interface MyIDictionary<T,V> {
    boolean isDefined(T id);
    V lookup(T id) throws MyIDictionaryException;
    void update(T id, V val) throws MyIDictionaryException;
    void put(T varName, V o);
    String toString();
}
