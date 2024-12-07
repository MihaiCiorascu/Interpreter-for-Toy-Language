package model.statements;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;

public interface IStatement {
    public ProgramState execute(ProgramState state) throws IStatementException;
    public IStatement deepCopy();
    String toString();
}
