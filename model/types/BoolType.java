package model.types;

import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class BoolType implements IType {
    public BoolType(){}

    @Override
    public String toString(){
        return "boolean";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof BoolType;
    }

    @Override
    public IType deepCopy(){
        return new BoolType();
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}


