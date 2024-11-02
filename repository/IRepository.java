package repository;

import model.ProgramState;

public interface IRepository {
    void addPrg(ProgramState prg);
    ProgramState getCurrentPrg();
}
