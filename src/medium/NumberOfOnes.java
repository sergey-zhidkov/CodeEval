package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NumberOfOnes {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getNumberOfOnes(line));
        }
    }

    private static int getNumberOfOnes(String line) {
        int digit = Integer.parseInt(line);
        String binaryDigit = Integer.toBinaryString(digit);
        int result = 0;
        for (int i = 0; i < binaryDigit.length(); i++) {
            if (binaryDigit.charAt(i) == '1') result++;
        }
        return result;
    }
}
