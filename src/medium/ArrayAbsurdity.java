package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArrayAbsurdity {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split(";");
            int [] array = stringToIntArray(parts[1].split(","));
            System.out.println(findDuplicate(array));
        }
    }

    private static int findDuplicate(int [] array) {
        int [] aux = new int [array.length];
        for (int i = 0; i < array.length; i++) {
            int number = array[i];
            aux[number]++;
            if (aux[number] == 2) {
                return number;
            }
        }
        return 0;
    }

    private static int [] stringToIntArray(String [] array) {
        int [] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }
}
