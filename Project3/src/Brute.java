import java.util.ArrayList;
import java.util.Arrays;


public class Brute {
	
	private static boolean checkColinear(Point a, Point b, Point c, Point d) {
		if (a.slopeTo(b) != a.slopeTo(c)) {
			return false;
		}
		if (a.slopeTo(c) != a.slopeTo(d)) {
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		String filename = args[0];
		In scanner = new In(filename);
		int numOfPoints = scanner.readInt();
		if (numOfPoints < 4) {
			return;
		}
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = 0; i < numOfPoints; i++) {
			int x = scanner.readInt();
			int y = scanner.readInt();
			Point p = new Point(x, y);
			points.add(p);
			p.draw();
		}
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (int i = 0; i < numOfPoints - 3; i++) {
			for (int j = i + 1; j < numOfPoints - 2; j ++) {
				for (int k = j + 1; k < numOfPoints - 1; k++) {
					for (int l = k + 1; l < numOfPoints; l ++) {
						if (checkColinear(points.get(i), points.get(j),
								points.get(k), points.get(l))) {
							Point[] line = new Point[] {points.get(i), 
									points.get(j), points.get(l), points.get(k)};
							Arrays.sort(line);
							line[0].drawTo(line[3]);
							StringBuilder sb = new StringBuilder();
							for (int n = 0; n < 4; n++) {
								sb.append(line[n].toString());
								if (n != 3) {
									sb.append("->");
								}
							}
							System.out.println(sb.toString());
						}
					}
				}
			}
		}
	}
	

}