package model.expressions;

import adt.myHeap.MyIHeap;
import exceptions.IExpressionException;
import model.values.*;
import adt.myDictionary.*;
import exceptions.MyException;

public class ValueExpression implements IExpression{
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException {
        return this.value;
    }

    @Override
    public IExpression deepCopy(){
        return new ValueExpression(this.value.deepCopy());
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
