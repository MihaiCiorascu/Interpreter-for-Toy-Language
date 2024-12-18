package model.statements;

import adt.myDictionary.MyIDictionary;
import exceptions.AdtException;
import exceptions.IExpressionException;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapNewStatement implements IStatement{
    String varName;
    IExpression expression;

    public HeapNewStatement(String varName, IExpression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IStatementException {
        if (!state.getSymTable().isDefined(varName)) {
            throw new IStatementException("!EXCEPTION! Variable '" + varName + "' not defined");
        }
        IType type;
        try {
            type = state.getSymTable().lookup(varName).getType();
        } catch (AdtException e) {
            throw new IStatementException(e.getMessage());
        }
        if (!(type instanceof RefType)) {
            throw new IStatementException("!EXCEPTION! Variable '" + varName + "' is not a reference");
        }

        IValue value;
        try {
            value = expression.eval(state.getSymTable(), state.getHeap());
        } catch (IExpressionException | MyException e) {
            throw new IStatementException(e.getMessage());
        }
        if (!value.getType().equals(((RefType) type).getInner())) {
            throw new IStatementException("Type mismatch: expected " + ((RefType) type).getInner().toString() +
                    ", got " + value.getType().toString());
        }

        Integer newAddress = state.getHeap().allocate();
        state.getHeap().put(newAddress, value);
        state.getSymTable().put(varName, new RefValue(newAddress, value.getType()));

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException{
        IType typeVar = typeEnv.lookup(varName);
        IType typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        }
        else
            throw new IStatementException("!New Statement error! Different types found");
    }

    @Override
    public IStatement deepCopy() {
        return new HeapNewStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "NewStmt(" + varName + ", " + expression.toString() + ")";
    }
}
