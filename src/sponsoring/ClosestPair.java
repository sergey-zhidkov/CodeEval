package sponsoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClosestPair {
    private static final String INFINITY = "INFINITY";
    private static final double MAX_DISTANCE = 10000;
    private static final DecimalFormat formatter = new DecimalFormat("0.0000");

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        int N;
        PointList points;
        while ((line = buffer.readLine()) != null) {
            N = Integer.parseInt(line);
            points = new PointList();
            for (int i = 0; i < N; i++) {
                String [] pair = buffer.readLine().split("\\s+");
                Point2D point = new Point2D(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
                points.insert(point);
            }
            System.out.println(getDistanceInClosestPair(points));
        }
    }

    static String getDistanceInClosestPair(PointList points) {
        if (points.isEmpty() || points.size() == 1) {
            return INFINITY;
        }
        points.sort(Point2D.X_ORDER);

        double squaredDistance = getSquaredMinDistance(points, 0, points.size());
        if (Math.sqrt(squaredDistance) >= MAX_DISTANCE) {
            return INFINITY;
        } else {
            return formatter.format(Math.sqrt(squaredDistance));
        }
    }

    static double getSquaredMinDistance(PointList points, int fromIndex, int toIndex) {
        int size = toIndex - fromIndex;
        double leftMinDistance;
        double rightMinDistance;
        double minDistance;
        if (size <= 3) {
            return points.getBruteForceSquaredMinDistance(fromIndex, toIndex);
        }

        int median = fromIndex + size / 2;
        Point2D midPoint = points.getPoint(median);
        leftMinDistance = getSquaredMinDistance(points, fromIndex, median);
        rightMinDistance = getSquaredMinDistance(points, median, toIndex);
        minDistance = Math.min(leftMinDistance, rightMinDistance);

        PointList stripped = points.getStrippedPoints(fromIndex, toIndex, midPoint, minDistance);
        return Math.min(minDistance, getMinDistanceFromStrip(stripped, minDistance));
    }

    static double getMinDistanceFromStrip(PointList stripped, final double squaredMinDistance) {
        double delta = Math.sqrt(squaredMinDistance);
        double squaredMin = squaredMinDistance;
        stripped.sort(Point2D.Y_ORDER);
        double minDistance = delta;
        double squaredDistance;
        Point2D p1;
        Point2D p2;
        for (int i = 0; i < stripped.size(); i++) {
            p1 = stripped.getPoint(i);
            for (int j = i + 1; j < stripped.size(); j++) {
                p2 = stripped.getPoint(j);
                if (p2.y - p1.y > minDistance) {
                    break;
                }
                squaredDistance = p2.distanceSquaredTo(p1);
                if (squaredDistance < squaredMin) {
                    squaredMin = squaredDistance;
                    minDistance = Math.sqrt(squaredDistance);
                }
            }
        }
        return squaredMin ;
    }
}

class Point2D {
    public static final Comparator<Point2D> X_ORDER = new XOrder();
    public static final Comparator<Point2D> Y_ORDER = new YOrder();

    final int x;
    final int y;

    Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // return Euclidean distance between this point and that point
    public double distanceTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    // return square of Euclidean distance between this point and that point
    public double distanceSquaredTo(Point2D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return dx*dx + dy*dy;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    // compare points according to their x-coordinate
    private static class XOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.x < q.x) return -1;
            if (p.x > q.x) return +1;
            return 0;
        }
    }

    // compare points according to their y-coordinate
    private static class YOrder implements Comparator<Point2D> {
        public int compare(Point2D p, Point2D q) {
            if (p.y < q.y) return -1;
            if (p.y > q.y) return +1;
            return 0;
        }
    }
}

class PointList {
    private ArrayList<Point2D> points;
    // construct an empty set of points
    public PointList() {
        points = new ArrayList<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return (points.size() == 0);
    }
    // number of points in the set
    public int size() {
        return points.size();
    }
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        points.add(p);
    }

    public void sort(Comparator<Point2D> comparator) {
        Collections.sort(points, comparator);
    }

    public double getBruteForceSquaredMinDistance(int fromIndex, int toIndex) {
        Point2D p1;
        Point2D p2;
        double minDistance = Double.MAX_VALUE;
        double nextDistance;
        for (int i = fromIndex; i < toIndex; i++) {
            p1 = points.get(i);
            for (int j = i + 1; j < toIndex; j++) {
                p2 = points.get(j);
                nextDistance = p1.distanceSquaredTo(p2);
                if (minDistance > nextDistance) {
                    minDistance = nextDistance;
                }
            }
        }
        return minDistance;
    }

    public PointList getStrippedPoints(int fromIndex, int toIndex, Point2D midPoint, double squaredMinDistance) {
        double minDistance = Math.sqrt(squaredMinDistance);
        PointList stripped = new PointList();
        for (int i = fromIndex; i < toIndex; i++) {
            Point2D point = points.get(i);
            if (Math.abs(midPoint.x - point.x) <= minDistance) {
                stripped.insert(point);
            }
        }
        return stripped;
    }

    public Point2D getPoint(int index) {
        return points.get(index);
    }
}