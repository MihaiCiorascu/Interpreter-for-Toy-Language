package src.java.adt.myDictionary;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import src.java.exceptions.AdtException;

public class MyDictionary<T, V> implements MyIDictionary<T, V> {
    private Map<T, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    public MyDictionary(Map<T, V> dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public List<V> getValues() {
        return new LinkedList<V>(dictionary.values());
    }

    @Override
    public Map<T,V> getContent() {
        return this.dictionary;
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
    public V lookup(T id) throws AdtException {
        if (isDefined(id)){
            return dictionary.get(id);
        } else {
            throw new AdtException("!EXCEPTION! The key provided for lookup doesn't exist.");
        }
    }

    @Override
    public void update(T id, V val) throws AdtException{
        if (isDefined(id)){
            dictionary.put(id, val);
        } else {
            throw new AdtException("!EXCEPTION! The key provided for update doesn't exist.");
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
    public MyIDictionary<T, V> deepCopy() {
        Map<T, V> result = new HashMap<>(this.dictionary);
        return new MyDictionary<>(result);
    }
}