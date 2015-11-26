package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OverlappingRectangles {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split(",");
            int [] array = stringToIntArray(parts);
            System.out.println((isOverlaps(array) ? "True" : "False"));
        }
    }

    static boolean isOverlaps(int [] array) {
        // rect1
        final int xLeft1 = array[0];
        final int yTop1 = array[1];
        final int xRight1 = array[2];
        final int yBottom1 = array[3];
        // rect2
        final int xLeft2 = array[4];
        final int yTop2 = array[5];
        final int xRight2 = array[6];
        final int yBottom2 = array[7];

        return xRight1 >= xLeft2 && yTop1 >= yBottom2
            && xLeft1 <= xRight2 && yBottom1 <= yTop2;
    }

    private static int [] stringToIntArray(String [] array) {
        int [] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }
}
