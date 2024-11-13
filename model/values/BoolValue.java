package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue{
    boolean value;

    public BoolValue(boolean defValue){
        this.value = defValue;
    }

    public boolean getValue(){
        return this.value;
    }

    @Override
    public IType getType(){
        return new BoolType();
    }

    @Override
    public boolean equals(IValue other){
        return other instanceof BoolValue && ((BoolValue) other).value == this.value;
    }

    @Override
    public String toString(){
        return " " + this.value;
    }

    @Override
    public IValue deepCopy(){
        return new BoolValue(this.value);
    }
}
