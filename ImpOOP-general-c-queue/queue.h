/* queue.h
   En generell kömekanism.
   Kön implementeras som en länkad lista  där datafältet i köelementen 
   är en pekare till en ospecificerad typ.
*/

#ifndef __queue__
#define __queue__

typedef void *QueueData;

typedef 
    struct queueElem {
    QueueData item;                     // Ospecificerad pekartyp
    struct queueElem *next;
} queueElem, *link;

typedef struct queueRecord {
    int cnt;
    queueElem *first;
    queueElem *last;
    queueElem *current;
} queueRecord, *Queue;

Queue     newQueue();                   // Returnerar ett nytt köobjekt (pekare)
void      enque(QueueData it, Queue q); // Lagrar ett element sist i kön
QueueData deque(Queue q);               // Tar ut första elementet ur kön och returnerar det
QueueData first(Queue q);               // Returnerar första elementet ur kön elle NULL om kön tom
int       qLength(Queue q);             // Returnerar köns längd
void      itReset(Queue q);             // Initierar iteratorn
int       itMore(Queue q);              // Kön genomitererad?
QueueData itNext(Queue q);              // Returnerar aktuellt vaärde och stegar fram iteratorn

#endif
