package view;

import adt.myDictionary.*;
import adt.myList.*;
import adt.myStack.*;
import controller.Controller;
import model.ProgramState;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.*;
import repository.*;
import view.command.ExitCommand;
import view.command.RunExample;

import java.io.BufferedReader;

public class Interpreter {
    private static IStatement createExample1() {
        // int v; v=2; Print(v)
        return new CompoundStatement(
                new VarDeclStatement("v", new IntType()),
                new CompoundStatement((new AssignmentStatement("v", new ValueExpression(new IntValue(2)))),
                        new PrintStatement(new VariableExpression("v"))));
    }

    private static IStatement createExample2() {
        // int a; int b; a=2+3*5; b=a+1; Print(b)
        return new CompoundStatement(new VarDeclStatement("a", new IntType()), new CompoundStatement(
                new VarDeclStatement("b", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',
                        new ValueExpression(new IntValue(2)), new ArithmeticExpression('*',
                        new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                        new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+',
                                new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                new PrintStatement(new VariableExpression("b"))))));
    }

    private static IStatement createExample3() {
        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        return new CompoundStatement(new VarDeclStatement("a", new BoolType()), new CompoundStatement(
                new VarDeclStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("a",
                new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(
                new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2)
        )), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(
                new VariableExpression("v"))))));
    }

    private static IStatement createExample4() {
        // string varf; varf = "test.in"; openRFile(varf); int varc; readFile(varf,
        // varc); Print(varc); readFile(varf, varc); Print(varc); closeRFile(varf);
        return new CompoundStatement(
                new VarDeclStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenRFile(new VariableExpression("varf")),
                                new CompoundStatement(new VarDeclStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))))))))));
    }

    private static IStatement error1() {
        // ERROR| int a; bool b; a=b
        return new CompoundStatement(new VarDeclStatement("a", new IntType()), new CompoundStatement(
                new VarDeclStatement("b", new BoolType()), new AssignmentStatement("a", new VariableExpression("b"))));
    }

    private static IStatement error2() {
        // ERROR| int a; int b; b=30; int c; c=b/a
        return new CompoundStatement(new VarDeclStatement("a", new IntType()), new CompoundStatement(
                new VarDeclStatement("b", new IntType()), new CompoundStatement(new AssignmentStatement("b",
                new ValueExpression(new IntValue(30))),
                new CompoundStatement(new VarDeclStatement("c", new IntType()), new AssignmentStatement("c",
                        new ArithmeticExpression('/', new VariableExpression("b"), new VariableExpression("a")))))));
    }

    private static ProgramState createProgram(IStatement originalProgram){
        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> output = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();

        return new ProgramState(stack, symTable, output, originalProgram, fileTable);
    }

    private static Controller createController(IStatement statement, String logFilePath) {
        ProgramState state = createProgram(statement);
        IRepository repo = new Repository(state, logFilePath);

        return new Controller(repo);
    }

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();

        menu.addCommand(new RunExample("1", createExample1(), createController(createExample1(), "log1.txt")));
        menu.addCommand(new RunExample("2", createExample2(), createController(createExample2(), "log2.txt")));
        menu.addCommand(new RunExample("3", createExample3(), createController(createExample3(), "log3.txt")));
        menu.addCommand(new RunExample("4", createExample4(), createController(createExample4(), "log4.txt")));
        menu.addCommand(new RunExample("5", error1(), createController(error1(), "log5.txt")));
        menu.addCommand(new RunExample("6", error2(), createController(error2(), "log6.txt")));
        menu.addCommand(new ExitCommand("0", "Exit"));

        menu.show();
    }
}