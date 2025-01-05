package src.java.model.values;

import src.java.model.types.IType;

public interface IValue {
    public IType getType();
    public boolean equals(IValue other);
    public String toString();
    public IValue deepCopy();
}
