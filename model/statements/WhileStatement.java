package model.statements;

import exceptions.IExpressionException;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStatement implements IStatement {
    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IStatementException {
        IValue value;
        try {
            value = expression.eval(state.getSymTable(), state.getHeap());
        } catch (IExpressionException | MyException e) {
            throw new IStatementException(e.getMessage());
        }
        if (!value.getType().equals(new BoolType())) {
            throw new IStatementException("!EXCEPTION! Invalid expression in while statement");
        }
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getValue()) {
            state.getStack().push(this);
            state.getStack().push(statement);
        }
        return null;
    }

    @Override
    public String toString() {
        return "WhileStmt(" + this.expression.toString() + ", " + this.statement.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }
}
