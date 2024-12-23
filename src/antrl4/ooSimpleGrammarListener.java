// Generated from ooSimpleGrammar.g4 by ANTLR 4.13.2
package antrl4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ooSimpleGrammarParser}.
 */
public interface ooSimpleGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#startRule}.
	 * @param ctx the parse tree
	 */
	void enterStartRule(ooSimpleGrammarParser.StartRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#startRule}.
	 * @param ctx the parse tree
	 */
	void exitStartRule(ooSimpleGrammarParser.StartRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#classes}.
	 * @param ctx the parse tree
	 */
	void enterClasses(ooSimpleGrammarParser.ClassesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#classes}.
	 * @param ctx the parse tree
	 */
	void exitClasses(ooSimpleGrammarParser.ClassesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#class_def}.
	 * @param ctx the parse tree
	 */
	void enterClass_def(ooSimpleGrammarParser.Class_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#class_def}.
	 * @param ctx the parse tree
	 */
	void exitClass_def(ooSimpleGrammarParser.Class_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#class_main_def}.
	 * @param ctx the parse tree
	 */
	void enterClass_main_def(ooSimpleGrammarParser.Class_main_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#class_main_def}.
	 * @param ctx the parse tree
	 */
	void exitClass_main_def(ooSimpleGrammarParser.Class_main_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#class_name}.
	 * @param ctx the parse tree
	 */
	void enterClass_name(ooSimpleGrammarParser.Class_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#class_name}.
	 * @param ctx the parse tree
	 */
	void exitClass_name(ooSimpleGrammarParser.Class_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#declarations}.
	 * @param ctx the parse tree
	 */
	void enterDeclarations(ooSimpleGrammarParser.DeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#declarations}.
	 * @param ctx the parse tree
	 */
	void exitDeclarations(ooSimpleGrammarParser.DeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#class_body}.
	 * @param ctx the parse tree
	 */
	void enterClass_body(ooSimpleGrammarParser.Class_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#class_body}.
	 * @param ctx the parse tree
	 */
	void exitClass_body(ooSimpleGrammarParser.Class_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#main_body}.
	 * @param ctx the parse tree
	 */
	void enterMain_body(ooSimpleGrammarParser.Main_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#main_body}.
	 * @param ctx the parse tree
	 */
	void exitMain_body(ooSimpleGrammarParser.Main_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#decl_line}.
	 * @param ctx the parse tree
	 */
	void enterDecl_line(ooSimpleGrammarParser.Decl_lineContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#decl_line}.
	 * @param ctx the parse tree
	 */
	void exitDecl_line(ooSimpleGrammarParser.Decl_lineContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#constructor_def}.
	 * @param ctx the parse tree
	 */
	void enterConstructor_def(ooSimpleGrammarParser.Constructor_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#constructor_def}.
	 * @param ctx the parse tree
	 */
	void exitConstructor_def(ooSimpleGrammarParser.Constructor_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#method_def}.
	 * @param ctx the parse tree
	 */
	void enterMethod_def(ooSimpleGrammarParser.Method_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#method_def}.
	 * @param ctx the parse tree
	 */
	void exitMethod_def(ooSimpleGrammarParser.Method_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#method_main_def}.
	 * @param ctx the parse tree
	 */
	void enterMethod_main_def(ooSimpleGrammarParser.Method_main_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#method_main_def}.
	 * @param ctx the parse tree
	 */
	void exitMethod_main_def(ooSimpleGrammarParser.Method_main_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#types}.
	 * @param ctx the parse tree
	 */
	void enterTypes(ooSimpleGrammarParser.TypesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#types}.
	 * @param ctx the parse tree
	 */
	void exitTypes(ooSimpleGrammarParser.TypesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(ooSimpleGrammarParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(ooSimpleGrammarParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#method_body}.
	 * @param ctx the parse tree
	 */
	void enterMethod_body(ooSimpleGrammarParser.Method_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#method_body}.
	 * @param ctx the parse tree
	 */
	void exitMethod_body(ooSimpleGrammarParser.Method_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#return_type}.
	 * @param ctx the parse tree
	 */
	void enterReturn_type(ooSimpleGrammarParser.Return_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#return_type}.
	 * @param ctx the parse tree
	 */
	void exitReturn_type(ooSimpleGrammarParser.Return_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#parlist}.
	 * @param ctx the parse tree
	 */
	void enterParlist(ooSimpleGrammarParser.ParlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#parlist}.
	 * @param ctx the parse tree
	 */
	void exitParlist(ooSimpleGrammarParser.ParlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(ooSimpleGrammarParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(ooSimpleGrammarParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ooSimpleGrammarParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ooSimpleGrammarParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assigmentToExpression}
	 * labeled alternative in {@link ooSimpleGrammarParser#assignment_stat}.
	 * @param ctx the parse tree
	 */
	void enterAssigmentToExpression(ooSimpleGrammarParser.AssigmentToExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assigmentToExpression}
	 * labeled alternative in {@link ooSimpleGrammarParser#assignment_stat}.
	 * @param ctx the parse tree
	 */
	void exitAssigmentToExpression(ooSimpleGrammarParser.AssigmentToExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignmentPlainConstructorCall}
	 * labeled alternative in {@link ooSimpleGrammarParser#assignment_stat}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentPlainConstructorCall(ooSimpleGrammarParser.AssignmentPlainConstructorCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentPlainConstructorCall}
	 * labeled alternative in {@link ooSimpleGrammarParser#assignment_stat}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentPlainConstructorCall(ooSimpleGrammarParser.AssignmentPlainConstructorCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#direct_call_stat}.
	 * @param ctx the parse tree
	 */
	void enterDirect_call_stat(ooSimpleGrammarParser.Direct_call_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#direct_call_stat}.
	 * @param ctx the parse tree
	 */
	void exitDirect_call_stat(ooSimpleGrammarParser.Direct_call_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void enterIf_stat(ooSimpleGrammarParser.If_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void exitIf_stat(ooSimpleGrammarParser.If_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#else_part}.
	 * @param ctx the parse tree
	 */
	void enterElse_part(ooSimpleGrammarParser.Else_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#else_part}.
	 * @param ctx the parse tree
	 */
	void exitElse_part(ooSimpleGrammarParser.Else_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stat(ooSimpleGrammarParser.While_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stat(ooSimpleGrammarParser.While_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#return_stat}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stat(ooSimpleGrammarParser.Return_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#return_stat}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stat(ooSimpleGrammarParser.Return_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#input_stat}.
	 * @param ctx the parse tree
	 */
	void enterInput_stat(ooSimpleGrammarParser.Input_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#input_stat}.
	 * @param ctx the parse tree
	 */
	void exitInput_stat(ooSimpleGrammarParser.Input_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#print_stat}.
	 * @param ctx the parse tree
	 */
	void enterPrint_stat(ooSimpleGrammarParser.Print_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#print_stat}.
	 * @param ctx the parse tree
	 */
	void exitPrint_stat(ooSimpleGrammarParser.Print_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ooSimpleGrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ooSimpleGrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#optional_sign}.
	 * @param ctx the parse tree
	 */
	void enterOptional_sign(ooSimpleGrammarParser.Optional_signContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#optional_sign}.
	 * @param ctx the parse tree
	 */
	void exitOptional_sign(ooSimpleGrammarParser.Optional_signContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#boolterm}.
	 * @param ctx the parse tree
	 */
	void enterBoolterm(ooSimpleGrammarParser.BooltermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#boolterm}.
	 * @param ctx the parse tree
	 */
	void exitBoolterm(ooSimpleGrammarParser.BooltermContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(ooSimpleGrammarParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(ooSimpleGrammarParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#boolfactor}.
	 * @param ctx the parse tree
	 */
	void enterBoolfactor(ooSimpleGrammarParser.BoolfactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#boolfactor}.
	 * @param ctx the parse tree
	 */
	void exitBoolfactor(ooSimpleGrammarParser.BoolfactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#constructor_call}.
	 * @param ctx the parse tree
	 */
	void enterConstructor_call(ooSimpleGrammarParser.Constructor_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#constructor_call}.
	 * @param ctx the parse tree
	 */
	void exitConstructor_call(ooSimpleGrammarParser.Constructor_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(ooSimpleGrammarParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(ooSimpleGrammarParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code factorintegerLiteral}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactorintegerLiteral(ooSimpleGrammarParser.FactorintegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code factorintegerLiteral}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactorintegerLiteral(ooSimpleGrammarParser.FactorintegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code factorParenthesizedExpression}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactorParenthesizedExpression(ooSimpleGrammarParser.FactorParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code factorParenthesizedExpression}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactorParenthesizedExpression(ooSimpleGrammarParser.FactorParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code factorVariableReference}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactorVariableReference(ooSimpleGrammarParser.FactorVariableReferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code factorVariableReference}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactorVariableReference(ooSimpleGrammarParser.FactorVariableReferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code factorVariableFunctionInvocation}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactorVariableFunctionInvocation(ooSimpleGrammarParser.FactorVariableFunctionInvocationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code factorVariableFunctionInvocation}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactorVariableFunctionInvocation(ooSimpleGrammarParser.FactorVariableFunctionInvocationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code factorConstructorInvocation}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactorConstructorInvocation(ooSimpleGrammarParser.FactorConstructorInvocationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code factorConstructorInvocation}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactorConstructorInvocation(ooSimpleGrammarParser.FactorConstructorInvocationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code factorIDdotID}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactorIDdotID(ooSimpleGrammarParser.FactorIDdotIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code factorIDdotID}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactorIDdotID(ooSimpleGrammarParser.FactorIDdotIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code factorID}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactorID(ooSimpleGrammarParser.FactorIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code factorID}
	 * labeled alternative in {@link ooSimpleGrammarParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactorID(ooSimpleGrammarParser.FactorIDContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#func_call}.
	 * @param ctx the parse tree
	 */
	void enterFunc_call(ooSimpleGrammarParser.Func_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#func_call}.
	 * @param ctx the parse tree
	 */
	void exitFunc_call(ooSimpleGrammarParser.Func_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(ooSimpleGrammarParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(ooSimpleGrammarParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#arglist}.
	 * @param ctx the parse tree
	 */
	void enterArglist(ooSimpleGrammarParser.ArglistContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#arglist}.
	 * @param ctx the parse tree
	 */
	void exitArglist(ooSimpleGrammarParser.ArglistContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#argitem}.
	 * @param ctx the parse tree
	 */
	void enterArgitem(ooSimpleGrammarParser.ArgitemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#argitem}.
	 * @param ctx the parse tree
	 */
	void exitArgitem(ooSimpleGrammarParser.ArgitemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#add_operator}.
	 * @param ctx the parse tree
	 */
	void enterAdd_operator(ooSimpleGrammarParser.Add_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#add_operator}.
	 * @param ctx the parse tree
	 */
	void exitAdd_operator(ooSimpleGrammarParser.Add_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#print_string_stat}.
	 * @param ctx the parse tree
	 */
	void enterPrint_string_stat(ooSimpleGrammarParser.Print_string_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#print_string_stat}.
	 * @param ctx the parse tree
	 */
	void exitPrint_string_stat(ooSimpleGrammarParser.Print_string_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#mul_operator}.
	 * @param ctx the parse tree
	 */
	void enterMul_operator(ooSimpleGrammarParser.Mul_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#mul_operator}.
	 * @param ctx the parse tree
	 */
	void exitMul_operator(ooSimpleGrammarParser.Mul_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ooSimpleGrammarParser#relational_Operator}.
	 * @param ctx the parse tree
	 */
	void enterRelational_Operator(ooSimpleGrammarParser.Relational_OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ooSimpleGrammarParser#relational_Operator}.
	 * @param ctx the parse tree
	 */
	void exitRelational_Operator(ooSimpleGrammarParser.Relational_OperatorContext ctx);
}