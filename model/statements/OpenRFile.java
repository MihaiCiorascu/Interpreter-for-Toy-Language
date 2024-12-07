package model.statements;

import exceptions.IExpressionException;
import exceptions.IStatementException;
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
    public ProgramState execute(ProgramState prg) throws IStatementException {
        IValue value;
        try {
            value = exp.eval(prg.getSymTable(), prg.getHeap());
        } catch (IExpressionException | MyException e) {
            throw new IStatementException(e.getMessage());
        }

        if (!value.getType().equals(new StringType())) {
            throw new IStatementException("!EXCEPTION! The expression is not a string");
        }
        StringValue stringValue = (StringValue) value;

        if (prg.getFileTable().isDefined(stringValue)) {
            throw new IStatementException("!EXCEPTION! The file " + stringValue.getValue() + " is already opened");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(stringValue.getValue()));
            prg.getFileTable().put(stringValue, br);
        } catch (FileNotFoundException e) {
            throw new IStatementException("!EXCEPTION! File not found: " + e.getMessage());
        }

        return null;
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
