package controller;

import adt.myHeap.MyIHeap;
import adt.myStack.MyIStack;
import exceptions.AdtExceptions.MyIStackException;
import exceptions.MyException;
import model.ProgramState;
import model.statements.IStatement;
import model.values.IValue;
import repository.IRepository;

public class Controller {
    private IRepository repository;

    public Controller(IRepository repository){
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState state) throws MyException {
        MyIStack<IStatement> stack = state.getStack();
        if (stack.isEmpty()) {
            throw new MyException("!EXCEPTION! Program state stack is empty");
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
        repository.logPrgStateExec(currentState);
        while (!currentState.getStack().isEmpty()) {
            oneStep(currentState);
            repository.logPrgStateExec(currentState);

            MyIHeap<Integer, IValue> heap = currentState.getHeap();
            heap.setHeap(heap.safeGarbageCollector(currentState.getUsedAddresses(), heap.getHeap()));
        }
    }
}