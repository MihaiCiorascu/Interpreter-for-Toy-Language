package src.java.model.statements;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.IStatementException;
import src.java.exceptions.MyException;
import src.java.model.ProgramState;
import src.java.model.expressions.IExpression;
import src.java.model.types.IType;
import src.java.model.types.StringType;
import src.java.model.values.IValue;
import src.java.model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStatement implements IStatement{
    private IExpression expression;

    public OpenRFileStatement(IExpression exp){
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
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnv) throws IStatementException{
        IType typeExp = expression.typeCheck(typeEnv);
        if (typeExp instanceof StringType)
            return typeEnv;
        else
            throw new IStatementException("!Open File Statement error! Variable is not a String");
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }
}
