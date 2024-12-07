package repository;

import exceptions.IRepositoryException;
import model.ProgramState;

import java.util.List;

public interface IRepository {
    void addPrg(ProgramState prg);
    void logPrgStateExec(ProgramState prg) throws IRepositoryException;
    List<ProgramState> getPrgStates();
    void setPrgStates(List<ProgramState> programStates);
}
