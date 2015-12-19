package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HiddenDigits {
    private static final char FIRST = 'a';
    private static final char LAST = 'j';

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getHiddenDigits(line));
        }
    }

    static String getHiddenDigits(String line) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                result.append(c);
            } else if (c >= FIRST && c <= LAST) {
                result.append(c - FIRST);
            }
        }

        return result.length() == 0 ? "NONE" : result.toString();
    }
}
