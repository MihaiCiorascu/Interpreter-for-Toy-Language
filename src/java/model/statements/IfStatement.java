package src.java.model.statements;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.adt.myHeap.MyIHeap;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.IStatementException;
import src.java.exceptions.MyException;
import src.java.model.ProgramState;
import src.java.model.expressions.IExpression;
import src.java.model.types.BoolType;
import src.java.model.types.IType;
import src.java.model.values.BoolValue;
import src.java.model.values.IValue;

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
    public ProgramState execute(ProgramState state) throws IStatementException {
        MyIDictionary<String, IValue> dict = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();
        IValue value;
        try{
            value = expression.eval(dict, heap);
        } catch (IExpressionException | MyException e){
            throw new IStatementException(e.getMessage());
        }

        if (!value.getType().equals(new BoolType())){
            throw new IStatementException("!EXCEPTION! Invalid expression in if statement");
        }

        BoolValue v = (BoolValue) value;
        if (v.getValue()){
            state.getExeStack().push(thenStatement);
        } else {
            state.getExeStack().push(elseStatement);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException{
        IType typeExp = expression.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            this.thenStatement.typeCheck(typeEnv.deepCopy());
            this.elseStatement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new IStatementException("!If Statement error! The condition of IF has not the type bool");
    }

    @Override
    public String toString(){
        return "(IF(" + this.expression.toString() + ") THEN (" + this.thenStatement.toString() + ") ELSE (" + this.elseStatement.toString() + "))";
    }

    @Override
    public IStatement deepCopy(){
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }
}