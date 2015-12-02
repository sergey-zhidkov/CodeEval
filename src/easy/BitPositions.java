package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BitPositions {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split(",");
            System.out.println(isBitsInSamePositions(Integer.parseInt(parts[0]),
                                                     Integer.parseInt(parts[1]),
                                                     Integer.parseInt(parts[2])));
        }
    }

    static boolean isBitsInSamePositions(int number, int index1, int index2) {
        String bits = Integer.toBinaryString(number);
        int len = bits.length();
        return bits.charAt(len - index1) == bits.charAt(len - index2);
    }
}
