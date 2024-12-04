package adt.myStack;

import exceptions.AdtExceptions.MyIStackException;
import exceptions.MyException;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws MyIStackException;
    T top() throws MyIStackException;
    void push(T v);
    int size();

    boolean isEmpty();
    String toString();
}