package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TrailingString {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            String [] parts = line.split(",");
            System.out.println(isSecondPartTrailingEnd(parts[0], parts[1]) ? "1" : "0");
        }
    }

    static boolean isSecondPartTrailingEnd(String line, String trailing) {
        if (line.length() < trailing.length()) {
            return false;
        }
        int startIndex = line.length() - trailing.length();
        return line.indexOf(trailing, startIndex) == startIndex;
    }
}
