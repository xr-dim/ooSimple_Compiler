#include <stdio.h>
#include <stdlib.h>

//Structs declaration
typedef struct Vehicle {
	int year;
	int capacity;
} Vehicle;

typedef struct Car {
	Vehicle* super_1;
	int doorsNumber;
} Car;

typedef struct Buss {
	Vehicle* super_1;
	Car* super_2;
	int ticketPrice;
} Buss;


//Methods Declarations
Vehicle* Vehicle_Constructor();
Vehicle* Vehicle_Constructor_int_int(int, int);
void setCapacity_Vehicle_int(Vehicle*, int);
void setYear_Vehicle_int(Vehicle*, int);
int getCapacity_Vehicle(Vehicle*);
int getYear_Vehicle(Vehicle*);
int getFieldsSum_Vehicle(Vehicle*);
int equals_Vehicle_Vehicle(Vehicle*, Vehicle*);
Car* Car_Constructor_int_int_int(int, int, int);
Car* Car_Constructor_int(int);
void setDoorsNumber_Car_int(Car*, int);
int getDoorsNumber_Car(Car*);
int getYearAsCar_Car(Car*);
void setYearAsCar_Car_int(Car*, int);
int getFieldsSum_Car(Car*);
Buss* Buss_Constructor_int_int_int_int(int, int, int, int);
Buss* Buss_Constructor_int_int_int(int, int, int);
Buss* Buss_Constructor_int_int(int, int);
int getFieldsSum_Buss(Buss*);
void setTicketPrice_Buss_int(Buss*, int);
int getTicketPrice_Buss(Buss*);

int main(void){
	int x;
	Vehicle* v0;
	Vehicle* v1;
	Car* c1;
	Buss* b1;
	v0 = Vehicle_Constructor();
	printf("v0 Capacity : ");
	printf("%d""\n", getCapacity_Vehicle(v0));
	printf("v0 Year : ");
	printf("%d""\n", getYear_Vehicle(v0));
	v1 = Vehicle_Constructor_int_int(7, 1997);
	printf("v1 Capacity : ");
	printf("%d""\n", getCapacity_Vehicle(v1));
	printf("v1 Year : ");
	printf("%d""\n", getYear_Vehicle(v1));
	setCapacity_Vehicle_int(v1,10);
	setYear_Vehicle_int(v1,2003);
	printf("v1 Capacity : ");
	printf("%d""\n", getCapacity_Vehicle(v1));
	printf("v1 Year : ");
	printf("%d""\n", getYear_Vehicle(v1));
	c1 = Car_Constructor_int_int_int(2, 2018, 3);
	printf("Get year as vehicle : ");
	printf("%d""\n", getYear_Vehicle(c1->super_1));
	printf("Get year as car : ");
	printf("%d""\n", getYearAsCar_Car(c1));
	setYear_Vehicle_int(c1->super_1,1945);
	setYearAsCar_Car_int(c1,2000);
	printf("Get fields Sum. Should override vehicle : ");
	printf("%d""\n", getFieldsSum_Car(c1));
	b1 = Buss_Constructor_int_int_int_int(80, 2024, 3, 5);
	printf("Buss capacity :  year:   doorsNumber:    ticketPrice:    \n");
	printf("%d"" %d"" %d"" %d""\n", getCapacity_Vehicle(b1->super_1) ,getYear_Vehicle(b1->super_1) ,getDoorsNumber_Car(b1->super_2) ,getTicketPrice_Buss(b1));
	printf("%d""\n", getFieldsSum_Buss(b1));
	setDoorsNumber_Car_int(b1->super_2,1);
	printf("%d""\n", getDoorsNumber_Car(b1->super_2));
	printf("%d""\n", equals_Vehicle_Vehicle(b1->super_1,v1));
	x = getCapacity_Vehicle(b1->super_1) + getYear_Vehicle(b1->super_1) + getDoorsNumber_Car(b1->super_2) + getTicketPrice_Buss(b1);
	return 0;
}

