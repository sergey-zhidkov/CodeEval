package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RemoveCharacters {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split(",");
            System.out.println(removeCharacters(parts[0], parts[1].trim()));
        }
    }

    static String removeCharacters(String line, String characters) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (characters.indexOf(c) < 0) {
                result.append(c);
            }
        }
        return result.toString();
    }
}
