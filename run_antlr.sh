#!/bin/bash

echo "Generating ANTLR files..."
cd ./src/antrl4
java -jar ../../antlr-4.13.2-complete.jar -Dlanguage=Java -listener -package antrl4 ooSimpleGrammar.g4


echo "Compiling Java files..."
cd ../../
javac -d out src/**/*.java


echo "Running program..."
java -cp "out:antlr-4.13.2-complete.jar" antrl4.Compiler "input_code_files/happy_day_scenario/Vehicle.oos"



