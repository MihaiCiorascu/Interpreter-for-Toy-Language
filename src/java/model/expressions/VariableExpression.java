package src.java.model.expressions;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.adt.myHeap.MyIHeap;
import src.java.exceptions.AdtException;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.MyException;
import src.java.model.types.IType;
import src.java.model.values.IValue;

public class VariableExpression implements IExpression{
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException {
        if(symTable.isDefined(this.id)){
            try{
                return symTable.lookup(this.id);
            } catch (AdtException e) {
                throw new MyException(e.getMessage());
            }
        } else {
            throw new MyException("!EXCEPTION! The variable " + this.id + " is not defined");
        }
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws IExpressionException {
        return typeEnv.lookup(id);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(id);
    }

    @Override
    public String toString() {
        return this.id;
    }
}