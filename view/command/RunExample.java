package view.command;

import controller.Controller;
import exceptions.IControllerException;
import exceptions.IExpressionException;
import exceptions.IStatementException;
import exceptions.MyException;
import model.statements.IStatement;

public class RunExample extends Command {
    private Controller controller;
    private boolean hasBeenExecuted;

    public RunExample(String key, IStatement statement, Controller controller) {
        super(key, statement.toString());
        this.controller = controller;
        this.hasBeenExecuted = false;
    }

    @Override
    public void execute() {
        if (hasBeenExecuted) {
            System.out.println("The program has already been executed!");
            return;
        }
        try {
            controller.allSteps();
            hasBeenExecuted = true;
        } catch (IControllerException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean hasBeenExecuted() {
        return hasBeenExecuted;
    }
}
