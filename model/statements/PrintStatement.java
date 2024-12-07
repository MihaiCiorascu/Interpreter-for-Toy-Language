package model.statements;

import exceptions.IExpressionException;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.values.IValue;

public class PrintStatement implements IStatement{
    IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString(){
        return "print(" + expression.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws IStatementException {
        IValue value;
        try{
            value = expression.eval(state.getSymTable(), state.getHeap());
        } catch (IExpressionException | MyException e){
            throw new IStatementException(e.getMessage());
        }
        state.getOut().add(value);
        return null;
    }

    @Override
    public IStatement deepCopy(){
        return new PrintStatement(expression.deepCopy());
    }
}
