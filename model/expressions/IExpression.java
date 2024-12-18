package model.expressions;

import adt.myDictionary.MyIDictionary;
import adt.myHeap.MyIHeap;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.types.IType;
import model.values.IValue;


public interface IExpression {
    IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException;
    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws IExpressionException;

    String toString();
    IExpression deepCopy();
}
