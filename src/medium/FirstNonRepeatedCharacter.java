package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FirstNonRepeatedCharacter {
    static final int ALPHABET_LETTERS_COUNT = 26;
    static final int A_LOWER_INDEX = "a".charAt(0);
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getFirstNonRepeatedCharacter(line));
        }
    }

    static char getFirstNonRepeatedCharacter(String line) {
        int [] charCount = new int [ALPHABET_LETTERS_COUNT];
        int [] orders = new int [ALPHABET_LETTERS_COUNT];

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            int charIndex = c - A_LOWER_INDEX;
            charCount[charIndex] = charCount[charIndex] + 1;
            int order = orders[charIndex];
            if (order == 0) {
                orders[charIndex] = i + 1;
            }
        }

        int numberOfCountOne = 0;
        int [] nonRepeatedCharacters = new int[ALPHABET_LETTERS_COUNT];
        for (int i = 0; i < charCount.length; i++) {
            int count = charCount[i];
            if (count == 1) {
                nonRepeatedCharacters[numberOfCountOne] = i;
                numberOfCountOne++;
            }
        }

        int smallestOrder = line.length() + 1;
        int smallestOrderIndex = 26;
        for (int i = 0; i < numberOfCountOne; i++) {
            int charIndex = nonRepeatedCharacters[i];
            if (smallestOrder > orders[charIndex]) {
                smallestOrder = orders[charIndex];
                smallestOrderIndex = charIndex;
            }
        }

        return (char) (smallestOrderIndex + A_LOWER_INDEX);
    }
}
