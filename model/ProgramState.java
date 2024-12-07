package model;

import adt.myBinaryTree.BinaryTreeBuilder;
import adt.myBinaryTree.ITreeBuilder;
import adt.myBinaryTree.MyIBinaryTree;
import adt.myDictionary.MyDictionary;
import adt.myDictionary.MyIDictionary;
import adt.myHeap.MyIHeap;
import adt.myList.MyIList;
import adt.myList.MyList;
import adt.myStack.MyIStack;
import adt.myStack.MyStack;
import exceptions.AdtException;
import exceptions.MyException;
import model.statements.IStatement;
import model.values.IValue;
import model.values.RefValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, IValue> symTable;
    MyIList<IValue> out;
    IStatement originalProgram;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyIHeap<Integer, IValue> heap;
    private int id;
    private static int generalId = 0;

    public MyIStack<IStatement> getStack() {
        return exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer, IValue> getHeap() {
        return heap;
    }

    public boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public synchronized static void idChange() {
        generalId = generalId + 1;
    }

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, IValue> symTable,
                        MyIList<IValue> out, IStatement prg, MyIDictionary<StringValue, BufferedReader> fileTable,
                        MyIHeap<Integer, IValue> heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = prg.deepCopy();
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = generalId;
        idChange();

        exeStack.push(prg);
    }

    public ProgramState oneStep() throws MyException {
        if (exeStack.isEmpty()) {
            throw new MyException("!EXCEPTION! Program state stack is empty");
        }

        IStatement currentStatement;
        try{
            currentStatement = exeStack.pop();
        } catch (AdtException e) {
            throw new MyException(e.getMessage());
        }
        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        return "\nProgramState{\n" + "Program id: " + id + "\n" +
                "ExeStack:\n" + BinaryTreeFromExeStack(exeStack) + "\n" +
                "SymTable:\n" + symTable.toString()+ "\n" +
                "Heap:\n" + heap.toString() + "\n" +
                "Out:\n" + out.toString() + "\n" +
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