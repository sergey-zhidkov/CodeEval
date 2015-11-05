package sponsoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MthToLastElement {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        int len;
        int index;
        int indexFromStart;
        int lastSpaceIndex;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            len = line.length();
            lastSpaceIndex = line.lastIndexOf(" ");
            index = Integer.parseInt(line.substring(lastSpaceIndex + 1));
            indexFromStart = lastSpaceIndex - (index * 2) + 1; // a b c d e 2 - dl = 11, lsi = 9, i*2 = 4, lsi - i*2 = 9 - 4 = 5;
            if (indexFromStart >= 0) {
                System.out.println(line.charAt(indexFromStart));
            }
        }
    }
}
