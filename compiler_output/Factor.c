#include <stdio.h>
#include <stdlib.h>

//Structs declaration
typedef struct Factorial {
	int n;
} Factorial;


//Methods Declarations
Factorial* Factorial_Constructor_int(int);
int computeFactorial_Factorial(Factorial*);
int add5Numbers_Factorial_int_int_int_int_int(Factorial*, int, int, int, int, int);

int main(void){
	int user_input;
	Factorial* f1;
	printf("Enter n :");
	scanf("%d",&user_input);
	f1 = Factorial_Constructor_int(user_input);
	printf("result: ");
	printf("%d""\n", computeFactorial_Factorial(f1));
	add5Numbers_Factorial_int_int_int_int_int(f1,10, 20, 30, 40, 50);
	return 0;
}

//Constructors Implementations
Factorial* Factorial_Constructor_int(int n){
	Factorial* str_ = (Factorial*) malloc(sizeof(Factorial));
	str_->n = n;
	return str_;
}

//Methods Implementations
int computeFactorial_Factorial(Factorial* str_){
	int result;
	result = 1;
	while(str_->n > 1){
		result = result * str_->n;
		str_->n = str_->n - 1;
	};
	return result;
}

int add5Numbers_Factorial_int_int_int_int_int(Factorial* str_, int n1, int n2, int n3, int n4, int n5){
	int sum;
	sum = 0;
	sum = sum + n1;
	sum = sum + n2;
	sum = sum + n3;
	sum = sum + n4;
	sum = sum + n5;
	return sum;
}


