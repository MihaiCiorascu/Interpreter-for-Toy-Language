package model.expressions;


import adt.myDictionary.MyIDictionary;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.types.*;
import model.values.*;

public class ArithmeticExpression implements IExpression{
    IExpression e1;
    IExpression e2;
    char op;

    public ArithmeticExpression(char op, IExpression e1, IExpression e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public IValue eval(MyIDictionary<String, IValue> table) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(table);

        if (v1 == null) throw new MyException("First operand evaluation is null");

        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(table);

            if (v2 == null) throw new MyException("Second operand evaluation is null");

            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();

                if (op == '+') return new IntValue(n1 + n2);
                if (op == '-') return new IntValue(n1 - n2);
                if (op == '*') return new IntValue(n1 * n2);
                if (op == '/') {
                    if (n2 == 0) throw new IExpressionException("Division by zero");
                    return new IntValue(n1 / n2);
                } else
                    throw new MyException("Invalid operator. Operator must be +, -, *, /");
            } else
                throw new MyException("Second operand is not an integer");
        } else
            throw new MyException("First operand is not an integer");
    }

    @Override
    public IExpression deepCopy(){
        return new ArithmeticExpression(op, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
