package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CleanUpTheWords {

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            cleanUpTheWordsAndPrint(line.trim());
        }
    }

    static void cleanUpTheWordsAndPrint(String line) {
        StringBuilder result = new StringBuilder();
        String space = " ";
        boolean prevCharWasLetter = true;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isAlphabetic(c)) {
                c = Character.toLowerCase(c);
                if (prevCharWasLetter || result.length() == 0) {
                    result.append(c);
                } else {
                    result.append(space).append(c);
                }
                prevCharWasLetter = true;
            } else {
                prevCharWasLetter = false;
            }
        }
        System.out.println(result);
    }
}
