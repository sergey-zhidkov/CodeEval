package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadMore {

    private static final String APPEND = "... <Read More>";
    private static final int MAX_LEN = 55;
    private static final int TRIM_TO_LEN = 40;

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            if (line.length() <= MAX_LEN) {
                System.out.println(line);
            } else {
                String newLine = line.substring(0, TRIM_TO_LEN);
                int lastSpace = newLine.lastIndexOf(' ');
                newLine = (lastSpace > 0) ? newLine.substring(0, lastSpace) + APPEND : newLine + APPEND;
                System.out.println(newLine);
            }
        }
    }
}
