package src.java.model.statements;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.IStatementException;
import src.java.exceptions.MyException;
import src.java.model.ProgramState;
import src.java.model.expressions.IExpression;
import src.java.model.types.IType;
import src.java.model.values.IValue;

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
        state.getOutput().add(value);
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
