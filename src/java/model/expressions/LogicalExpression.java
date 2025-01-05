package src.java.model.expressions;

import src.java.adt.myDictionary.MyIDictionary;
import src.java.adt.myHeap.MyIHeap;
import src.java.exceptions.IExpressionException;
import src.java.exceptions.MyException;
import src.java.model.types.BoolType;
import src.java.model.types.IType;
import src.java.model.values.BoolValue;
import src.java.model.values.IValue;

enum LogicalOperator {
    AND, OR
}

public class LogicalExpression implements IExpression {
    private IExpression leftOperator;
    private IExpression rightOperator;
    private LogicalOperator operator;

    public LogicalExpression(LogicalOperator operator, IExpression left, IExpression right){
        this.leftOperator = left;
        this.rightOperator = right;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException {
        IValue leftValue, rightValue;
        try {
            leftValue = leftOperator.eval(symTable, heap);
        } catch (IExpressionException | MyException e) {
            throw new MyException(e.getMessage());
        }

        if (leftValue.getType().equals(new BoolType())) {
            try {
                rightValue = rightOperator.eval(symTable, heap);
            } catch (IExpressionException | MyException e) {
                throw new MyException(e.getMessage());
            }
            if (rightValue.getType().equals(new BoolType())) {
                BoolValue leftBool = (BoolValue) leftValue;
                BoolValue rightBool = (BoolValue) rightValue;

                if (operator == LogicalOperator.AND)
                    if (leftBool.getValue() && rightBool.getValue())
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);
                else if (leftBool.getValue() || rightBool.getValue())
                    return new BoolValue(true);
                else
                    return new BoolValue(false);
            } else
                throw new MyException("!EXCEPTION! The right operand is not boolean value");
        }
        else
            throw new MyException("!EXCEPTION! The left operand is not boolean value");
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws IExpressionException {
        IType type1, type2;
        type1 = this.leftOperator.typeCheck(typeEnv);
        type2 = this.rightOperator.typeCheck(typeEnv);

        if(type1.equals(new BoolType())) {
            if(type2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new IExpressionException("!Logical Expression Error! The right operand is not a bool");
        } else
            throw new IExpressionException("!Logical Expression Error! The left operand is not a bool");
    }

    @Override
    public IExpression deepCopy(){
        return new LogicalExpression(operator, leftOperator.deepCopy(), rightOperator.deepCopy());
    }

    @Override
    public String toString() {
        return leftOperator.toString() + " " + operator + " " + rightOperator.toString();
    }
}
