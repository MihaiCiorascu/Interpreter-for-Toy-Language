package src.java.repository;

import src.java.exceptions.IRepositoryException;
import src.java.model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private String logFilePath;

    public Repository(ProgramState prg){
        programStates = new ArrayList<>();
        this.programStates.add(prg);
    }

    public Repository(ProgramState prg, String logFilePath) {
        programStates = new ArrayList<>();
        this.programStates.add(prg);
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
        programStates.add(prg);
    }

    @Override
    public List<ProgramState> getPrgStates() {
        return this.programStates;
    }

    @Override
    public void setPrgStates(List<ProgramState> other) {
        this.programStates = other;
    }

    @Override
    public void logPrgStateExec(ProgramState prg) throws IRepositoryException {
        if (logFilePath == null) {
            setLogFilePath();
        }
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new IRepositoryException("Error opening log file");
        }
        logFile.println(prg.toString());
        logFile.close();
    }
}
