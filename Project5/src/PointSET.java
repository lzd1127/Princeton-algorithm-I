import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
	private TreeSet<Point2D> set;

	public PointSET() {
		// construct an empty set of points
		set = new TreeSet<Point2D>();
	}

	public boolean isEmpty() {
		// is the set empty?
		return set.isEmpty();
	}

	public int size() {
		// number of points in the set
		return set.size();
	}

	public void insert(Point2D p) {
		// add the point p to the set (if it is not already in the set)
		if (!contains(p)) {
			set.add(p);
		}
	}

	public boolean contains(Point2D p) {
		// does the set contain the point p?
		return set.contains(p);
	}

	public void draw() {
		// draw all of the points to standard draw
		for (Point2D p : set) {
			p.draw();
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points in the set that are inside the rectangle
		List<Point2D> list = new ArrayList<Point2D>();
		for (Point2D p : set) {
			if (rect.contains(p)) {
				list.add(p);
			}
		}
		return list;
	}

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to p; null if set is empty
		if (isEmpty()) {
			return null;
		}
		double nearestDistance = Double.MAX_VALUE;
		Point2D nearestPoint = null;
		for (Point2D point : set) {
			if (point.distanceTo(p) < nearestDistance) {
				nearestPoint = point;
				nearestDistance = point.distanceTo(p);
			}
		}
		return nearestPoint;
	}
}
