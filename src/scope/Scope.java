package scope;
import errors.ErrorHandler;
import symbol.ClassSymbol;
import symbol.MethodSymbol;
import symbol.Symbol;
import symbol.VariableSymbol;

import java.util.*;

public class Scope {

    private Symbol scopeOwner;
    private final Scope enclosingScope;


    //SymbolsTable
    private final HashMap<String,VariableSymbol> variablesMap = new HashMap<>();
    private final HashMap<String,MethodSymbol> methodsMap = new HashMap<>();
    private final HashMap<String ,ClassSymbol> classesMap = new HashMap<>();


    public Scope(Symbol scopeOwner, Scope enclosingScope){
        this.scopeOwner = scopeOwner;
        this.enclosingScope = enclosingScope;
    }


    public void defineNewSymbol(Symbol symbol) {
        switch (symbol) {
            case VariableSymbol variableSymbol -> {
                if (this.variablesMap.containsKey(symbol.getSignature()))
                    ErrorHandler.duplicateSymbolError(symbol, this.scopeOwner.getName());

                this.variablesMap.put(symbol.getSignature(), variableSymbol);
            }
            case MethodSymbol methodSymbol -> {
                if (this.methodsMap.containsKey(symbol.getSignature()))
                    ErrorHandler.duplicateSymbolError(symbol, this.scopeOwner.getName());

                this.methodsMap.put(symbol.getSignature(), methodSymbol);
            }
            case ClassSymbol classSymbol -> {
                if (this.classesMap.containsKey(symbol.getSignature()))
                    ErrorHandler.duplicateSymbolError(symbol, this.scopeOwner.getName());

                this.classesMap.put(symbol.getSignature(), classSymbol);
            }
            default -> throw new IllegalArgumentException("Unexpected symbol type: " + symbol.getClass().getName());
        }
    }

    public VariableSymbol resolveVariable(String variableSymbolSignature){
        Scope currentScope = this;

        while(currentScope != null){
            if (currentScope.variablesMap.containsKey(variableSymbolSignature)) {
                return currentScope.variablesMap.get(variableSymbolSignature);

            }
            else if(currentScope.getScopeOwnerSymbol() instanceof ClassSymbol){
                //We are in upper top level. Now we have to check parent variables
                int numberOfParents = ((ClassSymbol) currentScope.getScopeOwnerSymbol()).getNumberOfParentClasses();
                for(int i = 0; i < numberOfParents; i++) {
                    String parentFieldNameCast = "super_" + (i+1) + "->"  + variableSymbolSignature;
                    if(currentScope.getVariablesMap().containsKey(parentFieldNameCast)) {
                        return currentScope.getVariablesMap().get(parentFieldNameCast);
                    }
                }
            }
            currentScope = currentScope.getEnclosingScope();
        }

        //Symbol is undefined
        return null;
    }

    public MethodSymbol resolveMethod(String methodSymbolSignature){
        Scope currentScope = this;

        while(currentScope != null){
            if(currentScope.getMethodsMap().containsKey(methodSymbolSignature))
                return currentScope.getMethodsMap().get(methodSymbolSignature);

            currentScope = currentScope.getEnclosingScope();
        }
        //Symbol is undefined
        return null;
    }
    public ClassSymbol resolveClass(String classSymbolSignature){
        Scope currentScope = this;

        while(currentScope != null){
            if(currentScope.getClassesMap().containsKey(classSymbolSignature))
                return currentScope.getClassesMap().get(classSymbolSignature);

            currentScope = currentScope.getEnclosingScope();
        }

        //Symbol is undefined
        return null;
    }



    public void setScopeOwner(Symbol scopeOwner) {
        this.scopeOwner = scopeOwner;
    }
    public Symbol getScopeOwnerSymbol(){
        return this.scopeOwner;
    }
    public Scope getEnclosingScope(){
        return this.enclosingScope;
    }
    public HashMap<String, VariableSymbol> getVariablesMap() {
        return this.variablesMap;
    }
    public HashMap<String, ClassSymbol> getClassesMap() {
        return this.classesMap;
    }
    public HashMap<String, MethodSymbol> getMethodsMap() {
        return this.methodsMap;
    }
    public String toString(){
        if(this.scopeOwner == null) System.out.println("GLOBAL SCOPE");
        else System.out.printf("Scope created from <%s> of type <%s> \n",
                this.scopeOwner.getName(), this.scopeOwner.getClass().getName().substring(7));

        int count = 1;
        for(Symbol symbol : this.classesMap.values()){
            System.out.println(count++ + " : " + symbol);
        }
        for(Symbol symbol : this.methodsMap.values()){
            System.out.println(count++ + " : " + symbol);
        }
        for(Symbol symbol : this.variablesMap.values()){
            System.out.println(count++ + " : " + symbol);
        }
        return "";
    }

}
















