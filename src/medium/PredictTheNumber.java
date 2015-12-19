package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PredictTheNumber {

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getValueAtPosition(Long.parseLong(line), 0));
        }
    }

    static int getValueAtPosition(long value, int step) {
        if (value == 0) {
            step = step % 3;
            if (step == 0) {
                return 0;
            }
            if (step == 1) {
                return 1;
            }
            return 2;
        }
        long closest = getClosestPowerOfTwo(value + 1);

        long half = closest / 2;
        long index = value - half;

        return getValueAtPosition(index, step + 1);
    }

    static long getClosestPowerOfTwo(long x) {
        x = x - 1;
        x = x | (x >> 1);
        x = x | (x >> 2);
        x = x | (x >> 4);
        x = x | (x >> 8);
        x = x | (x >> 16);
        x = x | (x >> 32);
        x = x + 1;
        return x;
    }
}
