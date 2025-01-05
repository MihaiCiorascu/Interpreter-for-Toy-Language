package src.java.model.statements;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.adt.myStack.MyStack;
import src.java.exceptions.IStatementException;
import src.java.model.ProgramState;
import src.java.model.types.IType;

public class ForkStatement implements IStatement{
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws IStatementException {
        return new ProgramState(new MyStack<>(), currentState.getSymTable().deepCopy(),
                currentState.getOutput(), this.statement, currentState.getFileTable(), currentState.getHeap());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException{
        this.statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
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
