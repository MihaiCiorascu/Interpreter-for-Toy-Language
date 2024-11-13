package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{
    public StringType(){}

    @Override
    public String toString(){return "String";}

    @Override
    public boolean equals(Object other){return other instanceof StringType;}

    @Override
    public IType deepCopy(){return new StringType();}

    @Override
    public IValue defaultValue(){return new StringValue("");}
}
