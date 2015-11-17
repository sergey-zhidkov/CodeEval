package sponsoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReverseGroups {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            String [] parts = line.split(";");
            System.out.println(reverseGroups(parts[0].split(","), Integer.parseInt(parts[1])));
        }
    }

    private static String reverseGroups(String [] list, int k) {
        for (int i = k; i <= list.length; i += k) {
            reverseArray(list, i - k, k);
        }
        return merge(list);
    }

    private static void reverseArray(String [] array, int fromIndex, int len) {
        int halfLen = len / 2;
        for (int i = fromIndex, j = 1; i < (fromIndex + halfLen); i++, j++) {
            swap(array, i, fromIndex + len - j);
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
            prefix = ",";
            result.append(anArray);
        }
        return result.toString();
    }
}
