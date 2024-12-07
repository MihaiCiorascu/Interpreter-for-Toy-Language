package adt.myStack;

import exceptions.AdtException;
import exceptions.MyException;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws AdtException;
    T top() throws AdtException;
    void push(T v);
    int size();

    boolean isEmpty();
    String toString();
}