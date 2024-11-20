package model.statements;

import adt.myStack.MyIStack;
import exceptions.MyException;
import model.ProgramState;

public class CompoundStatement implements IStatement{
    IStatement first;
    IStatement second;

    public CompoundStatement(IStatement first, IStatement second){
        this.first = first;
        this.second = second;
    }

    public IStatement getFirst(){
        return this.first;
    }

    public IStatement getSecond(){
        return this.second;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();

        stack.push(second);
        stack.push(first);

        return state;
    }

    @Override
    public String toString(){
        return "(" + this.first.toString() + "; " + this.second.toString() + ")";
    }

    @Override
    public IStatement deepCopy(){
        return new CompoundStatement(this.first.deepCopy(), this.second.deepCopy());
    }
}
