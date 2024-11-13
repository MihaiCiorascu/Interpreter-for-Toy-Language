package model.expressions;

import adt.myDictionary.MyIDictionary;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExpression{
    IExpression e1;
    IExpression e2;
    String op;

    public RelationalExpression(IExpression e1, IExpression e2, String op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable) throws MyException, IExpressionException {
        IValue v1, v2;
        v1 = e1.eval(symTable);
        v2 = e2.eval(symTable);

        if (v1.getType().equals(new IntType()) && v2.getType().equals(new IntType())) {
            IntValue i1 = (IntValue) v1;
            IntValue i2 = (IntValue) v2;
            int n1, n2;
            n1 = i1.getValue();
            n2 = i2.getValue();
            switch (op) {
                case "<":
                    return new IntValue(n1 < n2 ? 1 : 0);
                case "<=":
                    return new IntValue(n1 <= n2 ? 1 : 0);
                case "==":
                    return new IntValue(n1 == n2 ? 1 : 0);
                case "!=":
                    return new IntValue(n1 != n2 ? 1 : 0);
                case ">":
                    return new IntValue(n1 > n2 ? 1 : 0);
                case ">=":
                    return new IntValue(n1 >= n2 ? 1 : 0);
                default:
                    throw new MyException("!EXCEPTION! Invalid operator");
            }
        }
        throw new MyException("!EXCEPTION! The operands are not integers");
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(e1.deepCopy(), e2.deepCopy(), op);
    }
}
