package queue;

public interface Queue {
    // n, e[1]..e[n]
    // inv: n >= 0 && e[i] != null

    // pre: element != null
    // post: n' == n + 1 && e'[n + 1] == element && e'[1..n] == e[1..n]
    void enqueue(Object element);

    // pre: n > 0
    // post: n' == n && e' == e && R == e[1]
    Object element();

    // pre: n > 0
    // post: n' == n - 1 && e'[1..n'] == e[2..n] && R == e[1]
    Object dequeue();

    // post: n' == n && e' == e && R == n
    int size();

    // post: n' == n && e' == e && R == (n == 0)
    boolean isEmpty();

    // post: n' == 0 && e' == []
    void clear();

    // post: n' == n && e'[1]..e'[n] == e[1]..e[n] && R = e
    Object[] toArray();
}