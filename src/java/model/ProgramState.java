package src.java.model;

import src.java.adt.myBinaryTree.BinaryTreeBuilder;
import src.java.adt.myBinaryTree.ITreeBuilder;
import src.java.adt.myBinaryTree.MyIBinaryTree;
import src.java.adt.myDictionary.MyIDictionary;
import src.java.adt.myHeap.MyIHeap;
import src.java.adt.myList.MyIList;
import src.java.adt.myStack.MyIStack;
import src.java.adt.myStack.MyStack;
import src.java.exceptions.AdtException;
import src.java.exceptions.MyException;
import src.java.model.statements.IStatement;
import src.java.model.values.IValue;
import src.java.model.values.StringValue;

import java.io.BufferedReader;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, IValue> symTable;
    MyIList<IValue> output;
    IStatement originalProgram;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyIHeap<Integer, IValue> heap;
    private final int id;
    private static int nextId = 0;
    private boolean isNotCompleted;

    public MyIStack<IStatement> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOutput() {
        return output;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer, IValue> getHeap() {
        return heap;
    }

    public int getId() {
        return id;
    }

    public boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public void setNotCompleted(boolean isNotCompleted) {
        this.isNotCompleted = isNotCompleted;
    }

    public synchronized static int getNextId() {
        return nextId++;
    }

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, IValue> symTable,
                        MyIList<IValue> output, IStatement originalProgram, MyIDictionary<StringValue, BufferedReader> fileTable,
                        MyIHeap<Integer, IValue> heap) {
        this.id = getNextId();
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.output = output;
        this.originalProgram = originalProgram.deepCopy();
        this.fileTable = fileTable;
        this.heap = heap;
        this.isNotCompleted = true;

        exeStack.push(originalProgram);
    }

    public ProgramState oneStep() throws MyException {
        if (exeStack.isEmpty()) {
            this.isNotCompleted = false;
            return null;
        }

        IStatement currentStatement;
        try{
            currentStatement = exeStack.pop();
        } catch (AdtException e) {
            throw new MyException("!EXCEPTION! ProgramState stack is empty");
        }
        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        return "\nProgramState{\n" + "Program id: " + id + "\n" +
                "ExeStack:\n" + BinaryTreeFromExeStack(exeStack) + "\n" +
                "SymTable:\n" + symTable.toString()+ "\n" +
                "Heap:\n" + heap.toString() + "\n" +
                "Out:\n" + output.toString() + "\n" +
                "OriginalProgram: " + originalProgram.toString() + "\n" +
                "FileTable:\n"+ fileTable.toString() + "\n}" +
                "\n\n\n";
    }

    private String BinaryTreeFromExeStack(MyIStack<IStatement> executionStack) {
        ITreeBuilder<IStatement> treeBuilder = new BinaryTreeBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        MyStack<IStatement> exeStack = (MyStack<IStatement>) executionStack;
        for (IStatement statement : exeStack.getStack()) {
            MyIBinaryTree<IStatement> currentTree = treeBuilder.buildTree(statement);
            StringBuilder subtreeStringBuilder = currentTree.treeTraversal();
            stringBuilder.append(subtreeStringBuilder);
            stringBuilder.append("--------------------------------------\n");
        }
        return stringBuilder.toString();
    }
}