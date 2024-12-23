package listeners;

import antrl4.ooSimpleGrammarBaseListener;
import antrl4.ooSimpleGrammarParser;
import errors.ErrorHandler;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import org.antlr.v4.runtime.tree.TerminalNode;
import symbol.*;
import scope.*;


public class SymbolicTablePhase extends ooSimpleGrammarBaseListener {
    private final ParseTreeProperty<Scope> scopes;
    private final Scope globalScope;
    private Scope currentScope;
    private final StringBuilder methodDeclarations;

    public SymbolicTablePhase(){
        this.scopes  = new ParseTreeProperty<>();
        this.globalScope = new Scope(null, null);
        this.methodDeclarations = new StringBuilder();
        this.currentScope = null;
    }

    public ParseTreeProperty<Scope> getScopes() {
        return this.scopes;
    }
    public Scope getGlobalScope() {
        return globalScope;
    }
    public StringBuilder getMethodDeclarations(){
        return this.methodDeclarations;
    }



    ///////////////////////////////////////////////////////////////
    /// Listeners


    //Generate Global Scope
    @Override
    public void enterStartRule(ooSimpleGrammarParser.StartRuleContext context) {
        this.currentScope = this.globalScope;
    }


    //Definition Classes symbolic table
    @Override
    public void enterClass_def(ooSimpleGrammarParser.Class_defContext context) {
        String className = context.class_name(0).getText();
        int codeFileLine = context.class_name(0).getStart().getLine();

        //Save classDataType
        VariableSymbol.dataTypesRegistry.add(className);

        //Add class symbol to global scope
        ClassSymbol symbol = new ClassSymbol(className, codeFileLine);
        symbol.setNumberOfParentClasses(context.class_name().size() -1);
        this.currentScope.defineNewSymbol(symbol);

        //Create class local scope and enter
        Scope localScope = new Scope(symbol, this.currentScope);
        this.scopes.put(context, localScope);
        this.currentScope = localScope;

        symbol.setScopePointer(localScope);
    }
    @Override
    public void exitClass_def(ooSimpleGrammarParser.Class_defContext context) {
        //Inheritance
        this.inheritance(context);

        //Exit Class local Scope
        this.currentScope = this.currentScope.getEnclosingScope();
    }


    private void inheritance(ooSimpleGrammarParser.Class_defContext context){
        for(int i = 1; i < context.class_name().size(); i++){

            VariableSymbol parentClassPointerVariableChildField =
                    new VariableSymbol(true,"super_" + i
                            ,VariableSymbol.VariableKind.DECLARATION
                            ,Symbol.DataCategory.CLASS
                            ,context.class_name(i).getText()
                            ,context.class_name().getFirst().getText(),context.start.getLine());
            this.currentScope.defineNewSymbol(parentClassPointerVariableChildField);


            ClassSymbol parentClassSymbol = this.globalScope.resolveClass(
                    context.class_name(i).getText() + "$");
            Scope parentClassScope = parentClassSymbol.getScopePointer();
            Scope childScope = this.currentScope;


            for(VariableSymbol parentVariable : parentClassScope.getVariablesMap().values()){
                if(childScope.resolveVariable(parentVariable.getSignature())==  null){
                    childScope.defineNewSymbol(
                            new VariableSymbol(true,"super_" + i + "->" + parentVariable.getName()
                                    , VariableSymbol.VariableKind.DECLARATION
                            , Symbol.DataCategory.PRIMITIVE,
                                    "int",childScope.getScopeOwnerSymbol().getName(),context.start.getLine()));
                }
            }
        }
    }




    // Main class symbolic table
    @Override
    public void enterClass_main_def(ooSimpleGrammarParser.Class_main_defContext context) {
        int codeFileLine = context.start.getLine();

        //Add Main's class symbol to global scope
        ClassSymbol symbol = new ClassSymbol("Main", codeFileLine);
        this.currentScope.defineNewSymbol(symbol);

        //Create class local scope and enter
        Scope localScope = new Scope(symbol, this.currentScope);
        this.scopes.put(context, localScope);
        this.currentScope = localScope;
    }
    @Override
    public void exitClass_main_def(ooSimpleGrammarParser.Class_main_defContext context) {
        //Exit main's local Scope
        this.currentScope = this.currentScope.getEnclosingScope();
    }


