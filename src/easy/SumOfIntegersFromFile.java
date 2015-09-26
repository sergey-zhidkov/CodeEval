package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SumOfIntegersFromFile {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        int result = 0;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            result += Integer.parseInt(line);
        }
        System.out.println(result);
    }
}

