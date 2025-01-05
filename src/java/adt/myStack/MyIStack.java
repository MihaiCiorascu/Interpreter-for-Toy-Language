package src.java.adt.myStack;

import src.java.exceptions.AdtException;

public interface MyIStack<T> {
    T pop() throws AdtException;
    T top() throws AdtException;
    void push(T v);
    int size();

    boolean isEmpty();
    String toString();
}