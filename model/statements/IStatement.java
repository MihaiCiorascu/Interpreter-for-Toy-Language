package model.statements;
import adt.myDictionary.MyIDictionary;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;
import model.types.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws IStatementException;
    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException;

    IStatement deepCopy();
    String toString();
}
