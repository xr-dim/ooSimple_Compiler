# ooSimpleToCMapper

The **ooSimpleToCMapper** is a powerful compiler designed for a custom object-oriented programming language
(`ooSimple`) and built using the ANTLR4 framework.
This project provides a comprehensive implementation of compiler functionalities, including grammar parsing,
semantic validation through symbol tables, and efficient code generation targeting the C programming language.

This compiler demonstrates all major stages of compilation,
including lexical analysis, syntax analysis, semantic analysis, and code generation,
making it an educational and practical tool for understanding compiler design and implementation.

Key highlights of this project include:

- **Custom Object-Oriented Language:** Designed to support classes, methods, variables, and control flow constructs.
- **ANTLR4 Integration:** Utilizes ANTLR4 for defining grammar rules and generating parsers.
- **Comprehensive Error Handling:** Handles syntax and semantic errors, such as undeclared variables or type mismatches, with detailed feedback.
- **Readable C Code Output:** Produces clean and understandable C code, making it ideal for educational purposes or as a base for further development.


## ANTLR4 Overview

ANTLR4 (Another Tool for Language Recognition) is a parser generator used to build compilers, interpreters, and translators for custom languages. In this project, it is used to define and process the `ooSimple` language. Key aspects include:

### Stages of ANTLR4 Processing

1. **Grammar Definition**: The user defines the grammar of the target language in a `.g4` file. This grammar specifies the rules for tokenizing and parsing the input.
    - Example: `ooSimpleGrammar.g4` describes the structure of the `ooSimple` language.
    - Grammar rules are categorized into lexer rules (for tokens) and parser rules (for syntax structure).

2. **Lexer Generation**: ANTLR4 generates a lexer from the grammar file. The lexer scans the input code and breaks it into tokens based on the rules defined in the grammar.
    - Tokens are the smallest meaningful units like keywords (`class`), identifiers (`myVar`), or symbols (`{`, `}`).
    - The lexer uses regular expressions defined in the grammar to recognize these tokens.

3. **Parser Generation**: ANTLR4 generates a parser that organizes the tokens into a structured hierarchy according to the grammar rules.
    - The parser checks the syntax of the token stream, ensuring it follows the defined grammar.
    - Errors in syntax are reported during this stage.
    - The output of this step is the **parse tree**, which represents the logical structure of the input.

4. **Parse Tree Construction**: The parse tree is a hierarchical representation of the input, where each node corresponds to a rule or token.
    - **Root Node**: Represents the starting rule defined in the grammar.
    - **Child Nodes**: Represent the components or subrules of the parent node.
    - **Leaf Nodes**: Represent individual tokens (e.g., keywords or literals).

5. **Listener or Visitor Execution**: Custom logic can be implemented by traversing the parse tree using Listeners or Visitors to perform tasks like semantic analysis or code generation.
    - Visitors provide a more manual approach, offering greater control over tree traversal.

### Parse Tree and Its Creation

The parse tree is central to how ANTLR4 processes input. Here's how it works:

- **Creation**: ANTLR4 builds the parse tree as the parser processes the input code. Each node in the tree corresponds to a specific grammar rule or token.
- **Structure**: The root node represents the starting grammar rule, and child nodes represent the components of that rule. Leaf nodes are individual tokens.
- **Traversal**: The parse tree can be traversed automatically using Listeners or manually using Visitors.
- **Purpose**: The parse tree serves as a blueprint for further processing, like validating semantics or generating code.

### What Are Listeners?

Listeners are a mechanism provided by ANTLR4 to traverse the parse tree. They automatically respond to events triggered during tree traversal, such as entering or exiting a node.

- **Built-In Methods**:
    - `enterRule`: Called when the parser enters a specific grammar rule.
    - `exitRule`: Called when the parser exits that rule.
- **Custom Implementation**:
    - Users extend the base listener class to add logic for handling specific nodes in the tree.
    - Example: In this project, the `SymbolicTablePhase` listener constructs and validates the symbol table.

### Relationship Between Listeners and the Parse Tree

Listeners interact directly with the parse tree to process the input. Here's how:

1. **Invocation**: As the parse tree is traversed, ANTLR4 automatically invokes the appropriate listener methods (e.g., `enterRule` and `exitRule`) for each node.
2. **Custom Logic Execution**: Listeners implement custom actions for each relevant node, such as:
    - Building a symbol table (e.g., `SymbolicTablePhase` in this project).
    - Generating target code (e.g., `CodeGenerator`).
3. **Event-Driven Processing**: The listener pattern allows event-driven processing, enabling modular and efficient handling of grammar rules.
4. **Efficiency**: Listeners reduce the complexity of manual tree traversal by focusing on specific nodes, allowing modular and organized logic.
5. **Integration**: Each listener method corresponds to a specific rule in the grammar, ensuring a tight connection between the grammar's design and the tree's processing.

By combining the parse tree's hierarchical structure with listeners' event-driven approach, ANTLR4 provides a powerful and flexible framework for language processing.

## Language Design

The `ooSimple` programming language is a custom object-oriented language designed with the following key features:

### Class Definitions

