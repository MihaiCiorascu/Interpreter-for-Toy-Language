package src.java.model.statements;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.adt.myStack.MyIStack;
import src.java.exceptions.IStatementException;
import src.java.model.ProgramState;
import src.java.model.types.IType;

public class CompoundStatement implements IStatement{
    IStatement firstStatement;
    IStatement secondStatement;

    public CompoundStatement(IStatement first, IStatement second){
        this.firstStatement = first;
        this.secondStatement = second;
    }

    public IStatement getFirstStatement(){
        return this.firstStatement;
    }

    public IStatement getSecondStatement(){
        return this.secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws IStatementException {
        MyIStack<IStatement> stack = state.getExeStack();

        stack.push(secondStatement);
        stack.push(firstStatement);

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException {
        return this.secondStatement.typeCheck(this.firstStatement.typeCheck(typeEnv));
    }

    @Override
    public String toString(){
        return "(" + this.firstStatement.toString() + "; " + this.secondStatement.toString() + ")";
    }

    @Override
    public IStatement deepCopy(){
        return new CompoundStatement(this.firstStatement.deepCopy(), this.secondStatement.deepCopy());
    }
}
