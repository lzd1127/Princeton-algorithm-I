import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] queue;
	private int size = 0;
	private int first = 0;
	private int last = 0;
   public RandomizedQueue() {
	   // construct an empty randomized queue
	   queue = (Item[]) new Object[2];
   }
   public boolean isEmpty() {
	   // is the queue empty?
	   return size == 0;
   }
   public int size() {
	   // return the number of items on the queue
	   return size;
   }
   private void resize(int max) {
	   Item[] tmp = (Item[]) new Object[max];
	   for (int i = 0; i < size; i++) {
		   tmp[i] = queue[(first + i) % queue.length];
	   }
	   queue = tmp;
	   first = 0;
	   last = size;
   }
   
   public void enqueue(Item item) {
	   // add the item
	   if (item == null) {
		   throw new NullPointerException();
	   }
	   if (size == queue.length) {
		   resize(2*queue.length);
	   }
	   queue[last] = item;
	   last++;
	   if (last == size) {
		   last = 0;
	   }
	   size++;
	   
   }
   public Item dequeue() {
	   // delete and return a random item
	  if (isEmpty()) throw new NoSuchElementException("Queue underflow");
	  int index = StdRandom.uniform(size);
	  int begin = (first + index) % queue.length;
	  Item tmp = queue[begin];
	  queue[begin] = null;
	  if (last == 0 && begin == queue.length -1) {
		  last = begin;
	  }
	  else if (begin == last - 1) {
		  last = begin;
	  }
	  else if (last != 0){
		  queue[begin] = queue[last - 1];
		  queue[last - 1] = null;
		  last = last - 1;
	  }
	  else {
		  queue[begin] = queue[queue.length -1];
		  queue[queue.length - 1] = null;
		  last = queue.length - 1;
	  }
	  
	  if (last == -1) {
		  last = queue.length - 1;
	  }
	  size --;
	  if (size > 0 && size == queue.length/4) {
		  resize(queue.length/2); 
	  }
	  return tmp;
   }
   public Item sample() {
	   // return (but do not delete) a random item
	   if (isEmpty()) throw new NoSuchElementException("Queue underflow");
	   int index = StdRandom.uniform(size);
       return queue[(first + index) % queue.length];
   }
   public Iterator<Item> iterator() {
	   // return an independent iterator over items in random order
	   return new RandomIterator();
   }
   
   private class RandomIterator implements Iterator<Item> {
	   private int nums;
	   private Item [] RandomIndexes;
	   RandomIterator() {
		   nums = 0;
		   RandomIndexes = (Item[]) new Object[size];
		   for (int i = 0; i < size; i++) {
			   RandomIndexes[i] = queue[(first + i) % queue.length];
		   }
		   StdRandom.shuffle(RandomIndexes);
	   }
	   public boolean hasNext() {
		   return nums < size;
	   }
	   public void remove() {
		   throw new UnsupportedOperationException();
	   }
	   public Item next() {
		   if (!hasNext()) {
			   throw new NoSuchElementException();
		   }
		   Item tmp = RandomIndexes[nums++];
		   return tmp;
	   }
	   
   }
   public static void main(String[] args) {
	   // unit testing
	   RandomizedQueue<String> q = new RandomizedQueue<String>();
	   q.enqueue("cat");
	   q.enqueue("Dog");
	   System.out.println(q.dequeue());
	   for (String s : q) {
		   System.out.println(s);
	   }
	   q.enqueue("mice");
	   q.enqueue("tiger");
	   //System.out.println(q.dequeue());
	   System.out.println(q.size());
	   System.out.println(q.isEmpty());
	   System.out.println(q.dequeue());
	   System.out.println(q.sample());
	   System.out.println(q.size());
	   System.out.println(q.isEmpty());
	   for (String s : q) {
		   System.out.println(s);
		   for(String t : q) {
			   System.out.println(" " + t);
		   }
	   }
   }
}