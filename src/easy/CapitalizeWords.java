package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CapitalizeWords {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(capitalizeWords(line));
        }
    }

    static String capitalizeWords(String line) {
        if (line.length() == 0) {
            return line;
        }
        StringBuilder result = new StringBuilder();

        int spaceIndex = -1;
        int startIndex = 0;
        while (true) {
            spaceIndex = line.indexOf(" ", startIndex);
            if (spaceIndex >= 0 && spaceIndex != startIndex) {
                result.append(Character.toUpperCase(line.charAt(startIndex)));
                result.append(line.substring(startIndex + 1, spaceIndex));
                result.append(" ");
                startIndex = spaceIndex + 1;
            } else if (spaceIndex < 0) {
                result.append(Character.toUpperCase(line.charAt(startIndex)));
                if (line.length() - startIndex > 1) {
                    result.append(line.substring(startIndex + 1, line.length()));
                }
            }
            if (startIndex == line.length() || spaceIndex < 0) {
                break;
            }
        }

        return result.toString();
    }
}
