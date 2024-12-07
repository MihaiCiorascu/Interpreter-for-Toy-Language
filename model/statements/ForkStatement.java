package model.statements;

import adt.myStack.MyStack;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;

public class ForkStatement implements IStatement{
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws IStatementException {
        return new ProgramState(new MyStack<>(), currentState.getSymTable().deepCopy(),
                currentState.getOut(), this.statement, currentState.getFileTable(), currentState.getHeap());
    }

    @Override
    public String toString() {
        return "Fork: " + statement.toString();
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }
}
