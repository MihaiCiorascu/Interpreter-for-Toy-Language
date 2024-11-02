package model.expressions;

import model.values.*;
import adt.myDictionary.*;
import exceptions.MyException;

public class ValueExpression implements IExpression{
    private IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table) throws MyException{
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
