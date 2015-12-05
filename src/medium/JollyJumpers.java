package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JollyJumpers {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            int firstSpace = line.indexOf(" ");
            line = line.substring(firstSpace + 1);
            System.out.println(isJollyJumpers(stringToIntArray(line.split(" "))) ? "Jolly" : "Not jolly");
        }
    }

    static boolean isJollyJumpers(int [] arr) {
        int [] aux = new int[arr.length - 1];

        for (int i = 1; i < arr.length; i++) {
            int auxIndex = Math.abs(arr[i-1] - arr[i]) - 1;
            if (auxIndex >= aux.length || auxIndex < 0) {
                return false;
            }
            aux[auxIndex]++;
            if (aux[auxIndex] > 1) {
                return false;
            }
        }

        for (int value : aux) {
            if (value == 0) {
                return false;
            }
        }

        return true;
    }

    private static int [] stringToIntArray(String [] array) {
        int [] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }
}
