package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TryToSolveIt {

    static final char FIRST = 'a';

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getDecodedString(line));
        }
    }

    static String getDecodedString(String line) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            int converted;
            if (c <= 'f' && c >= 'a') {
                converted = 'u' + (c - 'a');
            } else if (c >= 'g' && c <= 'm') {
                converted = 'n' + (c - 'g');
            } else if (c >= 'u' && c <= 'z') {
                converted = 'a' + (c - 'u');
            } else {
                converted = 'g' + (c - 'n');
            }
            result.append((char) converted);
        }
        return result.toString();
    }

//    a | b | c | d | e | f | g | h | i | j | k | l | m
//    u | v | w | x | y | z | n | o | p | q | r | s | t
}
