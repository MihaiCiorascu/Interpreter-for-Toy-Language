package model.expressions;

import adt.myDictionary.MyIDictionary;
import adt.myHeap.MyIHeap;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

enum LogicalOperator {
    AND, OR
}

public class LogicalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private LogicalOperator operator;

    public LogicalExpression(LogicalOperator operator, IExpression left, IExpression right){
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException {
        IValue leftValue, rightValue;
        try {
            leftValue = left.eval(symTable, heap);
        } catch (IExpressionException | MyException e) {
            throw new MyException(e.getMessage());
        }

        if (leftValue.getType().equals(new BoolType())) {
            try {
                rightValue = right.eval(symTable, heap);
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
    public IExpression deepCopy(){
        return new LogicalExpression(operator, left.deepCopy(), right.deepCopy());
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }
}
