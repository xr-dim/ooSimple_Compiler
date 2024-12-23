#include <stdio.h>
#include <stdlib.h>

//Structs declaration
typedef struct MathOperations {
	int a;
	int b;
	int c;
} MathOperations;


//Methods Declarations
MathOperations* MathOperations_Constructor();
MathOperations* MathOperations_Constructor_int_int_int(int, int, int);
int fibonacci_MathOperations_int(MathOperations*, int);
int addNumbers_MathOperations_int_int(MathOperations*, int, int);
int addNumbers_MathOperations_int_int_int(MathOperations*, int, int, int);
int conditionsMonster_MathOperations_int_int_int(MathOperations*, int, int, int);

int main(void){
	MathOperations* obj;
	int x1;
	int x2;
	int x3;
	MathOperations_Constructor_int_int_int(1, 2, 3);
	obj = MathOperations_Constructor();
	printf("Hello world");
	printf("\n");
	x1 = fibonacci_MathOperations_int(obj,10);
	x2 = fibonacci_MathOperations_int(obj,x1);
	x3 = x1 + x2;
	fibonacci_MathOperations_int(obj,0);
	addNumbers_MathOperations_int_int(obj,10, 20);
	addNumbers_MathOperations_int_int_int(obj,x1, x2, x3);
	x1 = conditionsMonster_MathOperations_int_int_int(obj,10, 20, 30);
	printf("%d""\n", x1);
	return 0;
}

//Constructors Implementations
MathOperations* MathOperations_Constructor(){
	MathOperations* str_ = (MathOperations*) malloc(sizeof(MathOperations));
	int x;
	int y;
	int z;
	x = 5;
	str_->a = 0;
	str_->b = x - 5;
	y = str_->a + str_->b;
	str_->c = y;
	z = 0;
	return str_;
}
MathOperations* MathOperations_Constructor_int_int_int(int a, int b, int c){
	MathOperations* str_ = (MathOperations*) malloc(sizeof(MathOperations));
	str_->a = a;
	str_->b = b;
	str_->c = c;
	return str_;
}

//Methods Implementations
int fibonacci_MathOperations_int(MathOperations* str_, int n){
	int temp;
	int i;
	if(n <= 0){
		return 0;
	}else {
		if(n == 1){
			return 1;
		}else {
			str_->a = 0;
			str_->b = 1;
			i = 2;
			while(i <= n){
				temp = str_->a + str_->b;
				str_->a = str_->b;
				str_->b = temp;
				i = i + 1;
			};
		};
	};
	return str_->b;
}

int addNumbers_MathOperations_int_int(MathOperations* str_, int num1, int num2){
	int sum;
	sum = num1 + num2;
	return sum;
}

int addNumbers_MathOperations_int_int_int(MathOperations* str_, int num1, int num2, int num3){
	num1 = num1 + num2 + num3;
	return num1;
}

int conditionsMonster_MathOperations_int_int_int(MathOperations* str_, int num1, int num2, int num3){
	int max;
	if(((num1 >= num2) && (num1 >= num3) && ((num2 <= num3) || (num2 + 1 == num3))) || ((num1 + num2 >= num3) && (num1 * 2 >= num3 * 2) && ((num3 % 2 == 0) || (num3 == num1 + num2))) || (((num1 - num3 >= num2 - num3) && (num1 * num3 >= num2 * num2)) || ((num1 + num3 <= num2 * 3)))){
		max = num1;
	}else {
		if(((num2 >= num1) && (num2 >= num3) && ((num1 % 2 == 0) || (num1 + num2 > num3))) || ((num2 * 2 >= num1 + num3) && (num2 - 1 >= num3) && ((num3 % 3 == 0) || (num1 <= num3)))){
			max = num2;
		}else {
			if(((num3 >= num1) && (num3 >= num2) && ((num1 + num2 < num3 * 2) || (num2 % 2 == 1))) || ((num3 + num2 >= num1 * 2) && (num3 - num1 >= num2 - 3) && ((num1 * num2 >= num3) || (num1 == 0)))){
				max = num3;
			}else {
				max = -1;
			};
		};
	};
	return max;
}


