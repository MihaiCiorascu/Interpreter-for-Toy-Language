package model.values;


import model.types.*;

public class StringValue implements IValue{
    private String value;

    public StringValue(String defValue){this.value = defValue;}

    public String getValue(){return this.value;}

    @Override
    public IType getType() {return new StringType();}

    @Override
    public boolean equals(IValue other) {
        return other instanceof StringValue && ((StringValue) other).value.equals(value);
    }

    @Override
    public String toString(){return " " + this.value;}

    @Override
    public IValue deepCopy(){return new StringValue(this.value);}
}
