package model.statements;
import exceptions.MyException;
import model.ProgramState;

public interface IStatement {
    public ProgramState execute(ProgramState state) throws MyException;
    public IStatement deepCopy();
    String toString();
}
