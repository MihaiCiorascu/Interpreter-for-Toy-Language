package adt.myStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import exceptions.AdtExceptions.MyIStackException;
import exceptions.MyException;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack(){
        this.stack = new Stack<>();
    }

    public Stack<T> getStack(){
        return this.stack;
    }

    @Override
    public T pop() throws MyIStackException {
        if(stack.isEmpty())
            throw new MyIStackException("!EXCEPTION! Cannot pop. Empty stack");
        return stack.pop();
    }

    @Override
    public T top() throws MyIStackException {
        if(stack.isEmpty())
            throw new MyIStackException("!EXCEPTION! Cannot top. Empty stack");
        return stack.peek();
    }

    @Override
    public void push(T v){
        stack.push(v);
    }

    @Override
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    @Override
    public int size(){
        return stack.size();
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (T el: this.stack.reversed())
            s.append(el).append("\n");
        return s.toString();
    }
}