    //Constructor
    @Override
    public void enterConstructor_def(ooSimpleGrammarParser.Constructor_defContext context) {
        int codeFileLine = context.start.getLine();
        StringBuilder parametersString = new StringBuilder(context.parameters().parlist().getText());
        String returnType = context.getChild(4).getText();
        String methodOwner = this.currentScope.getScopeOwnerSymbol().getName();


        //Add constructor symbol to class scope
        MethodSymbol symbol = new MethodSymbol(MethodSymbol.MethodType.CONSTRUCTOR
                ,"Constructor", parametersString.toString(), returnType, methodOwner, codeFileLine);


        //all methods are public in c, so we save the method on global scope
        this.globalScope.getMethodsMap().put(symbol.getSignature(), symbol);

        //Constructor Declaration
        this.methodDeclarations.append(generateConstructorDeclaration(symbol)).append(";\n");

        //Create local scope and enter
        Scope localScope = new Scope(symbol, this.currentScope);
        this.scopes.put(context, localScope);
        this.currentScope = localScope;
    }
    @Override
    public void exitConstructor_def(ooSimpleGrammarParser.Constructor_defContext context) {
        this.currentScope = this.currentScope.getEnclosingScope();
    }
    public static String generateConstructorDeclaration(MethodSymbol constructorSymbol){
        StringBuilder localStringBuilder = new StringBuilder();

        //Return Type
        localStringBuilder.append(constructorSymbol.getMethodOwner()).append("* ");

        //Name
        localStringBuilder.append(constructorSymbol.getSignature());

        //ParametersDataTypes
        localStringBuilder.append("(");
        int parametersCount = constructorSymbol.getParameters().size();
        for(String dataType : constructorSymbol.getParameters().values()){
            localStringBuilder.append(dataType)
                    .append(!dataType.equals("int") ? "*" : "")
                    .append(parametersCount-- - 1 != 0 ? ", " : "");
        }
        localStringBuilder.append(")");

        return localStringBuilder.toString();
    }



    //Method
    @Override
    public void enterMethod_def(ooSimpleGrammarParser.Method_defContext context) {
        String name = context.ID().getText();
        String parameters = context.parameters().parlist().getText();
        String returnType = context.getChild(4).getText();
        String methodOwner = this.currentScope.getScopeOwnerSymbol().getName();
        int codeFileLine = context.start.getLine();

        //Add method symbol to class scope
        MethodSymbol symbol = new MethodSymbol(MethodSymbol.MethodType.REGULAR_METHOD,name, parameters, returnType, methodOwner, codeFileLine);

        //all methods are public in c, so we save the method on global scope
        this.globalScope.getMethodsMap().put(symbol.getSignature(), symbol);

        //Method Declaration
        this.methodDeclarations.append(generateMethodDeclaration(symbol)).append(";\n");

        //Create method local scope and enter
        Scope localScope = new Scope(symbol, this.currentScope);
        this.scopes.put(context, localScope);
        this.currentScope = localScope;
    }
    @Override
    public void exitMethod_def(ooSimpleGrammarParser.Method_defContext context) {
        //Exit method Scope
        this.currentScope = this.currentScope.getEnclosingScope();
    }

    public static String generateMethodDeclaration(MethodSymbol methodSymbol){
        StringBuilder localStringBuilder = new StringBuilder();

        //Return Type
        localStringBuilder.append(methodSymbol.getReturnType().equals("-") ? "void " : methodSymbol.getReturnType().equals("int") ? "int " : methodSymbol.getReturnType() + "* ");

        //Method Name
        localStringBuilder.append(methodSymbol.getSignature());

        //ParametersDataTypes
        localStringBuilder.append("(").append(methodSymbol.getMethodOwner()).append("*");
        for(String dataType : methodSymbol.getParameters().values()){
            localStringBuilder.append(", ").append(dataType)
                    .append(!(dataType.equals("void") ||  dataType.equals("int") )? "*" : "");
        }
        localStringBuilder.append(")");

        return localStringBuilder.toString();
    }


    //Main method definition
    @Override
    public void enterMethod_main_def(ooSimpleGrammarParser.Method_main_defContext context) {
        int codeFileLine = context.start.getLine();
        String name = context.getChild(1).getText();
        String parameters = context.getChild(3).getText();
        String returnType = context.getChild(6).getText();
        String methodOwner = this.currentScope.getScopeOwnerSymbol().getName();


        //Create main's method symbol and add it to class scope
        MethodSymbol symbol = new MethodSymbol(MethodSymbol.MethodType.MAIN,name, parameters, returnType, methodOwner, codeFileLine);

        //all methods are public in c, so we save the method on global scope
        this.globalScope.getMethodsMap().put(symbol.getSignature(), symbol);


        //Main method has the same scope as the main class. We just update the owner of the current scope
        this.currentScope.setScopeOwner(symbol);
    }



