import java.util.Iterator;

public class Subset {
   public static void main(String[] args) {
	   int num = Integer.parseInt(args[0]);
	   RandomizedQueue<String> q = new RandomizedQueue<String>();
	   while (!StdIn.isEmpty()){
		   q.enqueue(StdIn.readString());
	   }
	   Iterator<String> iter = q.iterator();
	   for (int i = 0; i < num; i++) {
		   System.out.println(iter.next());
	   }
   }
}
