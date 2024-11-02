package model.types;

import model.values.IValue;

public interface IType {
    boolean equals(IType other);
    String toString();
    IType deepCopy();
    IValue defaultValue();
}