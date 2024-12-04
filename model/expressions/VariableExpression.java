package model.expressions;

import adt.myDictionary.MyIDictionary;
import adt.myHeap.MyIHeap;
import exceptions.AdtExceptions.MyIDictionaryException;
import exceptions.MyException;
import model.values.IValue;

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
            } catch (MyIDictionaryException e) {
                throw new MyException(e.getMessage());
            }
        } else {
            throw new MyException("!EXCEPTION! The variable " + this.id + " is not defined");
        }
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