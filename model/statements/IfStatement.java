package model.statements;

import adt.myDictionary.MyIDictionary;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStatement implements IStatement{
    private IExpression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement){
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString(){
        return "(IF(" + this.expression.toString() + ") THEN (" + this.thenStatement.toString() + ") ELSE (" + this.elseStatement.toString() + "))";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> dict = state.getSymTable();
        IValue value;
        try{
            value = expression.eval(dict);
        } catch (IExpressionException | MyException e){
            throw new MyException(e.getMessage());
        }

        if (!value.getType().equals(new BoolType())){
            throw new MyException("Invalid expression in if statement");
        }

        BoolValue v = (BoolValue) value;
        if (v.getValue()){
            state.getStack().push(thenStatement);
        } else {
            state.getStack().push(elseStatement);
        }

        return state;
    }

    @Override
    public IStatement deepCopy(){
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }
}
