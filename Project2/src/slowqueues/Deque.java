import java.util.Iterator;
import java.util.NoSuchElementException;



public class Deque<Item> implements Iterable<Item> {
	private int N; // number of elements on queue
	private Node<Item> head; // beginning of queue
	private Node<Item> tail; // end of queue

	// helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
		private Node<Item> prev;
	}

	public Deque() {
		// construct an empty deque
		N = 0;
		head = null;
		tail = null;
	}

	public boolean isEmpty() {
		// is the deque empty?
		return N == 0;
	}

	public int size() {
		// return the number of items on the deque
		return N;
	}

	public void addFirst(Item item) {
		// insert the item at the front
		if (item == null) {
			throw new NullPointerException();
		}
		Node<Item> node = new Node<Item>();
		node.item = item;
		if (head == null) {
			tail = node;
		} else {
			head.prev = node;
		}
		node.next = head;
		head = node;
		N++;
	}

	public void addLast(Item item) {
		// insert the item at the end
		if (item == null) {
			throw new NullPointerException();
		}
		Node<Item> node = new Node<Item>();
		node.item = item;
		if (tail == null) {
			head = node;
		} else {
			tail.next = node;
			node.prev = tail;
		}
		tail = node;
		node.next = null;
		N++;
	}

	public Item removeFirst() {
		// delete and return the item at the front
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Node<Item> tmp = head;
		head = head.next;
		if (head == null) {
			tail = null;
		} else {
			head.prev = null;
		}
		N--;
		tmp.next = null;
		return tmp.item;
	}

	public Item removeLast() {
		// delete and return the item at the end
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Node<Item> tmp = tail;
		tail = tail.prev;
		if (tail == null) {
			head = null;
		} else {
			tail.next = null;
		}
		tmp.prev = null;
		N--;
		return tmp.item;

	}

	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {
		private Node<Item> current = head;

		public boolean hasNext() {
			return current != null;   
		}	

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	

	public static void main(String[] args) {
		// unit testing
		 Deque<String> queue = new Deque<String>();
		 queue.addFirst("cat");
		 queue.addLast("dog");
		 queue.addLast("ant");
		 queue.addFirst("mice");
		 System.out.println(queue.size());
		 for (String name : queue) {
			 System.out.println(name);
		 }
		 System.out.println(queue.removeFirst());
		 System.out.println(queue.removeLast());
		 System.out.println(queue.size());
		 for (String name : queue) {
			 System.out.println(name);
		 }
	}
}