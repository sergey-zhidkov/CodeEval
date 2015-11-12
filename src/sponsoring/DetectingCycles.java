package sponsoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DetectingCycles {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            System.out.println(getCycle(line.split("\\s+")));
        }
    }

    private static String getCycle(String [] pointsString) {
        int [] graph = new int[100];
        int [] points = stringToIntArray(pointsString);

        for (int i = 1; i < points.length; i++) {
            graph[points[i - 1]] = points[i];
        }

        int startPoint = points[0];
        int nextI = graph[startPoint];
        int nextJ = graph[startPoint]; // 0
        nextJ = graph[nextJ]; // gpaph[0] == 6;
        while (nextJ != nextI) {
            nextI = graph[nextI];
            nextJ = graph[nextJ];
            nextJ = graph[nextJ];
        }

        int mu = 0;
        nextI = startPoint;
        while (nextJ != nextI) {
            nextI = graph[nextI];
            nextJ = graph[nextJ];
            mu++;
        }

        int lam = 1;
        nextJ = graph[nextI];
        while (nextJ != nextI) {
            nextJ = graph[nextJ];
            lam++;
        }

        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (int k = mu; k < (lam + mu); k++) {
            result.append(prefix);
            prefix = " ";
            result.append(points[k]);
        }

        return result.toString();
    }

    private static int [] stringToIntArray(String [] array) {
        int [] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }
}
