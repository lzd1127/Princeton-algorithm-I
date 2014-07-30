import java.util.HashSet;
import java.util.Set;

public class KdTree {
	private static final int HORIZONTAL = 0;
	private Node root;
	private int size = 0;

	private static class Node {
		private Point2D point; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this
								// node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree

		public Node(Point2D p, RectHV rect) {
			this.point = p;
			this.rect = rect;
		}
	}

	public boolean isEmpty() {
		// is the set empty?
		return size() == 0;
	}

	public int size() {
		// number of points in the set
		return size;
	}

	public void insert(Point2D p) {
		// add the point p to the set (if it is not already in the set)
		int flag = 0;
		root = put(root, null, p, flag);
	}

	private Node put(Node node, Node parentNode, Point2D p, int flag) {
		if (node == null) {
			size++;
			double rectXmin = 0.0;
			double rectXmax = 1.0;
			double rectYmin = 0.0;
			double rectYmax = 1.0;
			if (parentNode != null) {
				if (flag == HORIZONTAL) {
					rectXmin = parentNode.rect.xmin();
					rectXmax = parentNode.rect.xmax();
					if (p.y() < parentNode.point.y()) {
						rectYmin = parentNode.rect.ymin();
						rectYmax = parentNode.point.y();
					} else if (p.y() > parentNode.point.y()) {
						rectYmin = parentNode.point.y();
						rectYmax = parentNode.rect.ymax();
					}
				} else {
					rectYmax = parentNode.rect.ymax();
					rectYmin = parentNode.rect.ymin();
					if (p.x() < parentNode.point.x()) {
						rectXmin = parentNode.rect.xmin();
						rectXmax = parentNode.point.x();
					} else if (p.x() > parentNode.point.x()) {
						rectXmin = parentNode.point.x();
						rectXmax = parentNode.rect.xmax();
					}
				}
			}
			return new Node(p, new RectHV(rectXmin, rectYmin, rectXmax,
					rectYmax));
		}
		double newKey = HORIZONTAL == flag ? p.x() : p.y();
		double nodeKey = HORIZONTAL == flag ? node.point.x() : node.point.y();
		flag = 1 - flag;
		if (newKey < nodeKey) {
			node.lb = put(node.lb, node, p, flag);
		} else if (newKey > nodeKey) {
			node.rt = put(node.rt, node, p, flag);
		} else {
			newKey = HORIZONTAL == flag ? p.x() : p.y();
			nodeKey = HORIZONTAL == flag ? node.point.x() : node.point.y();
			if (newKey == nodeKey) {
				node.point = p;
			} else {
				node.rt = put(node.rt, node, p, flag);
			}
		}
		return node;
	}

	public boolean contains(Point2D p) {
		// does the set contain the point p?
		return get(root, p, 0) != null;
	}

	private Point2D get(Node node, Point2D p, int flag) {
		if (node == null) {
			return null;
		}
		double newKey = flag == HORIZONTAL ? p.x() : p.y();
		double nodeKey = flag == HORIZONTAL ? node.point.x() : node.point.y();
		flag = 1 - flag;
		if (newKey > nodeKey) {
			return get(node.rt, p, flag);
		} else if (newKey < nodeKey) {
			return get(node.lb, p, flag);
		} else {
			newKey = flag == HORIZONTAL ? p.x() : p.y();
			nodeKey = flag == HORIZONTAL ? node.point.x() : node.point.y();
			if (newKey == nodeKey) {
				return node.point;
			} else {
				return get(node.rt, p, flag);
			}
		}
	}

	public void draw() {
		// draw all of the points to standard draw
		drawPoint(root, null, 0);
	}

	private void drawPoint(Node node, Node parentNode, int orient) {
		if (node == null) {
			return;
		}
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		node.point.draw();

		StdDraw.setPenRadius();
		StdDraw.setPenColor(HORIZONTAL == orient ? StdDraw.RED : StdDraw.BLUE);
		if (HORIZONTAL == orient) {
			if (parentNode != null) {
				if (node.point.y() < parentNode.point.y()) {
					new Point2D(node.point.x(), 0.0).drawTo(new Point2D(
							node.point.x(), parentNode.point.y()));
				} else if (node.point.y() > parentNode.point.y()) {
					new Point2D(node.point.x(), parentNode.point.y())
							.drawTo(new Point2D(node.point.x(), 1.0));
				}
			} else {
				new Point2D(node.point.x(), 0.0).drawTo(new Point2D(node.point
						.x(), 1.0));
			}
		} else {
			if (parentNode != null) {
				if (node.point.x() < parentNode.point.x()) {
					new Point2D(0.0, node.point.y()).drawTo(new Point2D(
							parentNode.point.x(), node.point.y()));
				} else if (node.point.x() > parentNode.point.x()) {
					new Point2D(parentNode.point.x(), node.point.y())
							.drawTo(new Point2D(1.0, node.point.y()));
				}
			} else {
				new Point2D(0.0, node.point.y()).drawTo(new Point2D(1.0,
						node.point.y()));
			}
		}
		orient = 1 - orient;
		drawPoint(node.lb, node, orient);
		drawPoint(node.rt, node, orient);
	}

	public Iterable<Point2D> range(RectHV rect) {
		// all points in the set that are inside the rectangle
		Set<Point2D> set = new HashSet<Point2D>();
		searchRange(root, rect, set);
		return set;
	}

	private void searchRange(Node node, RectHV rect, Set<Point2D> set) {
		if (node == null || !node.rect.intersects(rect)) {
			return;
		}
		if (rect.contains(node.point)) {
			set.add(node.point);
		}
		searchRange(node.lb, rect, set);
		searchRange(node.rt, rect, set);

	}

	private Point2D closest;
	private double closestDistance;

	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to p; null if set is empty
		if (isEmpty()) {
			return null;
		}
		closest = root.point;
		closestDistance = Double.MAX_VALUE;
		findNearest(root, p, 0);
		return closest;
	}

	private void findNearest(Node node, Point2D p, int flag) {
		if (node == null) {
			return;
		}
		if (node.rect.distanceTo(p) > closestDistance) {
			return;
		}
		if (node.point.distanceTo(p) < closestDistance) {
			closest = node.point;
			closestDistance = node.point.distanceTo(p);
		}
		Node firstNode = null;
		Node secondNode = null;
		if (flag == HORIZONTAL) {
			if (p.x() < node.point.x()) {
				firstNode = node.lb;
				secondNode = node.rt;
			} else {
				firstNode = node.rt;
				secondNode = node.lb;
			}
		} else {
			if (p.y() < node.point.y()) {
				firstNode = node.lb;
				secondNode = node.rt;
			} else {
				firstNode = node.rt;
				secondNode = node.lb;
			}
		}
		flag = 1 - flag;
		findNearest(firstNode, p, flag);
		findNearest(secondNode, p, flag);
	}
}
