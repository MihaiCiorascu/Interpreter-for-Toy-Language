package model.statements;

import adt.myDictionary.MyIDictionary;
import exceptions.IExpressionException;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.IType;
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
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException{
        this.expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStatement deepCopy(){
        return new PrintStatement(expression.deepCopy());
    }
}
