package adt.myList;
import exceptions.AdtExceptions.MyIListException;

import java.util.List;

public interface MyIList<T> {
    List<T> getAll();
    T get(int index) throws MyIListException;
    void add(T elem);
    String toString();
}
