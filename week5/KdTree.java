import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private Node rootNode;
    private int size;
    private Point2D nearestPoint;

    // construct an empty set of points
    public KdTree() {
        this.size = 0;
        this.rootNode = null;
        this.nearestPoint = null;
    }

    private class Node {

        private Node leftNode;
        private Node rightNode;
        private boolean isVertical;
        private Point2D point;
        private RectHV rectHV;

        public Node(Point2D point, boolean isVertical) {
            this.point = point;
            this.isVertical = isVertical;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (this.rootNode == null) {
            this.rootNode = new Node(p, true);
            this.rootNode.rectHV = new RectHV(0, 0, 1, 1);
            this.size++;
            return;
        }

        Node root = this.rootNode;
        boolean leftNodeIsEmpty = true;

        while (true) {

            if (p.compareTo(root.point) == 0) break;

            if (root.isVertical && root.point.x() >= p.x()) {
                if (root.leftNode == null) break;
                root = root.leftNode;

            } else if (root.isVertical) {
                if (root.rightNode == null) {
                    leftNodeIsEmpty = false;
                    break;
                }
                root = root.rightNode;

            } else if (root.point.y() >= p.y()) {
                if (root.leftNode == null) break;
                root = root.leftNode;

            } else {
                if (root.rightNode == null) {
                    leftNodeIsEmpty = false;
                    break;
                }
                root = root.rightNode;
            }
        }

        if (p.compareTo(root.point) == 0) {
            return;
        }

        RectHV rectHV = returnRectForNode(root, leftNodeIsEmpty);

        if (leftNodeIsEmpty) {
            root.leftNode = new Node(p, !root.isVertical);
            root.leftNode.rectHV = rectHV;
        } else {
            root.rightNode = new Node(p, !root.isVertical);
            root.rightNode.rectHV = rectHV;
        }

        this.size++;
    }

    private RectHV returnRectForNode(Node node, boolean leftNodeIsEmpty) {
        if (node.isVertical && leftNodeIsEmpty) {
            return new RectHV(node.rectHV.xmin(), node.rectHV.ymin(), node.point.x(), node.rectHV.ymax());
        } else if (node.isVertical) {
            return new RectHV(node.point.x(), node.rectHV.ymin(), node.rectHV.xmax(), node.rectHV.ymax());
        } else if (leftNodeIsEmpty) {
            return new RectHV(node.rectHV.xmin(), node.rectHV.ymin(), node.rectHV.xmax(), node.point.y());
        } else {
            return new RectHV(node.rectHV.xmin(), node.point.y(), node.rectHV.xmax(), node.rectHV.ymax());
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (this.isEmpty()) return false;

        Node currNode = this.rootNode;

        while (currNode != null) {
            if (currNode.point.equals(p)) return true;

            if (currNode.isVertical) {
                currNode = currNode.point.x() >= p.x() ? currNode.leftNode : currNode.rightNode;
//                if (this.rootNode.point.x() >= p.x()) this.rootNode = this.rootNode.leftNode;
//                else this.rootNode = this.rootNode.rightNode;
            } else {
                currNode = currNode.point.y() >= p.y() ? currNode.leftNode : currNode.rightNode;
//                if (this.rootNode.point.y() >= p.y()) this.rootNode = this.rootNode.leftNode;
//                else this.rootNode = this.rootNode.rightNode;
            }
        }

        return false;
    }

    // draw all points to standard draw
    public void draw() {
//        Node root = this.rootNode;
//
//        drawHelperMethod(this.rootNode);
//
//        this.rootNode = root;
    }

    private void drawHelperMethod(Node node) {
        if (node == null) return;

        node.point.draw();

        drawHelperMethod(node.leftNode);
        drawHelperMethod(node.rightNode);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        if (this.isEmpty()) return null;

        ArrayList pointsInRange = new ArrayList<Point2D>();
        Node tempRoot = this.rootNode;

        pointsInRange = rangeHelperMethod(this.rootNode, rect, pointsInRange);

        this.rootNode = tempRoot;

        return pointsInRange;
    }

    private ArrayList<Point2D> rangeHelperMethod(Node root, RectHV rectHV, ArrayList<Point2D> point2Ds) {

        if (root == null) return point2Ds;

        if (root.rectHV.intersects(rectHV)) {
            if (rectHV.contains(root.point)) {
                point2Ds.add(root.point);
            }
            rangeHelperMethod(root.leftNode, rectHV, point2Ds);
            rangeHelperMethod(root.rightNode, rectHV, point2Ds);
        } else {
            return point2Ds;
        }

        return point2Ds;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (this.isEmpty()) return null;

        Node root = this.rootNode;

        this.nearestPoint = this.rootNode.point;

        nearestHelperMethod(p, this.rootNode);

        this.rootNode = root;

        return this.nearestPoint;
    }

    private void nearestHelperMethod(Point2D p, Node node) {
        if (node == null) return;

        if (node.point.equals(p)) {
            this.nearestPoint = p;
            return;
        }

        if (node.rectHV.distanceSquaredTo(p) < this.nearestPoint.distanceSquaredTo(p)) {
            if (node.point.distanceSquaredTo(p) < this.nearestPoint.distanceSquaredTo(p)) {
                this.nearestPoint = node.point;
            }

            if (node.rightNode != null && node.rightNode.rectHV.contains(p)) {
                nearestHelperMethod(p, node.rightNode);
                nearestHelperMethod(p, node.leftNode);
            } else {
                nearestHelperMethod(p, node.leftNode);
                nearestHelperMethod(p, node.rightNode);
            }
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
