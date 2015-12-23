package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WithoutRepetitions {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getStringWithoutRepetitions(line));
        }
    }

    static String getStringWithoutRepetitions(String input) {
        StringBuilder output = new StringBuilder();

        char prev = input.charAt(0);
        output.append(prev);
        for (int i = 1; i < input.length(); i++) {
            char curr = input.charAt(i);
            if (prev == curr) {
                continue;
            }
            output.append(curr);
            prev = curr;
        }
        return output.toString();
    }
}
