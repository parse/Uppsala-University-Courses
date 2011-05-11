#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "queue.h"

/*
    newQueue()
    PRE: (inget)
    POST: Skapar en tom kö
    RETURN: Returnerar en tom kö
*/
Queue newQueue () {
    Queue q = (Queue) malloc( sizeof(queueRecord) );
    q->first = NULL;
    q->last = NULL;
    q->current = NULL;
    q->cnt = 0;

    return q;
}

/*
    enque(it, q)
    PRE: q är en pekare till en initierad kö, it är en godtycklig pekare
    POST: Det som it pekar på ligger i slutet av kön q
    RETURN: (inget)
*/
void enque (QueueData it, Queue q) {
    assert(q);

    link elem = (link) malloc( sizeof(queueElem) );
    
    // Set the element
    elem->item = it;
    elem->next = NULL;

    if (q->last != NULL)
        (q->last)->next = elem;
    
    // Setup the queue
    if (q->first == NULL) {
        q->first = elem;
        q->current = elem;
    }

    q->last = elem;
    (q->cnt)++;
}

/*
    deque(q)
    PRE: q är en pekare till en initierad kö
    POST: Om kön inte är tom är det första elementet borttaget
    RETURN: NULL om kön är tom, annars det värde som är först
*/
QueueData deque (Queue q) {
    assert(q);

    if (q->first == NULL)
        return NULL;

    link tmpData = q->first;
    QueueData data = tmpData->item;
    
    q->first = tmpData->next;
    (q->cnt)--;

    free(tmpData);

    return data;
}

/*
    first(q)
    PRE: q är en pekare till en initierad kö
    POST: (inget)
    RETURN: Det första värdet i kön som q pekar på
*/
QueueData first(Queue q) {
    assert(q);

    if (q->first == NULL)
        return NULL;
    else
        return (q->first)->item;
}

/*
    qLength(q)
    PRE: q är en pekare till en initierad kö
    POST: (inget)
    RETURN: Längden på kön som q pekar på
*/
int qLength (Queue q) {
    assert(q);

    return q->cnt;
}

/*
    itReset(q)
    PRE: q är en pekare till en initierad kö
    POST: Köns innehåll oförändrat, första elementet satt som aktuellt element
    RETURN: (inget)
*/
void itReset (Queue q) {
    assert(q);

    q->current = q->first;
}

/*
    itMore(q)
    PRE: q är en pekare till en initierad kö
    POST: (inget)
    RETURN: 1 om det finns fler element i kön efter aktuellt element, annars 0. 
*/
int itMore (Queue q) {
    assert(q);

    return q->current != NULL;
}

/*
    itNext(q)
    PRE: q är en pekare till en initierad kö
    POST: Köns innehåll oförändrat. Om det finns ett aktuellt element kommer dess efterföljare vara det nya aktuella    elementet.
    RETURN: Om det vid inträdet finns ett aktuellt element kommer dess värde returneras, annars NULL
*/
QueueData itNext (Queue q) {
    assert(q);

    link l = q->current;

    if (l != NULL) {
        q->current = l->next;
        return l->item;
    }
    else
        return NULL;
}
