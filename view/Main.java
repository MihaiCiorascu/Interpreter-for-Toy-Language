package view;

import adt.myDictionary.*;
import adt.myList.*;
import adt.myStack.*;
import controller.Controller;
import exceptions.*;
import model.ProgramState;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.*;
import repository.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static IStatement createExample1() {
        return new CompoundStatement(
                new VarDeclStatement("v", new IntType()),
                new CompoundStatement((new AssignmentStatement("v", new ValueExpression(new IntValue(2)))),
                        new PrintStatement(new VariableExpression("v"))));
    }

    private static IStatement createExample2() {
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
        return new CompoundStatement(new VarDeclStatement("a", new BoolType()), new CompoundStatement(
                new VarDeclStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("a",
                new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(
                        new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2)
        )), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(
                new VariableExpression("v"))))));
    }

    public static void main(String[] args) throws MyException {
        Scanner scanner = new Scanner(System.in);
        IStatement selectedProgram = null;

        while (selectedProgram == null){
            System.out.println("1. int v; v=2; Print(v)");
            System.out.println("2. int a; int b; a=2+3*5; b=a+1; Print(b)");
            System.out.println("3. bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)");
            System.out.println("\nSelect which program to execute (1-3)");

            try{
                int choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        selectedProgram = createExample1();
                        break;
                    case 2:
                        selectedProgram = createExample2();
                        break;
                    case 3:
                        selectedProgram = createExample3();
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine();
            }
        }

        MyIStack<IStatement> stack = new MyStack<>();
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIList<IValue> output = new MyList<>();

        ProgramState prg = new ProgramState(stack, symTable, output, selectedProgram);
        IRepository repo = new Repository(prg);
        Controller ctrl = new Controller(repo);

        try{
            ctrl.allSteps();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
