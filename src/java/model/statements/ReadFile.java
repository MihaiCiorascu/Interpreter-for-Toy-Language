package src.java.model.statements;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.exceptions.AdtException;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.IStatementException;
import src.java.exceptions.MyException;
import src.java.model.ProgramState;
import src.java.model.expressions.IExpression;
import src.java.model.types.IType;
import src.java.model.types.IntType;
import src.java.model.types.StringType;
import src.java.model.values.IValue;
import src.java.model.values.IntValue;
import src.java.model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement{
    private IExpression expression;
    private String varName;

    public ReadFile(IExpression exp, String varName) {
        this.expression = exp;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState prg) throws IStatementException {
        if (!prg.getSymTable().isDefined(varName)) {
            throw new IStatementException("!EXCEPTION! The variable " + varName + " is not defined");
        }

        IValue varValue;
        try {
            varValue = prg.getSymTable().lookup(varName);
        } catch (AdtException e) {
            throw new IStatementException(e.getMessage());
        }
        if (!varValue.getType().equals(new IntType())) {
            throw new IStatementException("!EXCEPTION! The variable " + varName + " is not an integer");
        }

        IValue fileNameValue;
        try {
            fileNameValue = expression.eval(prg.getSymTable(), prg.getHeap());
        } catch (IExpressionException | MyException e) {
            throw new IStatementException(e.getMessage());
        }
        if (!fileNameValue.getType().equals(new StringType())) {
            throw new IStatementException("!EXCEPTION! The expression is not a string");
        }

        StringValue filename = (StringValue) fileNameValue;
        BufferedReader br;
        try {
            br = prg.getFileTable().lookup(filename);
        } catch (AdtException e) {
            throw new IStatementException(e.getMessage());
        }
        if (br == null) {
            throw new IStatementException("!EXCEPTION! The file " + filename.getValue() + " is not opened");
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
                    throw new IStatementException("!EXCEPTION! The file " + filename.getValue() + " contains a non-integer value");
                }
            }
            try {
                prg.getSymTable().update(varName, val);
            } catch (AdtException e) {
                throw new IStatementException(e.getMessage());
            }
        }  catch (IOException e) {
            throw new IStatementException("!EXCEPTION! Error reading from file: " + e.getMessage());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException{
        IType typeVar = typeEnv.lookup(this.varName);
        IType typeExp = expression.typeCheck(typeEnv);
        if (typeVar instanceof IntType) {
            if (typeExp instanceof StringType)
                return typeEnv;
            else
                throw new IStatementException("!Read File Error! Expression is not of type String");
        }
        else
            throw new IStatementException("!Read File Error! Variable is not of type Int");
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(expression.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + varName + ")";
    }
}
