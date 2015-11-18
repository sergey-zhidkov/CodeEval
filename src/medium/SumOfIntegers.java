package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SumOfIntegers {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            String [] parts = line.split(",");
            System.out.println(getMaximumSubArray(stringToIntArray(parts)));
        }
    }

    private static int getMaximumSubArray(int [] array) {
        int maxSoFar = array[0];
        int maxEndingHere = array[0];
        for (int i = 1; i < array.length; i++) {
            maxEndingHere = Math.max(maxEndingHere + array[i], array[i]);
            maxSoFar = Math.max(maxEndingHere, maxSoFar);
        }
        return maxSoFar;
    }

    private static int [] stringToIntArray(String [] array) {
        int [] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }
}
