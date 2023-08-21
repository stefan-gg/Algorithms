import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Objects;

public class BruteCollinearPoints {

    private int numOfSegments = 0;

    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pointss) {

        if (pointss == null) {
            throw new IllegalArgumentException();
        }

        Point[] points = pointss.clone();

        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }

        this.lineSegments = new LineSegment[points.length];

        Arrays.sort(points);

        if (!checkFoDuplicate(points)) {
            throw new IllegalArgumentException();
        }
        this.lineSegments = new LineSegment[points.length];

        double slope = 0.0;
        Point p1 = null;

        for (int i = 0; i < points.length; i++) {
            p1 = points[i];

            for (int j = i + 1; j < points.length; j++) {
                slope = p1.slopeTo(points[j]);

                for (int k = j + 1; k < points.length; k++) {

                    if (p1.slopeTo(points[k]) == slope) {

                        for (int l = k + 1; l < points.length; l++) {
                            if (p1.slopeTo(points[l]) == slope) {
                                this.lineSegments[numOfSegments++] = new LineSegment(p1, points[l]);
                            }
                        }
                    }
                }
            }
        }
        this.lineSegments = Arrays.stream(this.lineSegments).filter(Objects::nonNull)
                .toArray(LineSegment[]::new);
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.lineSegments.clone();
    }

    private boolean checkFoDuplicate(Point[] points) {
        for (int i = 1; i < points.length; i++) {
            if ((points[i].compareTo(points[i - 1]) == 0)) return false;
        }
        return true;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            if (segment != null) {
                System.out.println(segment);
                segment.draw();
            }
        }
        StdDraw.show();
    }
}

