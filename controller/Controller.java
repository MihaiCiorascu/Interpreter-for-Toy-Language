package controller;

import adt.myStack.MyIStack;
import exceptions.AdtExceptions.MyIStackException;
import exceptions.MyException;
import model.ProgramState;
import model.statements.IStatement;
import repository.IRepository;

public class Controller {
    private IRepository repository;

    public Controller(IRepository repository){
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        if (stack.isEmpty()) {
            throw new MyException("Program state stack is empty");
        }

        IStatement currentStatement;
        try{
            currentStatement = stack.pop();
        } catch (MyIStackException e) {
            throw new MyException(e.getMessage());
        }
        return currentStatement.execute(state);
    }

    public void allSteps() throws MyException {
        ProgramState currentState = repository.getCurrentPrg();
        System.out.println(currentState);
        while (!currentState.getStack().isEmpty()) {
            oneStep(currentState);
            System.out.println(currentState);
        }
    }
}
