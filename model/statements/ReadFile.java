package model.statements;

import exceptions.AdtExceptions.MyIDictionaryException;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement{
    private IExpression exp;
    private String varName;

    public ReadFile(IExpression exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        if (!prg.getSymTable().isDefined(varName)) {
            throw new MyException("!EXCEPTION! The variable " + varName + " is not defined");
        }

        IValue varValue;
        try {
            varValue = prg.getSymTable().lookup(varName);
        } catch (MyIDictionaryException e) {
            throw new MyException(e.getMessage());
        }
        if (!varValue.getType().equals(new IntType())) {
            throw new MyException("!EXCEPTION! The variable " + varName + " is not an integer");
        }

        IValue fileNameValue;
        try {
            fileNameValue = exp.eval(prg.getSymTable(), prg.getHeap());
        } catch (IExpressionException | MyException e) {
            throw new MyException(e.getMessage());
        }
        if (!fileNameValue.getType().equals(new StringType())) {
            throw new MyException("!EXCEPTION! The expression is not a string");
        }

        StringValue filename = (StringValue) fileNameValue;
        BufferedReader br;
        try {
            br = prg.getFileTable().lookup(filename);
        } catch (MyIDictionaryException e) {
            throw new MyException(e.getMessage());
        }
        if (br == null) {
            throw new MyException("!EXCEPTION! The file " + filename.getValue() + " is not opened");
        }

        try {
            String line = br.readLine();
            IntValue val;
            if (line == null) {
                val = new IntValue(0);
            } else {
                try{
                    val = new IntValue(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    throw new MyException("!EXCEPTION! The file " + filename.getValue() + " contains a non-integer value");
                }
            }
            try {
                prg.getSymTable().update(varName, val);
            } catch (MyIDictionaryException e) {
                throw new MyException(e.getMessage());
            }
        }  catch (IOException e) {
            throw new MyException("!EXCEPTION! Error reading from file: " + e.getMessage());
        }

        return prg;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + varName + ")";
    }
}
