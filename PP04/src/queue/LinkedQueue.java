package queue;


public class LinkedQueue extends AbstractQueue {
	
	// inv: value != null
	private class Node {
		
		private Object value;
		private Node next;
		
		public Node(Object value, Node next) {
			assert value != null;
			this.value = value;
			this.next = next;
		}

	}
	
	private Node head;
	private Node tail;

	protected void enqueueImpl(Object element) {
		Node temp = tail;
		tail = new Node(element, null);
		if (size == 0) {
			head = tail;
		} else {
			temp.next = tail;
		}
    }

    protected  Object elementImpl() {
		return head.value;
	}

	protected void remove() {
		head = head.next;
	}

	public void clear() {
		size = 0;
		head = null;
		tail = null;
	}

}