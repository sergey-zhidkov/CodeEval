package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LongestWord {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getFirstLongestWord(line));
        }
    }

    static String getFirstLongestWord(String line) {
        int longestLen = 0;
        int longestStartIndex = 0;
        int prevWhiteSpace = -1;
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char c = line.charAt(i);
            if (Character.isWhitespace(c)) {
                if (i - prevWhiteSpace - 1 > longestLen) {
                    longestLen = i - prevWhiteSpace - 1;
                    longestStartIndex = prevWhiteSpace + 1;
                }
                prevWhiteSpace = i;
            }
        }
        if (len - prevWhiteSpace - 1 > longestLen) {
            longestLen = len - prevWhiteSpace - 1;
            longestStartIndex = prevWhiteSpace + 1;
        }

        return line.substring(longestStartIndex, longestStartIndex + longestLen);
    }
}
