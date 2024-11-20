package model;

import adt.myBinaryTree.BinaryTreeBuilder;
import adt.myBinaryTree.ITreeBuilder;
import adt.myBinaryTree.MyIBinaryTree;
import adt.myDictionary.MyDictionary;
import adt.myDictionary.MyIDictionary;
import adt.myList.MyIList;
import adt.myList.MyList;
import adt.myStack.MyIStack;
import adt.myStack.MyStack;
import model.statements.IStatement;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, IValue> symTable;
    MyIList<IValue> out;
    IStatement originalProgram;
    MyIDictionary<StringValue, BufferedReader> fileTable;

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

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, IValue> symTable,
                        MyIList<IValue> out, IStatement prg, MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = prg.deepCopy();
        this.fileTable = fileTable;

        exeStack.push(prg);
    }

    @Override
    public String toString() {
        return "\nProgramState{\n" + "ExeStack:\n" + BinaryTreeFromExeStack(exeStack) + "\n" +
                "SymTable:\n" + symTable.toString()+ "\n" +
                "Out:\n" + out.toString() + "\n" +
                "OriginalProgram: " + originalProgram.toString() + "\n" +
                "FileTable:\n"+ fileTable.toString() + "\n" +
                "Heap:\n"+ "\n}" +
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