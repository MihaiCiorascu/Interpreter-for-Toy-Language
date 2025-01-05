package src.java.model.statements;
import src.java.adt.myDictionary.MyIDictionary;
import src.java.exceptions.IStatementException;
import src.java.model.ProgramState;
import src.java.model.types.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws IStatementException;
    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException;

    IStatement deepCopy();
    String toString();
}
