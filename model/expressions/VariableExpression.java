package model.expressions;

import adt.myDictionary.MyIDictionary;
import exceptions.AdtExceptions.MyIDictionaryException;
import exceptions.MyException;
import model.values.IValue;

public class VariableExpression implements IExpression{
    String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table) throws MyException {
        if(table.isDefined(this.id)){
            try{
                return table.lookup(this.id);
            } catch (MyIDictionaryException e) {
                throw new MyException(e.getMessage());
            }
        } else {
            throw new MyException("The variable " + this.id + " is not defined");
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
