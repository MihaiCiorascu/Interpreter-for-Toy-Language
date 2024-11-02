package model;

import adt.myDictionary.MyIDictionary;
import adt.myList.MyIList;
import adt.myStack.MyIStack;
import model.statements.IStatement;
import model.values.IValue;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, IValue> symTable;
    MyIList<IValue> out;
    IStatement originalProgram;

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, IValue> symTable, MyIList<IValue> out, IStatement prg) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = prg.deepCopy();

        exeStack.push(prg);
    }

    public MyIStack<IStatement> getStack() {
        return exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString() {
        return "\nProgramState{\n" + "ExeStack: " + exeStack.toString() + "\n" +
                "SymTable: " + symTable.toString()+ "\n" +
                "Out: " + out.toString() + "\n" +
                "OriginalProgram: " + originalProgram.toString() + "\n}" +
                "\n--------------------------------------------------------------------------";
    }
}