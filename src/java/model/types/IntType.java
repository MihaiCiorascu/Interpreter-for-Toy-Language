package src.java.model.types;

import src.java.model.values.IValue;
import src.java.model.values.IntValue;

public class IntType implements IType{
    public IntType() {}

    @Override
    public String toString() {
        return "integer";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof IntType;
    }

    @Override
    public IType deepCopy(){
        return new IntType();
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }
}
