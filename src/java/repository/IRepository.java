package src.java.repository;

import src.java.exceptions.IRepositoryException;
import src.java.model.ProgramState;

import java.util.List;

public interface IRepository {
    void addPrg(ProgramState prg);
    void logPrgStateExec(ProgramState prg) throws IRepositoryException;
    List<ProgramState> getPrgStates();
    void setPrgStates(List<ProgramState> programStates);
}
