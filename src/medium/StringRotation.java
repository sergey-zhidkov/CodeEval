package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StringRotation {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split(",");
            System.out.println(isRotation(parts[0], parts[1]) ? "True" : "False");
        }
    }

    static boolean isRotation(String first, String second) {
        int firstLen = first.length();
        int secondLen = second.length();
        if (firstLen != secondLen) {
            return false;
        }

        char firstChar = first.charAt(0);
        int startSearchIndex = -1;
        int index;
        while (true) {
            index = second.indexOf(firstChar, startSearchIndex);
            startSearchIndex = index + 1;
            if (index < 0 || startSearchIndex >= secondLen) {
                break;
            }
            String compare = second.substring(index, secondLen) + second.substring(0, index);
            if (compare.equals(first)) {
                return true;
            }
        }

        return false;
    }
}
