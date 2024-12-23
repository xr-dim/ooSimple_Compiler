package antrl4;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import listeners.*;
import scope.*;


public class Compiler {

    public static void main(String[] args) throws Exception {
        if(args.length < 1){
            System.out.println("Error. You should input the oss file as an argument");
            System.exit(-1);
        }

        String outputFileName = generateOutPutFileName(args[0]);

        //DataFLow
        codeMapping(
                symbolicTableAnalysis(
                        syntacticalAnalysis(
                                lexicalAnalysis(new FileInputStream(args[0])))), outputFileName);

        System.out.println("Compiling Successful");
    }

    private static String generateOutPutFileName(String inputFilePath) {
        return inputFilePath.substring(inputFilePath.lastIndexOf('/') + 1, inputFilePath.length() - 3) + "c";
    }

    private static CommonTokenStream lexicalAnalysis(InputStream inputStream) throws IOException {
        CharStream input = CharStreams.fromStream(inputStream);
        ooSimpleGrammarLexer lexer = new ooSimpleGrammarLexer(input);
        return new CommonTokenStream(lexer);
    }

    private static ooSimpleGrammarParser syntacticalAnalysis(CommonTokenStream tokens){
        return new ooSimpleGrammarParser(tokens);
    }

    private static ArrayList<Object> symbolicTableAnalysis(ooSimpleGrammarParser parser){
        SymbolicTablePhase symbolicTablePhase = new SymbolicTablePhase();
        ParseTree tree = parser.startRule();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(symbolicTablePhase, tree);

        return new ArrayList<>(List.of(tree
                , symbolicTablePhase.getGlobalScope()
                , symbolicTablePhase.getScopes()
                , symbolicTablePhase.getMethodDeclarations()));
    }

    @SuppressWarnings("unchecked")
    private static void codeMapping(ArrayList<Object> parameters, String outputFileName) throws IOException {
        ParseTree tree = (ParseTree) parameters.get(0);
        Scope globalScope =  (Scope) parameters.get(1);
        ParseTreeProperty<Scope> scopes = (ParseTreeProperty<Scope>) parameters.get(2);
        StringBuilder symbolicTableMethodsDeclarations = (StringBuilder) parameters.get(3);


        CodeGenerator codeGenerator =  new CodeGenerator(globalScope,scopes,symbolicTableMethodsDeclarations);
        ParseTreeWalker  walker = new ParseTreeWalker();
        walker.walk(codeGenerator,tree);

        codeGenerator.printToFile(outputFileName);
    }

}