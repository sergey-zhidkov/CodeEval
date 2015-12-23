package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MixedContent {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(sortContent(line.split(",")));
        }
    }

    static String sortContent(String [] list) {
        StringBuilder numbers = new StringBuilder();
        StringBuilder words = new StringBuilder();

        for (String aList : list) {
            if (Character.isDigit(aList.charAt(0))) {
                numbers.append(aList).append(",");
            } else {
                words.append(aList).append(",");
            }
        }
        StringBuilder result = new StringBuilder();
        if (words.length() > 0) {
            result = words.deleteCharAt(words.length() - 1);
        }
        if (words.length() > 0 && numbers.length() > 0) {
            result.append("|");
        }
        if (numbers.length() > 0) {
            result.append(numbers.deleteCharAt(numbers.length() - 1));
        }

        return result.toString();
    }
}
