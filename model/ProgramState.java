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

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, IValue> symTable,
                        MyIList<IValue> out, IStatement prg, MyIDictionary<StringValue, BufferedReader> fileTable,
                        MyIHeap<Integer, IValue> heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = prg.deepCopy();
        this.fileTable = fileTable;
        this.heap = heap;

        exeStack.push(prg);
    }

    @Override
    public String toString() {
        return "\nProgramState{\n" + "ExeStack:\n" + BinaryTreeFromExeStack(exeStack) + "\n" +
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

    public Set<Integer> getUsedAddresses() {
        Set<Integer> usedAddresses = new HashSet<>();
        for (IValue value : symTable.getValues()) {
            if (value instanceof RefValue) {
                usedAddresses.add(((RefValue) value).getAddress());
            }
        }

        for (IValue value : this.heap.getValues()) {
            if (value instanceof RefValue) {
                usedAddresses.add(((RefValue) value).getAddress());
            }
        }

        return usedAddresses;
    }
}