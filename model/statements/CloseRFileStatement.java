package model.statements;

import adt.myDictionary.MyIDictionary;
import exceptions.AdtException;
import exceptions.IExpressionException;
import exceptions.IStatementException;
import exceptions.MyException;
import model.ProgramState;
import model.expressions.IExpression;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{
    private IExpression expression;

    public CloseRFileStatement(IExpression exp){
        this.expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState prg) throws IStatementException {
        IValue value;
        try {
            value = expression.eval(prg.getSymTable(), prg.getHeap());
        } catch (IExpressionException | MyException e) {
            throw new IStatementException(e.getMessage());
        }
        if (!value.getType().equals(new StringType())) {
            throw new IStatementException("!EXCEPTION! The expression is not a string");
        }
        StringValue stringValue = (StringValue) value;

        BufferedReader br;
        try {
            br = prg.getFileTable().lookup(stringValue);
        } catch (AdtException e) {
            throw new IStatementException(e.getMessage());
        }
        if (br == null) {
            throw new IStatementException("!EXCEPTION! The file" + stringValue.getValue() + " is not opened");
        }

        try {
            br.close();
        } catch (IOException e) {
            throw new IStatementException("!EXCEPTION! Error closing the file: " + e.getMessage());
        }

        prg.getFileTable().put(stringValue, null);

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException {
        IType expressionType = expression.typeCheck(typeEnv);
        if (expressionType instanceof StringType) {
            return typeEnv;
        }
        else{
            throw new IStatementException("Close File Statement Error: Argument provided is not a string");
        }
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
}
