import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (this.isEmpty()) return false;

        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point2D : set) {
            point2D.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

        if (rect == null) throw new IllegalArgumentException();

        if (this.isEmpty()) return null;

        ArrayList<Point2D> pointsInRange = new ArrayList<>();

        for (Point2D point2D : set) {

            if (rect.contains(point2D)){
                pointsInRange.add(point2D);
            }
        }

        return pointsInRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (this.isEmpty()) return null;

        Point2D nearestPoint = null;
        double nearest = 2.0;

        for (Point2D point2D : set) {

            if (nearest > point2D.distanceSquaredTo(p)) {
                nearestPoint = point2D;
                nearest = point2D.distanceSquaredTo(p);
            }
        }

        return isEmpty() ? null : nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        // initialize the data structures from file
//        String filename = args[0];
//        In in = new In(filename);
//        PointSET brute = new PointSET();
//        KdTree kdtree = new KdTree();
//        while (!in.isEmpty()) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            Point2D p = new Point2D(x, y);
//            kdtree.insert(p);
//            brute.insert(p);
//        }
//
//        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
//        double x1 = 0.0, y1 = 0.0;      // current location of mouse
//        boolean isDragging = false;     // is the user dragging a rectangle
//
//        // draw the points
//        StdDraw.clear();
//        StdDraw.setPenColor(StdDraw.BLACK);
//        StdDraw.setPenRadius(0.01);
//        brute.draw();
//        StdDraw.show();
//
//        // process range search queries
//        StdDraw.enableDoubleBuffering();
//        while (true) {
//
//            // user starts to drag a rectangle
//            if (StdDraw.isMousePressed() && !isDragging) {
//                x0 = x1 = StdDraw.mouseX();
//                y0 = y1 = StdDraw.mouseY();
//                isDragging = true;
//            }
//
//            // user is dragging a rectangle
//            else if (StdDraw.isMousePressed() && isDragging) {
//                x1 = StdDraw.mouseX();
//                y1 = StdDraw.mouseY();
//            }
//
//            // user stops dragging rectangle
//            else if (!StdDraw.isMousePressed() && isDragging) {
//                isDragging = false;
//            }
//
//            // draw the points
//            StdDraw.clear();
//            StdDraw.setPenColor(StdDraw.BLACK);
//            StdDraw.setPenRadius(0.01);
//            brute.draw();
//
//            // draw the rectangle
//            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
//                    Math.max(x0, x1), Math.max(y0, y1));
//            StdDraw.setPenColor(StdDraw.BLACK);
//            StdDraw.setPenRadius();
//            rect.draw();
//
//            // draw the range search results for brute-force data structure in red
//            StdDraw.setPenRadius(0.03);
//            StdDraw.setPenColor(StdDraw.RED);
//            for (Point2D p : brute.range(rect))
//                p.draw();
//
//            // draw the range search results for kd-tree in blue
//            StdDraw.setPenRadius(0.02);
//            StdDraw.setPenColor(StdDraw.BLUE);
//            for (Point2D p : kdtree.range(rect))
//                p.draw();
//
//            StdDraw.show();
//            StdDraw.pause(20);
//        }


        /* ******************* NEAREST POINT **************************************** */


//        String filename = args[0];
//        In in = new In(filename);
//        PointSET brute = new PointSET();
//        KdTree kdtree = new KdTree();
//        while (!in.isEmpty()) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            Point2D p = new Point2D(x, y);
//            kdtree.insert(p);
//            brute.insert(p);
//        }
//
//        // process nearest neighbor queries
//        StdDraw.enableDoubleBuffering();
//        while (true) {
//
//            // the location (x, y) of the mouse
//            double x = StdDraw.mouseX();
//            double y = StdDraw.mouseY();
//            Point2D query = new Point2D(x, y);
//
//
//            // draw all of the points
//            StdDraw.clear();
//            StdDraw.setPenColor(StdDraw.BLACK);
//            StdDraw.setPenRadius(0.01);
//            brute.draw();
//
//            // draw in red the nearest neighbor (using brute-force algorithm)
//            StdDraw.setPenRadius(0.03);
//
//            // draw query point
//            query.draw();
//
//            StdDraw.setPenColor(StdDraw.RED);
//            brute.nearest(query).draw();
//            StdDraw.setPenRadius(0.02);
//
//            // draw in blue the nearest neighbor (using kd-tree algorithm)
//            StdDraw.setPenColor(StdDraw.BLUE);
//            kdtree.nearest(query).draw();
//            StdDraw.show();
//            StdDraw.pause(40);
//        }
    }
}