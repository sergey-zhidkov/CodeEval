package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Pangrams {

    private final static char FIRST = 'a';

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            System.out.println(getMissingAlphabet(line.toLowerCase()));
        }
    }

    static String getMissingAlphabet(String line) {
        int [] alphabet = new int[26];
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isAlphabetic(c)) {
                alphabet[c - FIRST]++;
            }
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == 0) {
                result.append((char) (i + FIRST));
            }
        }

        if (result.length() == 0) {
            return "NULL";
        }
        return result.toString();
    }

}
