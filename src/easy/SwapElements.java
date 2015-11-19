package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SwapElements {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            String [] parts = line.split(":");
            String [] list = parts[0].trim().split("\\s+");
            String [] swappedString = parts[1].trim().split(",");
            for (int i = 0; i < swappedString.length; i++) {
                String [] swapped = swappedString[i].trim().split("-");
                swap(list, Integer.parseInt(swapped[0]), Integer.parseInt(swapped[1]));
            }
            System.out.println(merge(list));
        }
    }

    private static void swap(String [] array, int index1, int index2) {
        String temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static String merge(String [] array) {
        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (String anArray : array) {
            result.append(prefix);
            prefix = " ";
            result.append(anArray);
        }
        return result.toString();
    }
}
