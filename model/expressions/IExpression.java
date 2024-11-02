package model.expressions;

import adt.myDictionary.MyIDictionary;
import exceptions.MyException;
import model.values.IValue;

public interface IExpression {
    public IValue eval(MyIDictionary<String, IValue> table) throws MyException;

    String toString();
    IExpression deepCopy();
}
