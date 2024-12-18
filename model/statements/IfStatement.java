package model.statements;

import adt.myDictionary.MyIDictionary;
import adt.myHeap.MyIHeap;
import exceptions.IExpressionException;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.IType;
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
            state.getStack().push(thenStatement);
        } else {
            state.getStack().push(elseStatement);
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