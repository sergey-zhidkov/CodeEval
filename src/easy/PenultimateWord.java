package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PenultimateWord {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            int lastIndex = line.lastIndexOf(" ");
            int firstIndex = 0;
            for (int i = lastIndex - 1; i >= 0; i--) {
                if (line.charAt(i) == ' ') {
                    firstIndex = i + 1;
                    break;
                }
            }
            System.out.println(line.substring(firstIndex, lastIndex));
        }
    }
}
