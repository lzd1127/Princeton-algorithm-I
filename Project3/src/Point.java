/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(Point a, Point b) {
        	if (a == null || b == null) {
        		throw new NullPointerException();
        	}
        	double slope1 = slopeTo(a);
        	double slope2 = slopeTo(b);
        	if (slope1 > slope2) {
        		return 1;
        	} else if (slope1 < slope2) {
        		return -1;
        	} else {
        		return 0;
        	}
        }
    	
    };       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    
    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	if (that == null) {
    		throw new NullPointerException();
    	}
        if (x != that.x && y == that.y) {
        	return 0.0;
        } else if (y != that.y && x == that.x) {
        	return Double.POSITIVE_INFINITY;
        } else if (x == that.x && y == that.y) {
        	return Double.NEGATIVE_INFINITY;
        } else {
        	return ((double)(that.y - y))/(that.x - x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
       if (that == null) {
    	   throw new NullPointerException();
       }
       if (y < that.y) {
    	   return -1;
       } else if (y > that.y) {
    	   return 1;
       } else if (x > that.x) {
    	   return 1;
       } else if (x < that.x) {
    	   return -1;
       } else {
    	   return 0; 
       }
    	
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    	/* YOUR CODE HERE */
		// Equal
		assert 0 == new Point(0, 1).compareTo(new Point(0, 1));
		// Compare Y
		assert -1 == new Point(1, 1).compareTo(new Point(1, 2));
		assert 1 == new Point(1, 2).compareTo(new Point(1, 1));
		// Compare X
		assert -1 == new Point(1, 1).compareTo(new Point(2, 1));
		assert 1 == new Point(2, 1).compareTo(new Point(1, 1));

		// Vertical Line
		assert Double.POSITIVE_INFINITY == new Point(8, 5).slopeTo(new Point(8,
				0));
		// Line Segment
		assert Double.NEGATIVE_INFINITY == new Point(5, 5).slopeTo(new Point(5,
				5));
		// Horizontal Line
		assert 0 == new Point(0, 5).slopeTo(new Point(8, 5));
		assert 0 == new Point(8, 5).slopeTo(new Point(0, 5));
		// Negative
		assert -2 == new Point(1, 2).slopeTo(new Point(3, -2));
		// Normal
		assert 2.25 == new Point(1, 1).slopeTo(new Point(5, 10));
		assert 6.0 == new Point(1, 1).slopeTo(new Point(5, 25));

		Point originPoint = new Point(1, 1);

		// Same slope (straight line)
		assert 0 == originPoint.SLOPE_ORDER.compare(new Point(2, 2), new Point(
				5, 5));

		// First slope is smaller
		assert -1 == originPoint.SLOPE_ORDER.compare(new Point(5, 10),
				new Point(5, 25));

		// Second slope is smaller
		assert +1 == originPoint.SLOPE_ORDER.compare(new Point(5, 25),
				new Point(5, 10));

		StdOut.println("test OK");
    }
}