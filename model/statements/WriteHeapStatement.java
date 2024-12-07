package model.statements;

import exceptions.AdtException;
import exceptions.IExpressionException;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class WriteHeapStatement implements IStatement{
    private String varName;
    private IExpression expression;

    public WriteHeapStatement(String varName, IExpression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IStatementException {
        if (!state.getSymTable().isDefined(this.varName)) {
            throw new IStatementException("!EXCEPTION! Variable '" + varName + "' not defined");
        }

        IValue varValue;
        try{
            varValue = state.getSymTable().lookup(this.varName);
        } catch (AdtException e){
            throw new IStatementException(e.getMessage());
        }
        if (!(varValue instanceof RefValue)) {
            throw new IStatementException("!EXCEPTION! Variable '" + varName + "' is not a RefType");
        }

        RefValue refValue = (RefValue) varValue;
        Integer address = refValue.getAddress();

        if (!state.getHeap().isDefined(address)) {
            throw new IStatementException("!EXCEPTION! Address " + address + " is not defined in the heap");
        }

        IValue value;
        try {
            value = expression.eval(state.getSymTable(), state.getHeap());
        } catch (IExpressionException | MyException e) {
            throw new IStatementException(e.getMessage());
        }
        if (!value.getType().equals(((RefType) refValue.getType()).getInner())) {
            throw new IStatementException("Type of expression and type of variable do not match");
        }

        state.getHeap().put(address, value);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(this.varName, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "WriteHeapStmt(" + this.varName + ", " + this.expression.toString() + ")";
    }
}
