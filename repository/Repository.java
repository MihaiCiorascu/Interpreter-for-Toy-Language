package repository;

import adt.myList.MyIList;
import model.ProgramState;

import java.util.ArrayList;
import java.util.List;


public class Repository implements IRepository {
    private List<ProgramState> prgList;

    public Repository(ProgramState prg){
        prgList = new ArrayList<>();
        this.prgList.add(prg);
    }

    @Override
    public void addPrg(ProgramState prg){
        prgList.add(prg);
    }

    @Override
    public ProgramState getCurrentPrg(){
        return prgList.get(0);
    }
}
