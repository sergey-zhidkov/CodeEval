package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MultiplyLists {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] lists = line.split("\\|");
            int [] list1 = stringToIntArray(lists[0].trim().split(" "));
            int [] list2 = stringToIntArray(lists[1].trim().split(" "));
            System.out.println(multiplyLists(list1, list2));
        }
    }

    static String multiplyLists(int [] list1, int [] list2) {
        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (int i = 0; i < list1.length; i++) {
            result.append(prefix);
            prefix = " ";
            result.append(list1[i] * list2[i]);
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
