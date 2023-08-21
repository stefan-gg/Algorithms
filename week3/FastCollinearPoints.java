import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Objects;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private int numOfSegments = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] pointss) {
        if (pointss == null) {
            throw new IllegalArgumentException();
        }

        Point[] points = pointss.clone();

        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }

        Arrays.sort(points);

        if (!checkFoDuplicate(points)) {
            throw new IllegalArgumentException();
        }

        Point[] copy = points.clone();

        this.lineSegments = new LineSegment[copy.length];

        Point maxPoint;
        Point minPoint;
        int countCollinear;

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points, copy[i].slopeOrder());
            countCollinear = 1;
            int index = 1;

            // min and max points
            // we will use them for check if LineSegment is unique
            // (if it is unique minPoint will have the value)
            // and for constructing LineSegment
            minPoint = points[0];
            maxPoint = points[0];

            while (index < points.length - 1) {
                double slope = points[0].slopeTo(points[index]);

                if (copy[i].slopeTo(points[index + 1]) == slope) {

                    if (points[index].compareTo(minPoint) < 0) {
                        minPoint = points[index];
                    }

                    if (points[index].compareTo(maxPoint) > 0) {
                        maxPoint = points[index];
                    }

                    if (points[index + 1].compareTo(minPoint) < 0) {
                        minPoint = points[index + 1];
                    }

                    if (points[index + 1].compareTo(maxPoint) > 0) {
                        maxPoint = points[index + 1];
                    }

                    countCollinear++;

                } else {

                    checkLineSegment(copy, maxPoint, minPoint, countCollinear, i);
                    countCollinear = 1;
                    minPoint = points[0];
                    maxPoint = points[0];
                }

                // check in case last points are collinear
                // or in case all points are collinear
                if (index == points.length - 2) {
                    checkLineSegment(copy, maxPoint, minPoint, countCollinear, i);
                }
                index++;
            }
        }
        this.lineSegments = Arrays.stream(this.lineSegments).filter(Objects::nonNull)
                .toArray(LineSegment[]::new);

    }

    private void checkLineSegment(Point[] copy, Point maxPoint, Point minPoint, int countCollinear,
                                  int i) {
        if (countCollinear >= 3 && minPoint.compareTo(
                copy[i]) == 0 && numOfSegments < lineSegments.length) {
            this.lineSegments[numOfSegments++] =
                    new LineSegment(minPoint, maxPoint);
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            if (segment != null) {
                System.out.println(segment);
                segment.draw();
            }
        }
        StdDraw.show();
    }
}