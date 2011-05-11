#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "queue.h"

int main() {
    Queue q = newQueue();

    /*
        Testdata
    */
    char *testString1 = "Hello ";
    char *testString2 = "world!";
    char *testString3 = "Herrooo ";
    char *testString4 = "wolld!";
    int testInt = 20;
    double testDouble = 2.54;

    /*
        Testar enque
    */
    enque(testString1, q);
    enque(&testInt, q);
    enque(&testDouble, q);
    enque(testString2, q);

    /*
        Testar qLength
    */
    printf("Storlek:\n  %d\n\n", qLength(q));    

    /*
        Testar first
    */
    printf("Första element:\n  %s\n\n", (char*) first(q));

    /*
        Testar deque
    */
    char *t1 = (char *) deque(q);
    int *t2 = (int*) deque(q);
    double *t3 = (double*) deque(q);
    char *t4 = (char *) deque(q);

    printf("Deque:\n  %s \n  %d \n  %f \n  %s \n\n", t1, *t2, *t3, t4);
    printf("Storlek:\n  %d\n\n", qLength(q));


    /*
        Testar iteratorn
    */
    enque(testString1, q);
    enque(testString2, q);
    enque(testString3, q);
    enque(testString4, q);

    printf("Iteratorn: (har stegat fram ett steg)\n");

    itNext(q); // Testar att förflytta iteratorn ett steg framåt

    while (itMore(q) != NULL)    
        printf("  %s\n", (char*) itNext(q));
    

    itReset(q); // Nollställ iteratorn


    printf("\nIteratorn: (har nollställt iteratorn)\n");

    // Kör iteratorn från starttillståndet denna gång
    char *s;
    while ((s = itNext(q)) != NULL)    
        printf("  %s\n", (char*) s);
    
    return 0;
}
