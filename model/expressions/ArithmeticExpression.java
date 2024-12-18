package model.expressions;


import adt.myDictionary.MyIDictionary;
import adt.myHeap.MyIHeap;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.types.*;
import model.values.*;

import java.lang.reflect.Type;

public class ArithmeticExpression implements IExpression{
    IExpression e1;
    IExpression e2;
    char op;

    public ArithmeticExpression(char op, IExpression e1, IExpression e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException {
        IValue v1, v2;
        v1 = e1.eval(symTable, heap);

        if (v1 == null) throw new MyException("!EXCEPTION! First operand evaluation is null");

        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(symTable, heap);

            if (v2 == null) throw new MyException("!EXCEPTION! Second operand evaluation is null");

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
                    if (n2 == 0) throw new IExpressionException("!EXCEPTION! Division by zero");
                    return new IntValue(n1 / n2);
                } else
                    throw new MyException("!EXCEPTION! Invalid operator. Operator must be +, -, *, /");
            } else
                throw new MyException("!EXCEPTION! Second operand is not an integer");
        } else
            throw new MyException("!EXCEPTION! First operand is not an integer");
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws IExpressionException {
        IType type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            }
            else
                throw  new IExpressionException("!Arithmetic Expression Error! Second operand is not an integer");
        }
        else
            throw new IExpressionException("!Arithmetic Expression Error! First operand is not an integer");
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