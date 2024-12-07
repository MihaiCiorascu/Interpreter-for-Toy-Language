package model.statements;

import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;

public class NopStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws IStatementException {
        return null;
    }

    @Override
    public IStatement deepCopy(){
        return new NopStatement();
    }

    @Override
    public String toString(){
        return "NOP";
    }
}
