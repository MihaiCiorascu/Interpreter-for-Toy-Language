package model.expressions;

import adt.myDictionary.MyIDictionary;
import adt.myHeap.MyIHeap;
import exceptions.AdtException;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.values.IValue;
import model.values.RefValue;

public class RefExp implements IExpression {
    private IExpression expression;

    public RefExp(IExpression expression){
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException {
        IValue value = expression.eval(symTable, heap);
        if (!(value instanceof RefValue)) {
            throw new IExpressionException("Expected a RefValue, got " + value);
        }

        RefValue refValue = (RefValue) value;
        Integer address = refValue.getAddress();
        if (!heap.isDefined(address)) {
            throw new IExpressionException("Address " + address + " is not defined in the heap");
        }
        try {
            return heap.get(address);
        } catch (AdtException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IExpression deepCopy(){
        return new RefExp(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "RefExp(" + expression.toString() + ")";
    }
}