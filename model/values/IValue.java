package model.values;

import model.types.BoolType;
import model.types.IType;

public interface IValue {
    public IType getType();
    public boolean equals(IValue other);
    public String toString();
    public IValue deepCopy();
    public IValue defaultValue();
}
