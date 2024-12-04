package model.expressions;

import adt.myDictionary.MyIDictionary;
import adt.myHeap.MyIHeap;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.values.IValue;

public interface IExpression {
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException;

    String toString();
    IExpression deepCopy();
}
