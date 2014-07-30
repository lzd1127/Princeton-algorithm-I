import java.util.Arrays;

public class Fast {
    private static final int POINTS_IN_LINE = 4;

    public static void main(String[] args) {

        final int fieldSize = 32768;
        StdDraw.setXscale(0, fieldSize);
        StdDraw.setYscale(0, fieldSize);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();  // get the number of points

        // create arrays for point storage and manipulation
        Point [] pointsInOrder = new Point[N];  // all the points
        Point [] arr = new Point[N]; // sorted by slope
        Point [] line = new Point[N]; // lines found
        
        for (int idx = 0; idx < N; idx++) {
            pointsInOrder[idx] = new Point(in.readInt(), in.readInt());
        }
        Arrays.sort(pointsInOrder);
        for ( Point p : pointsInOrder) {
            p.draw();
        }
        StdDraw.show(0);
        for (int idx = 0; idx < N; idx++) {
            arr[idx] = pointsInOrder[idx];
        }

        //Stopwatch timer = new Stopwatch();  // start timer

        for (int i = 0; i < N; i++) {
            Point origin = pointsInOrder[i];
            Arrays.sort(arr, origin.SLOPE_ORDER); // sort by slope
            line[0] = origin; // start of potential line
            int j = 0;
            int next = 0;
            while (j < N) {
                double slope = origin.slopeTo(arr[j]);  // slope from origin to next start
                while (j + next < N  // still more points
                        && origin.slopeTo(arr[j + next]) == slope ){  // slopes are equal
                    line[next + 1] = arr[j + next]; // save index
                    next++;
                }
                if (next >= POINTS_IN_LINE -1) {
                    Arrays.sort(line, 0, next + 1);
                    for (int n = 0; n < next; n++) {
                        StdOut.print(line[n].toString() + " -> ");
                    }
                    StdOut.println(line[next].toString());
                    line[0].drawTo(line[next]);
                    StdDraw.show(0);
                    j = j + next + 1;
                } else {
                    j++;
                }
                next = 0;
            }
        }
        //StdOut.println(timer.elapsedTime()); // stop and print timer
    }
}

