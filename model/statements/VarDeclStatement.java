package model.statements;

import adt.myDictionary.MyIDictionary;
import exceptions.MyException;
import model.ProgramState;
import model.types.IType;
import model.values.IValue;

public class VarDeclStatement implements IStatement {
    private String varName;
    private IType type;

    public VarDeclStatement(String varName, IType type) {
        this.varName = varName;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (symTable.isDefined(this.varName)) {
            throw new MyException("!EXCEPTION! Variable '" + varName + "' already declared");
        }
        symTable.put(this.varName, this.type.defaultValue());

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(this.varName, this.type.deepCopy());
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.varName;
    }
}