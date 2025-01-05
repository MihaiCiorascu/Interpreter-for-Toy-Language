package src.java.adt.myList;
import src.java.exceptions.AdtException;

import java.util.List;

public interface MyIList<T> {
    List<T> getAll();
    T get(int index) throws AdtException;
    void add(T elem);
    String toString();
}
