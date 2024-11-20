package adt.myHeap;

import exceptions.AdtExceptions.MyIHeapException;
import model.values.IValue;

import java.util.Map;

public interface MyIHeap {
    boolean isValid(Integer address);
    void addToNewAddress(IValue value);
    void addToAdress(Integer address, IValue value);
    boolean checkAddress(Integer address);
    Integer getLastAddedAddress();
    IValue getValueFromAdress(Integer address) throws MyIHeapException;
    void setContent(Map<Integer, IValue> content);
    Map<Integer, IValue> getContent();
}
