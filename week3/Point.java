import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/*
 * Description :
 * https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php
 */

public class Point implements Comparable<Point> {

    private int x;
    private int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private class SlopeOrder implements Comparator<Point> {
        /**
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return
         */
        @Override
        public int compare(Point o1, Point o2) {
            if (slopeTo(o1) > slopeTo(o2)) {
                return 1;
            } else if (slopeTo(o1) < slopeTo(o2)) {
                return -1;
            }
            return 0;
        }
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    // draws this point
    public void draw() {
        StdDraw.point(this.x, this.y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
     * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if
     * y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
     * y1); a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y > that.y) {
            return 1;
        } else if (this.y < that.y) {
            return -1;
        } else {
            if (this.x == that.x) return 0;
            return this.x > that.x ? 1 : -1;
        }
    }

    /**
     * Returns the slope between this point and the specified point. Formally, if the two points are
     * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope
     * is defined to be +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if
     * (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    // the slope between this point and that point
    public double slopeTo(Point that) {

        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;

        if (this.y == that.y) return 0.0;

        if (this.x == that.x) return Double.POSITIVE_INFINITY;

        return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Unit tests the Point data type.
     */
//    public static void main(String[] args) {
//
//    }
}