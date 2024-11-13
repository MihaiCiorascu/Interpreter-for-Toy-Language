package model.values;

import model.types.*;

public class IntValue implements IValue {
    private int value;

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
        return other instanceof IntValue && ((IntValue) other).value == this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public IValue deepCopy(){
        return new IntValue(this.value);
    }
}
