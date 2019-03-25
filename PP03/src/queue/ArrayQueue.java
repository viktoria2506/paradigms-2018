package queue;

public class ArrayQueue {

    // inv: size >= 0 && for i = 0 to size - 1 : elements[(start + i) % elements.length] != null

    private int size;
    private Object[] elements = new Object[0];
    private int start, end;

    private int inc(int x) {
        x++;
        if (x == elements.length) {
            x = 0;
        }
        return x;
    }

    private int dec(int x) {
        x--;
        if (x < 0) x += elements.length;
        return x;
    }


    //pre: element != null
    //post: elements[end] = element,
    //      end = (end + 1) % elements.length,
    //      size = size + 1

    public void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        elements[end] = element;
        size++;
        end = inc(end);
    }

    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        }
        Object[] newElements = new Object[2 * capacity];
        System.arraycopy(elements, start, newElements, 0, elements.length - start);
        System.arraycopy(elements, 0, newElements, elements.length - start, end);
        elements = newElements;
        start = 0;
        end = capacity - 1;
    }

    // pre: size > 0
    // post: R = elements[start] && elements = elements'

    public Object element() {
        assert size > 0;
        return elements[start];
    }

    //pre: size > 0
    //post: R = elements[start],
    //      start = (start + 1) % elements.length,
    //      size = size - 1

    public Object dequeue() {
        assert size > 0;
        Object result = elements[start];
        size--;
        start = inc(start);
        return result;
    }

    // post: R = size && size == size' && elements == elements'

    public int size() {
        return size;
    }

    // post: R = (size == 0) && size == size' && elements == elements'

    public boolean isEmpty() {
        return size == 0;
    }

    // post: start = 0 && end = 0 && size = 0 && elements.length = 0

    public void clear() {
        start = 0;
        end = 0;
        size = 0;
        elements = new Object[2];
    }

    // post: size == size' && start == start' && end == end' && elements == elements' && R = ans: ans[0]..ans[size-1] == elements[start]..elements[dec(end)]
    public Object[] toArray() {
        Object[] ans = new Object[size()];
        if (size == 0) return ans;
        if (start < end) {
            System.arraycopy(elements, start, ans, 0, size);
        } else {
            System.arraycopy(elements, start, ans, 0, elements.length - start);
            System.arraycopy(elements, 0, ans, elements.length - start, end);
        }
        return ans;
    }
}

