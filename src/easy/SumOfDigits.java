package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SumOfDigits {
    static final int FIRST = '0';

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getSumOfDigits(line));
        }
    }

    static int getSumOfDigits(String line) {
        int result = 0;
        for (int i = 0; i < line.length(); i++) {
            result += line.charAt(i) - FIRST;
        }
        return result;
    }
}
