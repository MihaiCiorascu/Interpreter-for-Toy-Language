package repository;

import adt.myList.MyIList;
import exceptions.MyException;
import model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Repository implements IRepository {
    private List<ProgramState> prgList;
    private String logFilePath;

    public Repository(ProgramState prg){
        prgList = new ArrayList<>();
        this.prgList.add(prg);
    }

    public Repository(ProgramState prg, String logFilePath) {
        prgList = new ArrayList<>();
        this.prgList.add(prg);
        this.logFilePath = logFilePath;
    }

    public void setLogFilePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the log file path: ");
        this.logFilePath = scanner.nextLine();
        scanner.close();
    }

    @Override
    public void addPrg(ProgramState prg){
        prgList.add(prg);
    }

    @Override
    public ProgramState getCurrentPrg() {
        return prgList.get(0);
    }

    @Override
    public void logPrgStateExec(ProgramState prg) throws MyException {
        if (logFilePath == null) {
            setLogFilePath();
        }
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (Exception e) {
            throw new MyException("Error opening log file");
        }
        logFile.println(prg.toString());
        logFile.close();
    }
}
