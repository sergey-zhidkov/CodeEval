package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NModM {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split(",");
            System.out.println(getModuleRemainder(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
    }

    static int getModuleRemainder(int N, int M) {
        int D = N / M;
        return N - (D * M);
    }
}
