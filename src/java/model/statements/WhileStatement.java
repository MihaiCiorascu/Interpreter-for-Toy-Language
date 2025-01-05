package src.java.model.statements;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.IStatementException;
import src.java.exceptions.MyException;
import src.java.model.ProgramState;
import src.java.model.expressions.IExpression;
import src.java.model.types.BoolType;
import src.java.model.types.IType;
import src.java.model.values.BoolValue;
import src.java.model.values.IValue;

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
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException {
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (typeExp instanceof BoolType) {
            this.statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new IStatementException("!While Statement error! The condition of WHILE has not the type bool");
        }
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