- Classes are declared using the keyword `class`, followed by the class name.
- **Multiple Inheritance**: Supported using the keyword `inherits`. A class may inherit from one or more parent classes, allowing complex hierarchies.
- Example:
  ```python
  class MyClass inherits ParentClass1, ParentClass2:
      ...
  ```

### Method Definitions

- Methods are defined using the keyword `def`, followed by the method name and parameters.
- Methods can return values or modify the object's state.
- The `self` keyword is used as the first parameter to refer to the current instance.
- The return type is specified after a colon `:`. If the method does not return a value, a dash (`-`) is used to indicate `void`.
- Example:
  ```python
  def calculate(self, value): int
      return value * 2;
  ```

### Constructor Definitions

- Constructors are special methods named `__init__` and are used to initialize object properties.
- Example:
  ```python
  def __init__(self, name): int
      self.name = name;
  ```

### Main Class

- A special `main` class can be defined to contain the entry point of the program.
- Example:
  ```python
  class Main:
      def main(self):
          print("Hello, World!");
  ```

### Variables and Declarations

- Variables are declared with their types, such as `int` or `class` types.
- Multiple variables can be declared in a single line.
- Example:
  ```python
  int a, b, c;
  int x;
  int z;
  ;
  ```

### Control Flow Statements

- Standard control flow constructs are supported, such as `if-else`, `while`, and `return`.
- Example of `if-else`:
  ```python
  if (x > 0):
      print("Positive");
  else:
      print("Non-positive");
  endif;
  ```
- Example of `while`:
  ```python
  while (x > 0):
      x = x - 1;
      print(x);
  endwhile;
  ```

These features combine to make `ooSimple` a structured and extensible language, ideal for learning and practical applications.

## Compiler Process Flow

The **ooSimpleToCMapper** compiler processes the input `ooSimple` code in a systematic and multi-step approach to produce equivalent C code. The process includes the following stages:

1. **Parse Tree Generation**:
    - The compiler begins by parsing the `ooSimple` input code using the ANTLR4 framework.
    - A hierarchical parse tree is created based on the grammar rules defined in `ooSimpleGrammar.g4`.
    - This tree represents the syntactic structure of the input program, with nodes corresponding to grammar rules or tokens.

2. **First Tree Traversal: Symbol Table Creation**:
    - During the first traversal of the parse tree, a symbol table is constructed.
    - This table stores metadata for program entities, including:
        - **Classes**: Their names, inheritance relationships, and members.
        - **Methods**: Their names, parameter types, return types, and visibility.
        - **Variables**: Their names, types, and scopes.
    - Scoping rules are enforced to manage nested blocks and inheritance.
    - Semantic checks, such as duplicate declarations or undefined parent classes, are performed.

3. **Second Tree Traversal: Code Generation**:
    - The parse tree is traversed a second time to produce equivalent C code.
    - The traversal involves:
        - **Statement Translation**: High-level constructs like `if`, `while`, and `return` are converted to C equivalents.
        - **Variable Resolution**: Ensuring all variables and methods are declared in the symbol table before use.
        - **Output Construction**: Sequentially generating lines of valid C code to replicate the behavior of the input program.
    - Type consistency and scoping rules are revalidated to ensure correctness.

4. **Error Handling**:
    - Syntax errors are reported during the parsing stage.
    - Semantic errors, such as undeclared variables or type mismatches, are reported during the first and second traversals.

This flow ensures a clear separation of concerns, making the compiler modular, extensible, and easy to debug.

## How to Run the Project

The **ooSimple_Compiler** runs directly using the run_antlr.sh script,
which automates the compilation of ooSimple files into C. While IntelliJ IDEA was used during development,
it is not a requirement for running the project.

### Running with the Script

1. **Script Overview**:
    - The project includes a script named `run_antlr.sh` located in the root directory.
    - This script automates the compilation process for the `ooSimple` source code.

2. **Usage Instructions**:
    - Open a terminal and navigate to the root directory of the project.
    - Run the script using:
      ```bash
      ./run_antlr.sh
      ```
    - Ensure the script has executable permissions. If not, make it executable:
      ```bash
      chmod +x run_antlr.sh
      ```

3. **Input Path**:
    - The script allows you to specify the path of the input `ooSimple` file. Update the input path directly within the script or modify it to accept arguments dynamically.

4. **Output Files**:
    - The generated C files are stored in the `compiler_output` directory within the project.
    - Ensure this directory exists and has write permissions.

5. **Predefined Input Files**:
    - The project includes a directory named `input_code_files` that contains ready-to-use `oos` files for testing.
        - **Subdirectories**:
            - `happy_day_scenario`: Contains valid `oos` files that compile without errors. Examples:
                - `Factor.oos`
                - `MathOperations.oos`
                - `Vehicle.oos`
            - `InvalidSyntaxTests`: Contains `oss` files designed to trigger specific syntax and semantic errors, testing the compiler's robustness in error handling.
                - `undeclaredLocalVariable.oos`
                - `assigmentDataTypesConflict.oos`
                - `methodWrongParameterType.oos`

### Additional Notes

- The script leverages the ANTLR4 runtime and ensures all dependencies are configured correctly.
- It compiles the grammar, processes the input file, and generates C code in one step.

## License

This project is licensed under the MIT License. You are free to use, modify, and distribute the code, provided you include the original license file.
