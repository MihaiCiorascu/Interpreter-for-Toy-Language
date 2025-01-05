package src.java.model.expressions;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.adt.myHeap.MyIHeap;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.MyException;
import src.java.model.types.IType;
import src.java.model.values.IValue;


public interface IExpression {
    IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException;
    IType typeCheck(MyIDictionary<String, IType> typeEnv) throws IExpressionException;

    String toString();
    IExpression deepCopy();
}
