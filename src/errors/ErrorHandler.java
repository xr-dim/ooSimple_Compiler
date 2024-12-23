package errors;

import symbol.*;

public class ErrorHandler {

    public static void duplicateSymbolError(Symbol symbol, String scopeOwnerName) {
        System.err.println("Error at line : " + symbol.getTokenLine());
        System.err.printf("Symbol <%s> of type <%s> is already defined in current scope (Current_Scope : %s).",
                symbol.getName(), symbol.getClass().getName().substring(7), scopeOwnerName);
        System.exit(-1);
    }

    public static void undefinedSymbol(String name, String type, int codeLine) {
        System.err.printf("Error at line : %s\n", codeLine);
        System.err.printf("Symbol <%s> of type <%s> is not declared.", name, type);
        System.exit(-1);

    }

    public static void undefinedConstructor(String name, String parameters, int codeLine) {
        System.err.printf("Error at line : %s\n", codeLine);
        System.err.printf("Constructor <%s> with parameters %s is not declared.", name, parameters);
        System.exit(-1);
    }


    public static void undeclaredStructField(String parameterName , String structName ,int line){
        System.err.println("Error at line : " + line);
        System.err.println("Parameter <" + parameterName + "> is not a declared field of struct <" + structName + ">");
        System.exit(-1);
    }

    public static void undefinedDataType(String s, int line) {
        System.err.println("Undefined data type at <" + s + "> at line :" + line);
        System.exit(-1);
    }


    

    public static void assignmentsDataTypesConflict(String parameterDataType, String assigmentRightPartDataType, int line){
        System.err.println("Error at line : " + line);
        System.err.println("Expression of data type <" + assigmentRightPartDataType + "> cannot be assigned to a " +
                "parameter of data type <" + parameterDataType + ">");
        System.exit(-1);
    }

    public static void expressionDataTypesConflict(String parameterDataType, String assigmentRightPartDataType, int line){
        System.err.println("Error at line : " + line);
        System.err.println("dataType of <" + assigmentRightPartDataType + "> cannot" +
                " have an operation with data type of <" + parameterDataType + ">");
        System.exit(-1);
    }

    public static void returnStatementDataTypeConflict(String methodReturnDataType, String returnedExpressionDataType, int line){
        System.err.println("Error at line : " + line);
        System.err.println("Method return data type is <" + methodReturnDataType + "> but returned data have a <" + returnedExpressionDataType
        +"> data Type. DATA TYPE CONFLICT");
        System.exit(-1);
    }

    public static void inputStatementDataTypeConflict(String variableName , String variableType , int line){
        System.err.println("Error at line : " + line);
        System.err.println("Variable <" + variableName +"> of dataType <" + variableType +"> cannot be an argument of an input statement.");
        System.err.println("Input statement target must be <int>");
        System.exit(-1);
    }

    public static void relationalOperationDataTypeConflict(String dataType1, String dataType2, int line){
        System.err.println("Error at line : " + line);
        System.err.println("Cannot compare expression of dataType <" + dataType1 + "> with an expression of dataType <" +dataType2 + ">");
        System.exit(-1);
    }
}


