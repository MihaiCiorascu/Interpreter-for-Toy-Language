package view.command;

import controller.Controller;
import exceptions.MyException;
import model.statements.IStatement;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, IStatement statement, Controller controller) {
        super(key, statement.toString());
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allSteps();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
