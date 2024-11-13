package model.statements;

import adt.myDictionary.MyIDictionary;
import exceptions.AdtExceptions.MyIDictionaryException;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.*;
import model.values.*;
import model.types.*;

public class AssignmentStatement implements IStatement{
    String id;
    IExpression expression;

    public AssignmentStatement(String id, IExpression expression){
        this.id = id;
        this.expression = expression;
    }

    public String getId(){
        return this.id;
    }

    public IExpression getExpression(){
        return this.expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();

        if (symTable.isDefined(id)) {
            IValue value;
            try {
                value = this.expression.eval(symTable);
            } catch (IExpressionException | MyException e) {
                throw new MyException(e.getMessage());
            }
            try{
                if (value.getType().equals(symTable.lookup(id).getType()))
                    symTable.update(id, value);
                else
                    throw new MyException("!EXCEPTION! Declared type of variable '" + id + "' and type of the assigned expression do not match");
            } catch (MyIDictionaryException | MyException e){
                throw new MyException(e.getMessage());
            }
        } else
            throw new MyException("!EXCEPTION! The used variable '" + id + "' was not declared before");
        return state;
    }

    @Override
    public String toString(){
        return id + "=" + expression.toString();
    }

    @Override
    public IStatement deepCopy(){
        return new AssignmentStatement(id, expression.deepCopy());
    }
}