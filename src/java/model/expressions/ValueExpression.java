package src.java.model.expressions;

import src.java.adt.myHeap.MyIHeap;
import src.java.exceptions.IExpressionException;
import src.java.model.types.IType;
import src.java.model.values.*;
import src.java.adt.myDictionary.*;
import src.java.exceptions.MyException;

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
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws IExpressionException{
        return value.getType();
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
