package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType{
    private IType inner;

    public RefType(IType inner){
        this.inner = inner;
    }

    public IType getInner(){
        return this.inner;
    }

    @Override
    public IType deepCopy(){
        return new RefType(inner.deepCopy());
    }

    @Override
    public IValue defaultValue(){
        return new RefValue(0, inner);
    }

    public boolean equals(Object other){
        if (other instanceof RefType){
            return inner.equals(((RefType) other).getInner());
        }
        return false;
    }

    @Override
    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }
}
