package src.java.model.expressions;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.adt.myHeap.MyIHeap;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.MyException;
import src.java.model.types.BoolType;
import src.java.model.types.IType;
import src.java.model.types.IntType;
import src.java.model.values.BoolValue;
import src.java.model.values.IValue;
import src.java.model.values.IntValue;

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
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException {
        IValue v1, v2;
        v1 = e1.eval(symTable, heap);
        v2 = e2.eval(symTable, heap);

        if (v1.getType().equals(new IntType()) && v2.getType().equals(new IntType())) {
            IntValue i1 = (IntValue) v1;
            IntValue i2 = (IntValue) v2;
            int n1, n2;
            n1 = i1.getValue();
            n2 = i2.getValue();
            switch (op) {
                case "<":
                    return new BoolValue(n1 < n2);
                case "<=":
                    return new BoolValue(n1 <= n2);
                case "==":
                    return new BoolValue(n1 == n2);
                case "!=":
                    return new BoolValue(n1 != n2);
                case ">":
                    return new BoolValue(n1 > n2);
                case ">=":
                    return new BoolValue(n1 >= n2);
                default:
                    throw new MyException("!EXCEPTION! Invalid operator");
            }
        }
        throw new MyException("!EXCEPTION! The operands are not integers");
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws IExpressionException {
        IType type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);

        if(type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new IExpressionException("!Relational Expression Error! Second operand is not an integer");
        } else
            throw new IExpressionException("!Relational Expression Error! First operand is not an integer");
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString(){
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
