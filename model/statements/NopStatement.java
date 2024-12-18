package model.statements;

import adt.myDictionary.MyIDictionary;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;
import model.types.BoolType;
import model.types.IType;

public class NopStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws IStatementException {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException{
        return typeEnv;
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
