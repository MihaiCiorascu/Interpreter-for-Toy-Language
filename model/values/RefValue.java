package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue{
    private int address;
    private IType locationType;

    public RefValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType(){
        return new RefType(locationType);
    }

    public int getAddress(){
        return this.address;
    }

    @Override
    public IValue deepCopy(){
        return new RefValue(address, locationType);
    }

    @Override
    public String toString(){
        return "RefValue(" + address + ", " + locationType.toString() + ")";
    }

    @Override
    public boolean equals(IValue other){
        return other instanceof RefValue && ((RefValue) other).address == address;
    }
}
