package model.statements;

import exceptions.IExpressionException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStatement{
    private IExpression exp;

    public OpenRFile(IExpression exp){
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

        if (prg.getFileTable().isDefined(stringValue)) {
            throw new MyException("!EXCEPTION! The file " + stringValue.getValue() + " is already opened");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(stringValue.getValue()));
            prg.getFileTable().put(stringValue, br);
        } catch (FileNotFoundException e) {
            throw new MyException("!EXCEPTION! File not found: " + e.getMessage());
        }

        return prg;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }
}
