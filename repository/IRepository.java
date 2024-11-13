package repository;

import exceptions.MyException;
import model.ProgramState;

public interface IRepository {
    void addPrg(ProgramState prg);
    ProgramState getCurrentPrg();
    void logPrgStateExec(ProgramState prg) throws MyException;
}