//Constructors Implementations
Vehicle* Vehicle_Constructor(){
	Vehicle* str_ = (Vehicle*) malloc(sizeof(Vehicle));
	int temp;
	temp = 0;
	str_->capacity = temp;
	str_->year = 0;
	return str_;
}
Vehicle* Vehicle_Constructor_int_int(int capacity, int year){
	Vehicle* str_ = (Vehicle*) malloc(sizeof(Vehicle));
	str_->capacity = capacity;
	str_->year = year;
	return str_;
}
Car* Car_Constructor_int_int_int(int capacity, int year, int doorsNumber){
	Car* str_ = (Car*) malloc(sizeof(Car));
	str_->super_1 = (Vehicle*) malloc(sizeof(Vehicle));
	str_->super_1->capacity = capacity;
	str_->super_1->year = year;
	str_->doorsNumber = doorsNumber;
	return str_;
}
Car* Car_Constructor_int(int doorsNumber){
	Car* str_ = (Car*) malloc(sizeof(Car));
	str_->super_1 = (Vehicle*) malloc(sizeof(Vehicle));
	str_->super_1->capacity = 0;
	str_->super_1->year = 0;
	str_->doorsNumber = doorsNumber;
	return str_;
}
Buss* Buss_Constructor_int_int_int_int(int capacity, int year, int doorsNumber, int ticketPrice){
	Buss* str_ = (Buss*) malloc(sizeof(Buss));
	str_->super_1 = (Vehicle*) malloc(sizeof(Vehicle));
	str_->super_2 = (Car*) malloc(sizeof(Car));
	str_->super_1->capacity = capacity;
	str_->super_1->year = year;
	str_->super_2->doorsNumber = doorsNumber;
	str_->ticketPrice = ticketPrice;
	return str_;
}
Buss* Buss_Constructor_int_int_int(int capacity, int year, int doorsNumber){
	Buss* str_ = (Buss*) malloc(sizeof(Buss));
	str_->super_1 = (Vehicle*) malloc(sizeof(Vehicle));
	str_->super_2 = (Car*) malloc(sizeof(Car));
	str_->super_1->capacity = capacity;
	str_->super_1->year = year;
	str_->super_2->doorsNumber = doorsNumber;
	return str_;
}
Buss* Buss_Constructor_int_int(int capacity, int ticketPrice){
	Buss* str_ = (Buss*) malloc(sizeof(Buss));
	str_->super_1 = (Vehicle*) malloc(sizeof(Vehicle));
	str_->super_2 = (Car*) malloc(sizeof(Car));
	str_->ticketPrice = 0;
	str_->ticketPrice = ticketPrice;
	str_->super_1->capacity = 0;
	str_->super_2->doorsNumber = 1 - 1;
	return str_;
}

//Methods Implementations
void setCapacity_Vehicle_int(Vehicle* str_, int capacity){
	str_->capacity = capacity;
}

void setYear_Vehicle_int(Vehicle* str_, int x){
	str_->year = x;
}

int getCapacity_Vehicle(Vehicle* str_){
	return str_->capacity;
}

int getYear_Vehicle(Vehicle* str_){
	return str_->year;
}

int getFieldsSum_Vehicle(Vehicle* str_){
	return str_->capacity + str_->year;
}

int equals_Vehicle_Vehicle(Vehicle* str_, Vehicle* other){
	if((str_->capacity == other->capacity) && (str_->year == other->year)){
		return 1;
	}else {
		return 0;
	};
}

void setDoorsNumber_Car_int(Car* str_, int x){
	str_->doorsNumber = x;
}

int getDoorsNumber_Car(Car* str_){
	return str_->doorsNumber;
}

int getYearAsCar_Car(Car* str_){
	return str_->super_1->year;
}

void setYearAsCar_Car_int(Car* str_, int year){
	str_->super_1->year = year;
}

int getFieldsSum_Car(Car* str_){
	return str_->super_1->capacity + str_->super_1->year + str_->doorsNumber;
}

int getFieldsSum_Buss(Buss* str_){
	return str_->super_1->capacity + str_->super_1->year + str_->super_2->doorsNumber + str_->ticketPrice;
}

void setTicketPrice_Buss_int(Buss* str_, int newPrice){
	str_->ticketPrice = newPrice;
}

int getTicketPrice_Buss(Buss* str_){
	return str_->ticketPrice;
}


