package queue;

import queue.ArrayQueue;
import queue.ArrayQueueADT;

public class Test {
    public static void main(String[] args) {
        ArrayQueue A1 = new ArrayQueue();
        A1.enqueue(5);
        System.out.println(A1.element());

        ArrayQueueADT A2 = new ArrayQueueADT();
        ArrayQueueADT.enqueue(A2, 5);
        System.out.println(ArrayQueueADT.element(A2));

    }
}
