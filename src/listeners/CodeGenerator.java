package listeners;
import antrl4.ooSimpleGrammarBaseListener;
import antrl4.ooSimpleGrammarParser;
import errors.ErrorHandler;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import scope.*;
import symbol.ClassSymbol;
import symbol.MethodSymbol;
import symbol.Symbol;
import symbol.VariableSymbol;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class CodeGenerator extends ooSimpleGrammarBaseListener {
    private final static int standardLibraries = 0;
    private final static int structs = 1;
    /// ///////////////////////methods declarations = 2; we did it in symbolicTablePhase
    private final static int constructorImplementation = 4;
    private final static int methodImplementation = 5;
    private final static int mainMethodImplementation = 3;


    private final ArrayList<StringBuilder> stringBuilders;
    private final ParseTreeProperty<String> rulesEvaluation;
    private final ParseTreeProperty<Scope> scopes;
    private final Scope globalScope;
    private int tabCount = 1;

    private final HashMap<String, ArrayList<String>> inheritanceLog = new HashMap<>();


    public CodeGenerator(Scope globalScope, ParseTreeProperty<Scope> scopes,
                         StringBuilder symbolicTableMethodsDeclarations) {
        this.globalScope = globalScope;
        this.scopes = scopes;
        this.rulesEvaluation = new ParseTreeProperty<>();
        this.stringBuilders = new ArrayList<>(List.of(
                new StringBuilder(),
                new StringBuilder("//Structs declaration\n"),
                new StringBuilder("//Methods Declarations\n").append(symbolicTableMethodsDeclarations),
                new StringBuilder(),
                new StringBuilder("//Constructors Implementations\n"),
                new StringBuilder("//Methods Implementations\n")));
    }

    public void printToFile(String outputFileName) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./compiler_output/" + outputFileName));

        this.stringBuilders.forEach(stringBuilder -> {
            try {
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        bufferedWriter.flush();
        bufferedWriter.close();
    }



    /// /////////////////////////////////////////////////////////////////////////////////////////////

    //Start Rule
    @Override
    public void enterStartRule(ooSimpleGrammarParser.StartRuleContext context) {
        this.checkMainClassIsDeclared(context);
        this.addStandardLibraries();
    }

    private void addStandardLibraries() {
        this.stringBuilders.get(standardLibraries).append("#include <stdio.h>\n#include <stdlib.h>\n");
    }

    private void checkMainClassIsDeclared(ooSimpleGrammarParser.StartRuleContext context) {
        if (this.globalScope.resolveClass("Main$") == null) {
            ErrorHandler.undefinedSymbol("Main", "CLASS", context.start.getLine());
        }
    }



    /// /////////////////////////////////////////////////////////////////////////////////////////////


    //Struct instance declaration
    @Override
    public void enterClass_def(ooSimpleGrammarParser.Class_defContext context) {
        String structName = context.class_name(0).getText();
        String structStringPrint = "typedef struct " + structName + " {" + "\n";
        this.stringBuilders.get(structs).append(structStringPrint);


        this.inheritanceLog.put(structName + "$", new ArrayList<>());
        for(int i = 1; i < context.class_name().size(); i++){
            this.inheritanceLog.get(structName + "$")
                    .add(context.class_name(i).getText());
        }
    }

    @Override
    public void exitClass_def(ooSimpleGrammarParser.Class_defContext context) {
        String structName = context.class_name(0).getText();
        String structStringPrint = "} " + structName + ";\n\n";
        this.stringBuilders.get(structs).append(structStringPrint);
    }


    /// /////////////////////////////////////////////////////////////////////////////////////////////


    //Constructors
    @Override
    public void enterConstructor_def(ooSimpleGrammarParser.Constructor_defContext context) {
        Scope constructorScope = this.scopes.get(context);
        MethodSymbol constructorSymbol = (MethodSymbol) constructorScope.getScopeOwnerSymbol();

        this.generateConstructorDeclaration(constructorSymbol);
        this.generateConstructorDefinition(constructorScope,constructorSymbol);
    }

    private void generateConstructorDeclaration(MethodSymbol constructorSymbol) {
        StringBuilder sb = new StringBuilder();

        //Return Type
        sb.append(constructorSymbol.getMethodOwner()).append("* ");

        //Name
        sb.append(constructorSymbol.getSignature());

        //Parameters
        sb.append("(");
        int parametersCount = constructorSymbol.getParameters().size();
        for (var entry : constructorSymbol.getParameters().entrySet()) {
            String parameterName = entry.getKey();
            String dataType = entry.getValue();
            sb.append(dataType).append(" ").append(parameterName)
                    .append(parametersCount-- - 1 != 0 ? ", " : "");
        }

        sb.append(")");
        this.stringBuilders.get(constructorImplementation).append(sb);
    }

    private void generateConstructorDefinition(Scope constructorScope, MethodSymbol constructorSymbol) {
        this.stringBuilders.get(constructorImplementation).append("{\n");
        this.generateSelfParameter(constructorSymbol);
        this.instantiateParents(constructorScope);
    }

    private void generateSelfParameter(MethodSymbol constructorSymbol) {
        this.stringBuilders.get(constructorImplementation)
                .append("\t")
                .append(constructorSymbol.getReturnType()).append("* ")
                .append("str_").append(" = ")
                .append("(").append(constructorSymbol.getReturnType()).append("*").append(") ")
                .append("malloc(sizeof(").append(constructorSymbol.getReturnType()).append("));\n");
    }


    private void instantiateParents(Scope constructorScope){
        constructorScope.getEnclosingScope().getVariablesMap().values()
                .stream().filter(VariableSymbol::isParentPointer)
                .filter(v -> v.getDataCategory() == Symbol.DataCategory.CLASS)
                .forEach(v -> this.stringBuilders.get(constructorImplementation)
                        .append("\t")
                        .append("str_->")
                        .append(v.getName())
                        .append(" = ")
                        .append("(").append(v.getDataType()).append("*").append(") ")
                        .append("malloc(sizeof(").append(v.getDataType()).append("));\n"));
    }

    @Override
    public void exitConstructor_def(ooSimpleGrammarParser.Constructor_defContext context) {
        this.stringBuilders.get(constructorImplementation).append("\treturn str_;").append("\n}\n");
    }





    /// /////////////////////////////////////////////////////////////////////////////////////////////


    //Methods
    @Override
    public void enterMethod_def(ooSimpleGrammarParser.Method_defContext context) {
        Scope methodScope = this.scopes.get(context);
        MethodSymbol methodSymbol = (MethodSymbol) methodScope.getScopeOwnerSymbol();
        this.generateMethodDeclaration(methodSymbol);
        this.generateMethodDefinition();

    }

    private void generateMethodDeclaration(MethodSymbol methodSymbol) {
        StringBuilder methodDeclaration = new StringBuilder();

        //Return Type
        methodDeclaration.append(methodSymbol.getReturnType().equals("-") ? "void " : methodSymbol.getReturnType().equals("int") ? "int " : methodSymbol.getReturnType() + "* ");

        //Method Name
        methodDeclaration.append(methodSymbol.getSignature());

        //ParametersDataTypes
        methodDeclaration.append("(").append(methodSymbol.getMethodOwner()).append("*").append(" str_");
        methodSymbol.getParameters().forEach((parameterName, parameterDataType) -> {
            methodDeclaration.append(", ")
                    .append(parameterDataType)
                    .append(!(parameterDataType.equals("void") || parameterDataType.equals("int")) ? "*" : "")
                    .append(" ").append(parameterName);
        });
        methodDeclaration.append(")");

        this.stringBuilders.get(methodImplementation).append(methodDeclaration);
    }

    private void generateMethodDefinition() {
        this.stringBuilders.get(methodImplementation).append("{\n");
    }

    @Override
    public void exitMethod_def(ooSimpleGrammarParser.Method_defContext ctx) {
        this.stringBuilders.get(methodImplementation).append("}\n\n");
    }

    @Override
    public void exitMethod_body(ooSimpleGrammarParser.Method_bodyContext context) {
        Scope currentScope = this.scopes.get(context);
        MethodSymbol.MethodType  methodType = ((MethodSymbol) currentScope.getScopeOwnerSymbol()).getMethodType();

        this.stringBuilders.get(methodType == MethodSymbol.MethodType.REGULAR_METHOD ? methodImplementation
                        : methodType == MethodSymbol.MethodType.CONSTRUCTOR ? constructorImplementation : mainMethodImplementation)
                .append(this.rulesEvaluation.get(context.statements()));
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////




    //Declarations
    @Override
    public void exitDeclarations(ooSimpleGrammarParser.DeclarationsContext context) {
        Scope currentScope = this.scopes.get(context);
        Symbol symbol = currentScope.getScopeOwnerSymbol();
        StringBuilder sb = new StringBuilder();

        currentScope.getVariablesMap()
                .values()
                .stream()
                .filter(v -> v.getVariableKind() == VariableSymbol.VariableKind.DECLARATION)
                .filter(v -> !(v.getDataCategory() == Symbol.DataCategory.PRIMITIVE && v.isParentPointer()))
                .forEach(v ->
                        sb.append("\t")
                                .append(v.getDataType())
                                .append(v.getDataType().equals("int") ? "" : "*")
                                .append(" ")
                                .append(v.getName()).append(";" + "\n"));

        if (symbol instanceof ClassSymbol) {
            this.stringBuilders.get(symbol.getSignature().equals("Main$") ? mainMethodImplementation : structs).append(sb);
        } else if (symbol instanceof MethodSymbol) {
            MethodSymbol.MethodType methodType = ((MethodSymbol) symbol).getMethodType();
            this.stringBuilders.get(
                    methodType == MethodSymbol.MethodType.CONSTRUCTOR ? constructorImplementation :
                            methodType == MethodSymbol.MethodType.REGULAR_METHOD ? methodImplementation :
                                    mainMethodImplementation).append(sb);
        }
    }




    /// /////////////////////////////////////////////////////////////////

    //global Methods
    private String generateFunctionSignature(String functionName, String functionOwnerStruct, ArrayList<String> functionParameters) {
        StringBuilder signature = new StringBuilder(functionName).append("_").append(functionOwnerStruct);
        for (String element : functionParameters) {
            //No parameters
            if (element.isEmpty()) continue;

            signature.append("_").append(element, 0, element.indexOf("@"));
        }

        return signature.toString();
    }

    private String functionArgumentsPrintFormat(ArrayList<String> arguments) {
        return arguments.stream()
                .map(arg -> arg.substring(arg.indexOf("@") + 1))
                .collect(Collectors.joining(", "));
    }



    /////////////////////////////////////////////////////////////////////////////////////////////
    //assignment_stat
    @Override
    public void exitAssigmentToExpression(ooSimpleGrammarParser.AssigmentToExpressionContext context) {
        if (context.getText().startsWith("self."))
            structFieldAssignments(context);
        else
            localAssignments(context);
    }
    private void structFieldAssignments(ooSimpleGrammarParser.AssigmentToExpressionContext context) {
        Scope methodScope = this.scopes.get(context);
        Scope structScope = methodScope.getEnclosingScope();

        String ooSimpleParameterName  = context.getText().substring("self.".length(), context.getText().indexOf("="));
        String assigmentRightPart = this.rulesEvaluation.get(context.expression());

        VariableSymbol parameterSymbol;
        //Check struct field is declared
        if ((parameterSymbol = structScope.resolveVariable(ooSimpleParameterName)) == null)
            ErrorHandler.undeclaredStructField(ooSimpleParameterName, structScope.getScopeOwnerSymbol().getName(),
                    context.start.getLine());

        String parameterName = parameterSymbol.getName();


        String parameterDataType = structScope.getVariablesMap().get(parameterName).getDataType();
        String assigmentRightPartDataType = assigmentRightPart.substring(0, assigmentRightPart.indexOf("@"));

        //Check assigment dataTypes Match
        if (!parameterDataType.equals(assigmentRightPartDataType))
            ErrorHandler.assignmentsDataTypesConflict(parameterDataType, assigmentRightPartDataType,context.start.getLine());

        this.rulesEvaluation.put(context.parent ,
                "str_->"
                        + parameterName
                        + " = "
                        + assigmentRightPart.substring(assigmentRightPart.indexOf("@") + 1));
    }
    private void localAssignments(ooSimpleGrammarParser.AssigmentToExpressionContext context) {
        Scope methodScope = this.scopes.get(context);

        String ooSimpleParameterName  = context.getText().substring(0, context.getText().indexOf("="));
        String assigmentRightPart = this.rulesEvaluation.get(context.expression());

        //Check parameter is declared
        VariableSymbol parameterSymbol;
        if ((parameterSymbol = methodScope.resolveVariable(ooSimpleParameterName))== null)
            ErrorHandler.undefinedSymbol(ooSimpleParameterName, "Local parameter", context.start.getLine());
        String parameterName = parameterSymbol.getName();


        String parameterDataType = parameterSymbol.getDataType();
        String assigmentRightPartDataType = assigmentRightPart.substring(0, assigmentRightPart.indexOf("@"));

        //Check assigment Data Types match
        if (!parameterDataType.equals(assigmentRightPartDataType))
            ErrorHandler.assignmentsDataTypesConflict(parameterDataType, assigmentRightPartDataType,context.start.getLine());


        this.rulesEvaluation.put(context.parent,parameterName
                + " = "
                + assigmentRightPart.substring(assigmentRightPart.indexOf("@") + 1));
    }


    @Override
    public void exitAssignmentPlainConstructorCall(ooSimpleGrammarParser.AssignmentPlainConstructorCallContext context) {
        this.rulesEvaluation.put(context.parent,
                this.rulesEvaluation.get(context.constructor_call()) //remove dataType@
                        .substring(this.rulesEvaluation.get(context.constructor_call()).indexOf("@")+1));
    }


    private ArrayList<String> generateFunctionParameters(String parametersString){
        parametersString = parametersString.substring(1, parametersString.length()-1);
        return new ArrayList<>(Arrays.asList(parametersString.split(",")));
    }








    /// ///////////////////////////////////////////
    ///direct_call_stat
    @Override
    public void exitDirect_call_stat(ooSimpleGrammarParser.Direct_call_statContext context) {
        Scope methodScope = this.scopes.get(context);
        String ooSimpleParameterName = context.ID().getText();

        //Check parameter is declared
        VariableSymbol parameterSymbol;
        if ((parameterSymbol = methodScope.resolveVariable(ooSimpleParameterName)) == null)
            ErrorHandler.undefinedSymbol(ooSimpleParameterName, "Local parameter", context.start.getLine());


        String parameterDataType = parameterSymbol.getDataType();
        String functionName = context.func_call().ID().getText();
        ArrayList<String> functionParameters
                = this.generateFunctionParameters(this.rulesEvaluation.get(context.func_call().arguments()));


        //FunctionOwner = ParameterDataType
        String functionSignature = this.generateFunctionSignature(functionName, parameterDataType, functionParameters);
        MethodSymbol functionCallSymbol = this.globalScope.resolveMethod(functionSignature);


        StringBuilder selfParameterCastedType = new StringBuilder(parameterSymbol.getName());

        //Check parent class if this method is declared
        if(functionCallSymbol == null){
            List<String> classesIamInheriting = this.inheritanceLog.get(parameterSymbol.getDataType() + "$");
            for(int i = 0; i < classesIamInheriting.size(); i++){
                parameterDataType = classesIamInheriting.get(i);
                functionSignature = this.generateFunctionSignature(functionName, parameterDataType, functionParameters);
                functionCallSymbol = this.globalScope.resolveMethod(functionSignature);
                if(functionCallSymbol != null){
                    selfParameterCastedType.append("->super_").append(i+1);
                    break;
                }
            }
        }


        //Certainly undefined method at this point
        if(functionCallSymbol == null){
            ErrorHandler.undefinedSymbol(functionName, "method ", context.start.getLine());
        }

        this.rulesEvaluation.put(context.parent, functionSignature + "(" +  selfParameterCastedType
                + (!functionParameters.getFirst().isEmpty() ? "," : "")
                + this.functionArgumentsPrintFormat(functionParameters)
                + ")");
    }



    ///////////////////////////////////////////////////////
    ///if_stat

    @Override
    public void enterIf_stat(ooSimpleGrammarParser.If_statContext context) {
        tabCount++;
    }

    @Override
    public void exitIf_stat(ooSimpleGrammarParser.If_statContext context) {
        StringBuilder ifStat = new StringBuilder("if(" + this.rulesEvaluation.get(context.condition()) + "){\n");
        if(this.rulesEvaluation.get(context.statements()) != null)
            ifStat.append(this.rulesEvaluation.get(context.statements()));
        ifStat.append("\t".repeat(--tabCount)).append("}");
        ifStat.append(this.rulesEvaluation.get(context.else_part()));
        this.rulesEvaluation.put(context.parent, ifStat.toString());
    }


    @Override
    public void exitElse_part(ooSimpleGrammarParser.Else_partContext context) {
        String elsePart = "else {\n" + this.rulesEvaluation.get(context.statements())
                + "\t".repeat(tabCount - 1) + "}";
        this.rulesEvaluation.put(context, this.rulesEvaluation.get(context.statements()) == null ? "" : elsePart);
    }



    /// ///////////////////////////////////////////////////
    ///while_stat
    @Override
    public void enterWhile_stat(ooSimpleGrammarParser.While_statContext context) {
        tabCount++;
    }

    @Override
    public void exitWhile_stat(ooSimpleGrammarParser.While_statContext context) {
        StringBuilder whileStat = new StringBuilder("while(" + this.rulesEvaluation.get(context.condition()) + "){\n");
        if(this.rulesEvaluation.get(context.statements()) != null)
            whileStat.append(this.rulesEvaluation.get(context.statements()));
        whileStat.append("\t".repeat(--tabCount)).append("}");
        this.rulesEvaluation.put(context.parent, whileStat.toString());
    }





    /// /////////////////////////////
    ///Return_stat
    @Override
    public void exitReturn_stat(ooSimpleGrammarParser.Return_statContext context) {
        //Skip constructor return. We always print it anyway on the constructor listener
        if(context.getChild(1).getText().equals("self")) return;

        if(context.getChild(1).getText().equals("self.")) {
            this.selfDotIDReturn(context);
        }else{
            this.expressionReturn(context);
        }
    }



    private void selfDotIDReturn(ooSimpleGrammarParser.Return_statContext context){
        Scope currentScope = this.scopes.get(context);
        MethodSymbol methodSymbol = (MethodSymbol) currentScope.getScopeOwnerSymbol();
        String structName = methodSymbol.getMethodOwner();
        ClassSymbol structSymbol = this.globalScope.resolveClass(structName + "$");
        //resolve cant be null because if a class is a method symbol owner, it certainly exists on global scope
        Scope structScope = structSymbol.getScopePointer();


        VariableSymbol structFieldVariableSymbol = structScope.resolveVariable(context.ID().getText());
        if(structFieldVariableSymbol == null)
            ErrorHandler.undeclaredStructField(context.ID().getText(),structName,context.start.getLine());


        if(!methodSymbol.getReturnType().equals(structFieldVariableSymbol.getDataType())){
            ErrorHandler.returnStatementDataTypeConflict(methodSymbol.getReturnType(),
                    structFieldVariableSymbol.getDataType(),context.start.getLine());
        }

        this.rulesEvaluation.put(context.parent, "return str_->" + structFieldVariableSymbol.getName());
    }
    private void expressionReturn(ooSimpleGrammarParser.Return_statContext context){
        Scope currentScope = this.scopes.get(context);

        String expression = this.rulesEvaluation.get(context.expression());
        String expressionDataType = expression.substring(0, expression.indexOf("@"));
        String methodReturnDataType = ((MethodSymbol) currentScope.getScopeOwnerSymbol()).getReturnType();

        if(!(methodReturnDataType.equals(expressionDataType)))
            ErrorHandler.returnStatementDataTypeConflict(methodReturnDataType,expressionDataType,context.start.getLine());

        this.rulesEvaluation.put(context.parent, "return " + expression.substring(expression.indexOf("@") + 1));


    }




    /// ///////////////////////////////////
    ///input_stat

    @Override
    public void exitInput_stat(ooSimpleGrammarParser.Input_statContext context) {
        Scope currentScope = this.scopes.get(context);
        String targetVariableOooSimpleName = context.ID().getText();

        VariableSymbol targerVariableSymbol = currentScope.resolveVariable(targetVariableOooSimpleName);
        if(targerVariableSymbol == null)
            ErrorHandler.undefinedSymbol(targetVariableOooSimpleName,"variable",context.start.getLine());

        if(!targerVariableSymbol.getDataType().equals("int"))
            ErrorHandler.inputStatementDataTypeConflict(targetVariableOooSimpleName,targerVariableSymbol.getDataType(),context.start.getLine());

        this.rulesEvaluation.put(context.parent, "scanf(\"%d\",&" + targerVariableSymbol.getName()+ ")");
    }



    /// ////////////////////////////////
    ///print_stat
    @Override
    public void exitPrint_stat(ooSimpleGrammarParser.Print_statContext context) {
        StringBuilder printFormat = new StringBuilder("\"%d\"");
        StringBuilder printArguments = new StringBuilder(
                this.rulesEvaluation.get(context.expression().getFirst())
                        .substring(this.rulesEvaluation.get(context.expression().getFirst()).indexOf("@") + 1));


        for(int i = 1; i < context.expression().size(); i++){
            printFormat.append("\" %d\"");
            printArguments.append(" ,").
                    append(this.rulesEvaluation.get(context.expression(i))
                            .substring(this.rulesEvaluation.get(context.expression(i)).indexOf("@") + 1));
        }

        printFormat.append("\"\\n\"");
        this.rulesEvaluation.put(context.parent, "printf(" + printFormat + ", " + printArguments + ")");
    }


    //print string statement
    @Override
    public void exitPrint_string_stat(ooSimpleGrammarParser.Print_string_statContext context) {
        this.rulesEvaluation.put(context.parent, "printf("+ context.STRING_LITERAL().getText() + ")");
    }



    /// ////////////////////////////////////////
    ///
    //constructor_call
    @Override
    public void exitConstructor_call(ooSimpleGrammarParser.Constructor_callContext context) {
        String constructorName = context.class_name().getText();
        ArrayList<String> constructorArguments =
                this.generateFunctionParameters(this.rulesEvaluation.get(context.arguments()));



        String constructorSignature = this.generateFunctionSignature(constructorName, "Constructor",
                constructorArguments);

        MethodSymbol constructorSymbol = this.globalScope.resolveMethod(constructorSignature);

        if (constructorSymbol == null)
            ErrorHandler.undefinedConstructor(constructorName, context.arguments().getText(), context.start.getLine());


        this.rulesEvaluation.put(context,
                constructorSymbol.getReturnType()
                        + "@"
                        + constructorSignature
                        + "("
                        + this.functionArgumentsPrintFormat(constructorArguments)
                        + ")" );
    }


    /// /////////////////////////////////////////////////////////////////////////////////////////////
    //Main class
    @Override
    public void enterClass_main_def(ooSimpleGrammarParser.Class_main_defContext context) {
        this.stringBuilders.get(mainMethodImplementation)
                .append("int main(void){\n");
    }

    @Override
    public void exitClass_main_def(ooSimpleGrammarParser.Class_main_defContext context) {
        this.stringBuilders.get(mainMethodImplementation)
                .append("\treturn 0;\n}\n");
    }


    /// /////////////////////////////////////////////////////////////////////////////////////////////
    //Rules evaluation propagation
    @Override
    public void exitArgitem(ooSimpleGrammarParser.ArgitemContext context) {
        this.rulesEvaluation.put(context, this.rulesEvaluation.get(context.expression()));
    }

    @Override
    public void exitArglist(ooSimpleGrammarParser.ArglistContext context) {
        StringBuilder argList = new StringBuilder();
        if (!context.argitem().isEmpty()) {
            argList.append(this.rulesEvaluation.get(context.argitem(0)));
        }

        for (int i = 1; i < context.argitem().size(); i++) {
            argList.append(",").append(this.rulesEvaluation.get(context.argitem(i)));
        }
        this.rulesEvaluation.put(context, argList.toString());
    }

    @Override
    public void exitArguments(ooSimpleGrammarParser.ArgumentsContext context) {
        this.rulesEvaluation.put(context, "(" + this.rulesEvaluation.get(context.arglist()) + ")");
    }

    @Override
    public void exitFactorintegerLiteral(ooSimpleGrammarParser.FactorintegerLiteralContext context) {
        this.rulesEvaluation.put(context, "int@" + context.INTEGER().getText());
    }


    @Override
    public void exitFactorParenthesizedExpression(ooSimpleGrammarParser.FactorParenthesizedExpressionContext context) {
        this.rulesEvaluation.put(context, "(" + this.rulesEvaluation.get(context.expression()) + ")");
    }

    @Override
    public void exitExpression(ooSimpleGrammarParser.ExpressionContext context) {
        StringBuilder expression = new StringBuilder(context.optional_sign().getText());
        expression.append(this.rulesEvaluation.get(context.term().getFirst()));

        String firstTermDataType = expression.substring(0,expression.indexOf("@"));

        for(int i = 1; i < context.term().size(); i++){
            expression.append(" ").append(context.add_operator(i - 1).getText());
            expression.append(" ").append(this.rulesEvaluation.get(context.term(i)));
            String nextTermsDataType = this.rulesEvaluation.get(context.term(i)).substring(0,
                    this.rulesEvaluation.get(context.term(i)).indexOf("@"));
            if(!firstTermDataType.equals(nextTermsDataType))
                ErrorHandler.expressionDataTypesConflict(firstTermDataType,nextTermsDataType,context.start.getLine());
        }


        //Handle optional sign with our expression type logic
        if(expression.charAt(0) == '+' || expression.charAt(0) == '-' ){
            expression.insert(expression.indexOf("@") + 1, expression.charAt(0));
            expression.deleteCharAt(0);
        }

        //Clear expression from dataType@ pollution
        int dataTypeIndex = expression.indexOf("@");
        int extraDataTypeMark = expression.indexOf("@", dataTypeIndex + 1);

        if(extraDataTypeMark != -1) {
            String dataType = expression.substring(0,dataTypeIndex +  1);
            this.rulesEvaluation.put(context, dataType + expression.toString().replaceAll(dataType,""));
        }else {
            this.rulesEvaluation.put(context, expression.toString());
        }
    }

    @Override
    public void exitFactorVariableReference(ooSimpleGrammarParser.FactorVariableReferenceContext context) {
        Scope currentScope = this.scopes.get(context);
        String variableOoSimpleName = context.ID().getText();
        boolean selfKeywordExists = context.getChild(0).getText().equals("self.");

        VariableSymbol variableSymbol;
        if ((variableSymbol = currentScope.resolveVariable(variableOoSimpleName)) == null)
            ErrorHandler.undefinedSymbol(variableOoSimpleName, selfKeywordExists ? "Struct field " +
                    "expression" : "local variable expression", context.start.getLine());

        this.rulesEvaluation.put(context, variableSymbol.getDataType() + "@" + (selfKeywordExists ?
                "str_->" : "") + variableSymbol.getName());
    }



    @Override
    public void exitFactorVariableFunctionInvocation(ooSimpleGrammarParser.FactorVariableFunctionInvocationContext context) {
        Scope currentScope = this.scopes.get(context);
        String variableOoSimpleName = context.ID().getText();
        boolean selfKeywordExists = context.getChild(0).getText().equals("self.");

        VariableSymbol variableSymbol;
        if ((variableSymbol = currentScope.resolveVariable(variableOoSimpleName)) == null)
            ErrorHandler.undefinedSymbol(variableOoSimpleName, selfKeywordExists ? "Struct field " +
                    "expression" : "local variable expression", context.start.getLine());



        //Variable Data Type = function Owner
        String functionOwner = variableSymbol.getDataType();
        String functionName = context.func_call().ID().getText();
        ArrayList<String> functionParameters
                = this.generateFunctionParameters(this.rulesEvaluation.get(context.func_call().arguments()));
        String functionSignature = this.generateFunctionSignature(functionName, functionOwner, functionParameters);



        MethodSymbol functionCallSymbol = this.globalScope.resolveMethod(functionSignature);
        StringBuilder selfParameterCastedType = new StringBuilder(variableSymbol.getName());
        if(functionCallSymbol == null) {
            List<String> classesIamInheriting = this.inheritanceLog.get(variableSymbol.getDataType() + "$");
            for (int i = 0; i < classesIamInheriting.size(); i++) {
                functionOwner = classesIamInheriting.get(i);
                functionSignature = this.generateFunctionSignature(functionName, functionOwner, functionParameters);
                functionCallSymbol = this.globalScope.resolveMethod(functionSignature);
                if (functionCallSymbol != null) {
                    selfParameterCastedType.append("->super_").append(i + 1);
                    break;
                }
            }
        }

        //Certainly unified at this point
        if (functionCallSymbol == null)
            ErrorHandler.undefinedSymbol(functionName, "Method", context.start.getLine());

        this.rulesEvaluation.put(context,
                functionCallSymbol.getReturnType()
                        + "@" + functionSignature
                        + "("
                        + selfParameterCastedType
                        + (!functionParameters.getFirst().isEmpty() ? "," : "")
                        + this.functionArgumentsPrintFormat(functionParameters)
                        + ")");
    }

    @Override
    public void exitFactorIDdotID(ooSimpleGrammarParser.FactorIDdotIDContext context) {
        Scope currentScope = this.scopes.get(context);

        String structArgumentOoSimpleName = context.ID(0).getText();
        String structArgumentOoSimpleFieldName = context.ID(1).getText();

        VariableSymbol structArgumentSymbol;
        if((structArgumentSymbol = currentScope.resolveVariable(structArgumentOoSimpleName)) == null)
            ErrorHandler.undefinedSymbol(structArgumentOoSimpleName, "Class", context.start.getLine());


        String structClassName = structArgumentSymbol.getDataType();
        Scope structClassScope = this.globalScope.resolveClass(structClassName + "$").getScopePointer();

        VariableSymbol structArgumentFieldSymbol;
        if((structArgumentFieldSymbol = structClassScope.resolveVariable(structArgumentOoSimpleFieldName))== null)
            ErrorHandler.undefinedSymbol(structArgumentOoSimpleFieldName, "struct field ", context.start.getLine());


        this.rulesEvaluation.put(context, structArgumentFieldSymbol.getDataType() + "@"
                + structArgumentSymbol.getName() + "->" + structArgumentFieldSymbol.getName());

    }

    @Override
    public void exitFactorConstructorInvocation(ooSimpleGrammarParser.FactorConstructorInvocationContext context) {
        this.rulesEvaluation.put(context, this.rulesEvaluation.get(context.constructor_call()));
    }


    @Override
    public void exitTerm(ooSimpleGrammarParser.TermContext context) {
        StringBuilder term = new StringBuilder(this.rulesEvaluation.get(context.factor(0)));
        for(int i = 1; i < context.factor().size(); i++){
            term.append(" ").append(context.mul_operator(i -1).getText());
            term.append(" ").append(this.rulesEvaluation.get(context.factor(i)));
        }
        this.rulesEvaluation.put(context, term.toString());
    }
    @Override
    public void exitFunc_call(ooSimpleGrammarParser.Func_callContext context) {
        this.rulesEvaluation.put(context, context.ID().getText() + this.rulesEvaluation.get(context.arguments()));
    }

    @Override
    public void exitBoolfactor(ooSimpleGrammarParser.BoolfactorContext context) {
        if(context.getChild(0).getText().equals("not")){
            this.rulesEvaluation.put(context, "!(" + this.rulesEvaluation.get(context.condition()) + ")");
        } else if (context.getChild(0).getText().equals("[")) {
            this.rulesEvaluation.put(context, "(" + this.rulesEvaluation.get(context.condition()) + ")");
        }else{
            String expression1 = this.rulesEvaluation.get(context.expression(0));
            String expression2 = this.rulesEvaluation.get(context.expression(1));
            String expression1DataType = expression1.substring(0, expression1.indexOf("@"));
            String expression2DataType = expression1.substring(0, expression2.indexOf("@"));


            if(!expression1DataType.equals(expression2DataType))
                ErrorHandler.relationalOperationDataTypeConflict(expression1DataType,expression2DataType, context.start.getLine());

            this.rulesEvaluation.put(context, expression1.substring(expression1DataType.length() + 1)
                    + " " + context.relational_Operator().getText() + " " + expression2.substring(expression2DataType.length() + 1));
        }
    }

    @Override
    public void exitCondition(ooSimpleGrammarParser.ConditionContext context) {
        StringBuilder condition = new StringBuilder(this.rulesEvaluation.get(context.boolterm().getFirst()));
        for(int i = 1; i < context.boolterm().size(); i++){
            condition.append(" || ").append(this.rulesEvaluation.get(context.boolterm(i)));
        }
        this.rulesEvaluation.put(context, condition.toString());
    }

    @Override
    public void exitBoolterm(ooSimpleGrammarParser.BooltermContext context) {
        StringBuilder boolTerm = new StringBuilder(this.rulesEvaluation.get(context.boolfactor(0)));
        for(int i = 1; i < context.boolfactor().size(); i++){
            boolTerm.append(" && ").append(this.rulesEvaluation.get(context.boolfactor(i)));
        }
        this.rulesEvaluation.put(context, boolTerm.toString());
    }

    @Override
    public void exitStatements(ooSimpleGrammarParser.StatementsContext context) {
        StringBuilder statements = new StringBuilder(
                "\t".repeat(tabCount)
                        + this.rulesEvaluation.get(context.statement().getFirst())
                        + ";\n");

        for(int i = 1; i < context.statement().size(); i++){
            statements
                    .append("\t".repeat(tabCount))
                    .append(this.rulesEvaluation.get(context.statement(i)))
                    .append(";\n");
        }
        this.rulesEvaluation.put(context, statements.toString());
    }
}