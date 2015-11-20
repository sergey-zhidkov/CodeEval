package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class NumberPairs {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            String [] parts = line.split(";");
            System.out.println(getPairs(stringToIntArray(parts[0].split(",")), Integer.parseInt(parts[1])));
        }
    }

    static String getPairs(int [] array, int sum) {
        int [] usedPairs = new int[array.length];
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            int firstFromPair = array[i];
            if (firstFromPair > sum) {
                break;
            }
            int secondFromPair = sum - firstFromPair;
            int index = Arrays.binarySearch(array, i + 1, array.length, secondFromPair);
            if (index >= 0) {
                result.append(firstFromPair).append(",").append(secondFromPair).append(";");
            }
        }

        if (result.length() == 0) {
            result.append("NULL");
        } else {
            result.deleteCharAt(result.length() - 1);
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
