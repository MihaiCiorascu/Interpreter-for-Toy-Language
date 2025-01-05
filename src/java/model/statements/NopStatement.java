package src.java.model.statements;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.exceptions.IStatementException;
import src.java.model.ProgramState;
import src.java.model.types.IType;

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
