package model.statements;

import adt.myDictionary.MyIDictionary;
import exceptions.AdtExceptions.MyIDictionaryException;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class CloseRFile implements IStatement{
    private IExpression exp;

    public CloseRFile(IExpression exp){
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IValue value;
        try {
            value = exp.eval(prg.getSymTable());
        } catch (IExpressionException | MyException e) {
            throw new MyException(e.getMessage());
        }
        if (!value.getType().equals(new StringType())) {
            throw new MyException("!EXCEPTION! The expression is not a string");
        }
        StringValue stringValue = (StringValue) value;

        BufferedReader br;
        try {
            br = prg.getFileTable().lookup(stringValue);
        } catch (MyIDictionaryException e) {
            throw new MyException(e.getMessage());
        }
        if (br == null) {
            throw new MyException("!EXCEPTION! The file" + stringValue.getValue() + " is not opened");
        }

        try {
            br.close();
        } catch (Exception e) {
            throw new MyException("!EXCEPTION! Error closing the file: " + e.getMessage());
        }

        prg.getFileTable().put(stringValue, null);

        return prg;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }
}
