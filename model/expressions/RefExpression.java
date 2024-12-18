package model.expressions;

import adt.myDictionary.MyIDictionary;
import adt.myHeap.MyIHeap;
import exceptions.AdtException;
import exceptions.IExpressionException;
import exceptions.MyException;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class RefExpression implements IExpression {
    private IExpression expression;

    public RefExpression(IExpression expression){
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> symTable, MyIHeap<Integer, IValue> heap) throws MyException, IExpressionException {
        IValue value = expression.eval(symTable, heap);
        if (!(value instanceof RefValue)) {
            throw new IExpressionException("!EXCEPTION! Expected a RefValue, got " + value);
        }

        RefValue refValue = (RefValue) value;
        Integer address = refValue.getAddress();
        if (!heap.isDefined(address)) {
            throw new IExpressionException("!EXCEPTION! Address " + address + " is not defined in the heap");
        }
        try {
            return heap.get(address);
        } catch (AdtException e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnv) throws IExpressionException {
        IType type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        }
        else
            throw new IExpressionException("!RefExp Error! The rH argument is not a RefType");
    }

    @Override
    public IExpression deepCopy(){
        return new RefExpression(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "RefExp(" + expression.toString() + ")";
    }
}