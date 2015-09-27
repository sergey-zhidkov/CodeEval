package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class BeautifulStrings {
    static final int LETTERS_COUNT = 26;

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            printMaximumPossibleBeauty(line.trim());
        }
    }

    static void printMaximumPossibleBeauty(String line) {
        String lowerCased = line.toLowerCase();

        int [] beautyArray = new int[LETTERS_COUNT];
        char firstChar = 'a';
        for (int i = 0; i < lowerCased.length(); i++) {
            char c = lowerCased.charAt(i);
            if (Character.isAlphabetic(c)) {
                beautyArray[c - firstChar] += 1;
            }
        }

        Arrays.sort(beautyArray);

        int result = 0;
        int nextLetterValue = LETTERS_COUNT;
        for (int i = beautyArray.length - 1; i >= 0; i--) {
            if (beautyArray[i] == 0) {
                break;
            }
            result += beautyArray[i] * nextLetterValue;
            nextLetterValue--;
        }
        System.out.println(result);
    }
}
