package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FindASquare {

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] pointsString = line.split(", ");
            Point2D [] points = new Point2D[pointsString.length];
            for (int i = 0; i < points.length; i++) {
                points[i] = parsePoint2D(pointsString[i]);
            }

            System.out.println(isThisSquare(points));
        }
    }

    static boolean isThisSquare(Point2D [] points) {
        Point2D p1 = points[0];
        Point2D p2 = points[1];
        Point2D p3 = points[2];
        Point2D p4 = points[3];

        int dist1 = p1.squareDistTo(p2);
        int dist2 = p1.squareDistTo(p3);
        int dist3 = p1.squareDistTo(p4);

        int min = Math.min(dist1, Math.min(dist2, dist3));
        int max = Math.max(dist1, Math.max(dist2, dist3));

        if (!distsAreGood(new int[]{dist1, dist2, dist3}, max, min)) {
            return false;
        }

        int dist;
        for (int i = 1; i < points.length; i++) {
            Point2D p = points[i];
            int [] distances = new int[3];
            int k = 0;
            for (int j = 0; j < points.length; j++) {
                if (j == i) continue;
                dist = p.squareDistTo(points[j]);
                if (dist != min && dist != max) {
                    return false;
                }
                distances[k++] = dist;
            }
            if (!distsAreGood(distances, max, min)) {
                return false;
            }
        }

        return true;
    }

    static boolean distsAreGood(int [] distances, int max, int min) {
        int numOfMaxValues = 0;
        for (int distance : distances) {
            if (distance == max) {
                numOfMaxValues++;
            }
        }
        if (numOfMaxValues != 1) {
            return false;
        }

        int numOfMinValues = 0;
        for (int distance : distances) {
            if (distance == min) {
                numOfMinValues++;
            }
        }
        if (numOfMinValues != 2) {
            return false;
        }

        return true;
    }

    static Point2D parsePoint2D(String pointString) {
        pointString = pointString.substring(1, pointString.length() - 1);
        String [] xAndY = pointString.split(",");
        return new Point2D(Integer.parseInt(xAndY[0]), Integer.parseInt(xAndY[1]));
    }
}

class Point2D {
    final int x;
    final int y;

    Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int squareDistTo(Point2D point) {
        return (x - point.x) * (x - point.x) + (y - point.y) * (y - point.y);
    }
}