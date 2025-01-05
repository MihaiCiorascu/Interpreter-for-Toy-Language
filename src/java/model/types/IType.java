package src.java.model.types;

import src.java.model.values.IValue;

public interface IType {
    boolean equals(Object other);
    String toString();
    IType deepCopy();
    IValue defaultValue();
}