package src.java.view;

import src.java.adt.myDictionary.*;
import src.java.adt.myHeap.MyHeap;
import src.java.adt.myHeap.MyIHeap;
import src.java.adt.myList.*;
import src.java.adt.myStack.*;
import src.java.controller.Controller;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.IStatementException;
import src.java.model.ProgramState;
import src.java.model.expressions.*;
import src.java.model.statements.*;
import src.java.model.types.*;
import src.java.model.values.*;
import src.java.repository.*;
import src.java.view.command.ExitCommand;
import src.java.view.command.RunExample;

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
                        new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VarDeclStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFileStatement(new VariableExpression("varf"))))))))));
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

    private static IStatement createExample5() {
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        return new CompoundStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapNewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new HeapNewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));
    }

    private static IStatement createExample6() {
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        return new CompoundStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapNewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new HeapNewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new RefExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression('+',
                                                        new RefExpression(new RefExpression(new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5)))))))));
    }

    private static IStatement createExample7() {
        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        return new CompoundStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapNewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new RefExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+',
                                                new RefExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5))))))));
    }

    private static IStatement createExample8() {
        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        return new CompoundStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapNewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new HeapNewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new HeapNewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new RefExpression(new RefExpression(new VariableExpression("a")))))))));
    }

    private static IStatement createExample9() {
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        return new CompoundStatement(
                new VarDeclStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
    }

    private static IStatement createExample10() {
        // int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        return new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new VarDeclStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                        new CompoundStatement(new HeapNewStatement("a", new ValueExpression(new IntValue(22))),
                                new CompoundStatement(new ForkStatement(
                                        new CompoundStatement(new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new RefExpression(new VariableExpression("a"))))))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new RefExpression(new VariableExpression("a"))))
                                )
                        )
                        )
                )
        );
    }

    private static IStatement createExample11() {
        // Ref(int) a; int v; new(a,10); fork(v=20; fork(wH(a, 40); print(rH(a));); print(v);); v=30; print(v); print(rH(a));
        return new CompoundStatement(
                new VarDeclStatement("a", new RefType(new IntType())),
                new CompoundStatement(
                        new VarDeclStatement("v", new IntType()),
                        new CompoundStatement(
                                new HeapNewStatement("a", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
                                                        new CompoundStatement(
                                                                new ForkStatement(
                                                                        new CompoundStatement(
                                                                                new HeapWriteStatement("a", new ValueExpression(new IntValue(40))),
                                                                                new PrintStatement(new RefExpression(new VariableExpression("a")))
                                                                        )
                                                                ),
                                                                new PrintStatement(new VariableExpression("v"))
                                                        )
                                                )
                                        ),
                                        new CompoundStatement(
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new RefExpression(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static IStatement createExample12() {
        // string varf; varf="test.in"; openRFile(varf); fork(int varc; readFile(varf, varc); print(varc);); int varc; readFile(varf, varc); print(varc); closeRFile(varf);
        return new CompoundStatement(
                new VarDeclStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new VarDeclStatement("varc", new IntType()),
                                                        new CompoundStatement(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new PrintStatement(new VariableExpression("varc"))
                                                        )
                                                )
                                        ),
                                        new CompoundStatement(
                                                new VarDeclStatement("varc", new IntType()),
                                                new CompoundStatement(
                                                        new ReadFile(new VariableExpression("varf"), "varc"),
                                                        new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("varc")),
                                                                new CloseRFileStatement(new VariableExpression("varf"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static IStatement createExample13() {
        // int v; int v;
        return new CompoundStatement(new VarDeclStatement("v", new IntType()), new VarDeclStatement("v", new IntType()));

    }

    private static ProgramState createProgram(IStatement originalProgram){
        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> output = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap<Integer, IValue> heap = new MyHeap<>();

        return new ProgramState(stack, symTable, output, originalProgram, fileTable, heap);
    }

    private static Controller createController(String exampleNumber, IStatement statement, String logFilePath) {
        try {
            statement.typeCheck(new MyDictionary<>());
        } catch (IStatementException | IExpressionException e) {
            System.out.println("Example " + exampleNumber + ": " + e.getMessage());
            return null;
        }

        ProgramState state = createProgram(statement);
        IRepository repo = new Repository(state, logFilePath);

        return new Controller(repo);
    }

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();

        addExampleToMenu(menu, "1", createExample1(), "log1.txt");
        addExampleToMenu(menu, "2", createExample2(), "log2.txt");
        addExampleToMenu(menu, "3", createExample3(), "log3.txt");
        addExampleToMenu(menu, "4", createExample4(), "log4.txt");
        addExampleToMenu(menu, "5", createExample5(), "log5.txt");
        addExampleToMenu(menu, "6", createExample6(), "log6.txt");
        addExampleToMenu(menu, "7", createExample7(), "log7.txt");
        addExampleToMenu(menu, "8", createExample8(), "log8.txt");
        addExampleToMenu(menu, "9", createExample9(), "log9.txt");
        addExampleToMenu(menu, "10", createExample10(), "log10.txt");
        addExampleToMenu(menu, "11", createExample11(), "log11.txt");
        addExampleToMenu(menu, "12", createExample12(), "log12.txt");
        addExampleToMenu(menu, "13", createExample13(), "log13.txt");
        menu.addCommand(new ExitCommand("0", "Exit"));

        menu.show();
    }

    private static void addExampleToMenu(TextMenu menu, String key, IStatement statement, String logFilePath) {
        Controller controller = createController(key, statement, logFilePath);
        if (controller != null) {
            menu.addCommand(new RunExample(key, statement, controller));
        }
    }
}