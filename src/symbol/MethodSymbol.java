package symbol;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MethodSymbol extends Symbol{

    private final String returnType;
    private final String methodOwner;
    private final HashMap<String, String> parameters;
    private final String paremetersString;
    private final MethodType methodType;


    public enum MethodType {
        CONSTRUCTOR,
        REGULAR_METHOD,
        MAIN
    }

    public MethodSymbol(MethodType methodType, String methodName, String paremetersString, String returnType,
                        String methodOwner, int tokenLine) {
        super(methodName, null , tokenLine);
        this.methodType = methodType;
        this.returnType = returnType;
        this.methodOwner = methodOwner;
        this.paremetersString = paremetersString;
        this.parameters = new LinkedHashMap<>();
        String parametersSignature = this.generateParametersSignature();
        super.signature = generateMethodSignature(this.methodType, this.methodOwner, methodName, parametersSignature);
    }

    public static String generateMethodSignature(MethodType methodType, String methodOwner, String methodName,
                                                 String parametersSignature){
        return methodType.equals(MethodType.CONSTRUCTOR)
                ? methodOwner + "_" + methodName + parametersSignature
                : methodName + "_" + methodOwner + parametersSignature;
    }

    public MethodType getMethodType() {
        return this.methodType;
    }

    private String generateParametersSignature(){
        String[] parametersList = this.paremetersString.split(",");
        StringBuilder parametersDataTypeSignature = new StringBuilder();

        for (int i = 1; i < parametersList.length; i++) {
            for (String registeredDataType : VariableSymbol.dataTypesRegistry) {
                if (parametersList[i].startsWith(registeredDataType)) {
                    this.parameters.put(parametersList[i].substring(registeredDataType.length()), registeredDataType);
                    parametersDataTypeSignature.append("_").append(registeredDataType);
                }
            }
        }

        return parametersDataTypeSignature.toString();
    }

    public String getReturnType() {
        return this.returnType;
    }

    public String getMethodOwner() {
        return this.methodOwner;
    }

    public HashMap<String, String> getParameters() {
        return this.parameters;
    }

    @Override
    public String toString() {
        return "MethodSymbol{" +
                "returnType='" + returnType + '\'' +
                ", methodOwner='" + methodOwner + '\'' +
                ", name='" + name + '\'' +
                ", parameters=" + parameters +
                ", methodType'" + methodType + '\'' +
                '}';
    }
}
