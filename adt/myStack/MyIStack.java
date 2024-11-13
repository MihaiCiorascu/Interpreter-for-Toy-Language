package adt.myStack;

import exceptions.AdtExceptions.MyIStackException;
import exceptions.MyException;

public interface MyIStack<T> {
    T pop() throws MyIStackException;
    T top() throws MyIStackException;
    void push(T v);

    boolean isEmpty();
    String toString();
}