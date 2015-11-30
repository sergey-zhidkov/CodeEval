package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RightMostChar {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            if (line.length() == 0) {
                continue;
            }
            String [] parts = line.split(",");
            System.out.println(getLastIndexOf(parts[0], parts[1]));
        }
    }

    private static int getLastIndexOf(String line, String character) {
        return line.lastIndexOf(character);
    }
}
