package model.values;

import model.types.*;

public class IntValue implements IValue {
    private int value;

    public IntValue() {
        this.value = 0;
    }

    public IntValue(int defValue) {
        this.value = defValue;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(IValue other) {
        if (other.getType().equals(this.getType())) {
            return this.value == ((IntValue) other).getValue();
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public IValue deepCopy(){
        return new IntValue(this.value);
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }
}
