package adt.myList;

import exceptions.AdtExceptions.MyIListException;

import java.util.List;
import java.util.ArrayList;

public class MyList<T> implements MyIList<T> {
    private List<T> elements;

    public MyList(List<T> e){
        elements = e;
    }

    public MyList(){
        elements = new ArrayList<T>();
    }

    @Override
    public List<T> getAll(){
        return elements;
    }

    @Override
    public T get(int index) throws MyIListException {
        if (index < 0 || index >= elements.size())
            throw new MyIListException("Index out of bounds.");
        return elements.get(index);
    }

    @Override
    public void add(T elem){
        elements.add(elem);
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Out[");
        for(T e: elements)
            s.append(e.toString()).append(" ");
        s.append("]");
        return s.toString();
    }
}