    //parameters
    @Override
    public void exitParameters(ooSimpleGrammarParser.ParametersContext context) {
        String[] parameters = context.parlist().getText().split(",");

        for (int i = 1; i < parameters.length; i++) {
            VariableSymbol newParameter = this.generateParameter(parameters[i], context);
            this.currentScope.defineNewSymbol(newParameter);
        }
    }
    private VariableSymbol generateParameter(String parameter , ooSimpleGrammarParser.ParametersContext context){
        String parameterOwner = this.currentScope.getScopeOwnerSymbol().getName();
        String dataType = this.extractDataType(parameter, context);
        String name = parameter.substring(dataType.length());
        VariableSymbol.VariableKind kind = VariableSymbol.VariableKind.PARAMETER;
        Symbol.DataCategory dataCategory =
                dataType.equals("int") ? Symbol.DataCategory.PRIMITIVE : Symbol.DataCategory.CLASS;
        int symbolLine = context.parlist().start.getLine();

        return new VariableSymbol(name, kind, dataCategory, dataType,parameterOwner, symbolLine);
    }
    private String extractDataType(String parameter, ooSimpleGrammarParser.ParametersContext context){
        String dataType = null;
        for(String registeredDataType : VariableSymbol.dataTypesRegistry){
            if(parameter.startsWith(registeredDataType)){
                dataType = registeredDataType;
            }
        }
        if(dataType == null) ErrorHandler.undefinedDataType(String.valueOf(context.start.getLine())
                ,context.start.getLine());

        return dataType;
    }


    //Declaration line
    @Override
    public void exitDecl_line(ooSimpleGrammarParser.Decl_lineContext context) {
        for (int i = 0; i < context.ID().size(); i++) {
            VariableSymbol newDeclaration = this.generateDeclaration(context.ID(i), context);
            this.currentScope.defineNewSymbol(newDeclaration);
        }
        //Save current scope for code generator
        this.scopes.put(context,this.currentScope);
    }
    private VariableSymbol generateDeclaration(TerminalNode declarationTerminalNode, ooSimpleGrammarParser.Decl_lineContext context){
        String declarationVariableOwner = this.currentScope.getScopeOwnerSymbol().getName();
        String dataType = context.types().getText();
        VariableSymbol.VariableKind kind = VariableSymbol.VariableKind.DECLARATION;
        Symbol.DataCategory dataCategory =
                dataType.equals("int") ? Symbol.DataCategory.PRIMITIVE : Symbol.DataCategory.CLASS;
        String name = declarationTerminalNode.getText();
        int symbolLine = declarationTerminalNode.getSymbol().getLine();

       return new VariableSymbol(name, kind, dataCategory, dataType,declarationVariableOwner, symbolLine);
    }




    //Scope saving

    @Override
    public void exitDeclarations(ooSimpleGrammarParser.DeclarationsContext context) {
       //save Scope for later
        this.scopes.put(context,this.currentScope);
    }

    @Override
    public void exitAssigmentToExpression(ooSimpleGrammarParser.AssigmentToExpressionContext context) {
        //Save current scope for mapping phase
        this.scopes.put(context,this.currentScope);
    }

    @Override
    public void exitDirect_call_stat(ooSimpleGrammarParser.Direct_call_statContext context) {
        //Save current scope for later
        this.scopes.put(context,this.currentScope);
    }

    @Override
    public void exitConstructor_call(ooSimpleGrammarParser.Constructor_callContext context) {
        //Save current scope for late manage
        this.scopes.put(context,this.currentScope);
    }

    @Override
    public void exitFactorVariableReference(ooSimpleGrammarParser.FactorVariableReferenceContext context){
        //Save current scope for later manage
        this.scopes.put(context, this.currentScope);
    }

    @Override
    public void exitFactorVariableFunctionInvocation(ooSimpleGrammarParser.FactorVariableFunctionInvocationContext context) {
        //Save current scope for later manage
        this.scopes.put(context, this.currentScope);
    }

    @Override
    public void exitFactorConstructorInvocation(ooSimpleGrammarParser.FactorConstructorInvocationContext context) {
        this.scopes.put(context, this.currentScope);
    }

    @Override
    public void exitFactorIDdotID(ooSimpleGrammarParser.FactorIDdotIDContext context) {
        this.scopes.put(context, this.currentScope);
    }

    @Override
    public void exitAssignmentPlainConstructorCall(ooSimpleGrammarParser.AssignmentPlainConstructorCallContext context) {
        this.scopes.put(context, this.currentScope);
    }

    @Override
    public void exitReturn_stat(ooSimpleGrammarParser.Return_statContext context) {
        this.scopes.put(context, this.currentScope);
    }

    @Override
    public void exitMethod_body(ooSimpleGrammarParser.Method_bodyContext context) {
        this.scopes.put(context, this.currentScope);
    }

    @Override
    public void exitInput_stat(ooSimpleGrammarParser.Input_statContext context) {
        this.scopes.put(context,currentScope);
    }
}
