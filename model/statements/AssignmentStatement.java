package model.statements;

import adt.myDictionary.MyIDictionary;
import exceptions.AdtException;
import exceptions.IExpressionException;
import exceptions.IStatementException;
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
    public ProgramState execute(ProgramState state) throws IStatementException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();

        if (symTable.isDefined(id)) {
            IValue value;
            try {
                value = this.expression.eval(symTable, state.getHeap());
            } catch (IExpressionException | MyException e) {
                throw new IStatementException(e.getMessage());
            }
            try{
                if (value.getType().equals(symTable.lookup(id).getType()))
                    symTable.update(id, value);
                else
                    throw new MyException("!EXCEPTION! Declared type of variable '" + id + "' and type of the assigned expression do not match");
            } catch (AdtException | MyException e){
                throw new IStatementException(e.getMessage());
            }
        } else
            throw new IStatementException("!EXCEPTION! The used variable '" + id + "' was not declared before");
        return null;
    }

    @Override
    public String toString(){
        return id + " = " + expression.toString();
    }

    @Override
    public IStatement deepCopy(){
        return new AssignmentStatement(id, expression.deepCopy());
    }
}