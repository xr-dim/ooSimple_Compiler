package symbol;

import java.util.HashSet;
import java.util.Set;

public class VariableSymbol extends Symbol {

    public final static HashSet<String> dataTypesRegistry = new HashSet<>(Set.of("int"));

    private final String variableOwner;
    private final VariableKind variableKind;
    private final DataCategory dataCategory;
    private final String dataType;
    private final boolean parentPointer;


    public enum VariableKind{
        DECLARATION,
        PARAMETER,
    }


    public VariableSymbol(String name, VariableKind variableKind, DataCategory dataCategory, String dataType,
                          String variableOwner, int tokenLine) {
        super(name,name , tokenLine);
        this.variableKind = variableKind;
        this.dataCategory = dataCategory;
        this.variableOwner = variableOwner;
        this.parentPointer = false;
        dataTypesRegistry.add(this.dataType = dataType);
    }

    public VariableSymbol(Boolean parentPointer, String name, VariableKind variableKind, DataCategory dataCategory,
                          String dataType, String variableOwner, int tokenLine) {
        super(name,name , tokenLine);
        this.variableKind = variableKind;
        this.dataCategory = dataCategory;
        this.variableOwner = variableOwner;
        this.parentPointer = parentPointer;
        this.dataType = dataType;
    }

    public String getDataType() {
        return this.dataType;
    }

    public DataCategory getDataCategory() {
        return this.dataCategory;
    }

    public VariableKind getVariableKind() {
        return this.variableKind;
    }

    public boolean isParentPointer() {
        return this.parentPointer;
    }

    @Override
    public String toString() {
        return "VariableSymbol{" +
                "variableOwner='" + variableOwner + '\'' +
                ", name='" + name + '\'' +
                ", variableKind=" + variableKind +
                ", dataCategory=" + dataCategory +
                ", dataType='" + dataType + '\'' +
                ", Signature='" + signature + '\'' +
                '}';
    }
}

