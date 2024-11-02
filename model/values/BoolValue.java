package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue{
    boolean value;

    public BoolValue(){
        this.value = false;
    }

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
    public boolean equals(IValue newValue){
        IType valueType = newValue.getType();
        if(!valueType.equals(this.getType()))
            return false;

        return ((BoolValue) newValue).getValue() == this.value;
    }

    @Override
    public String toString(){
        return " " + this.value;
    }

    @Override
    public IValue deepCopy(){
        return new BoolValue(this.value);
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
