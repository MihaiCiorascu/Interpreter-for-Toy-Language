package adt.myDictionary;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import exceptions.AdtExceptions.MyIDictionaryException;

public class MyDictionary<T, V> implements MyIDictionary<T, V> {
    private Map<T, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public void put(T key, V value) {
        this.dictionary.put(key, value);
    }

    @Override
    public boolean isDefined(T id) {
        return dictionary.containsKey(id);
    }

    @Override
    public V lookup(T id) throws MyIDictionaryException {
        if (isDefined(id)){
            return dictionary.get(id);
        } else {
            throw new MyIDictionaryException("!EXCEPTION! The key provided for lookup doesn't exist.");
        }
    }

    @Override
    public void update(T id, V val) throws MyIDictionaryException{
        if (isDefined(id)){
            dictionary.put(id, val);
        } else {
            throw new MyIDictionaryException("!EXCEPTION! The key provided for update doesn't exist.");
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T key: dictionary.keySet())
            s.append(key).append(" -> ").append(dictionary.get(key)).append("\n");
        return s.toString();
    }

    @Override
    public List<V> getValues() {
        return new LinkedList<V>(dictionary.values());
    }
}