package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DecimalToBinary {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(convertDecimalToBinary(Integer.parseInt(line)));
        }
    }

    static String convertDecimalToBinary(int number) {
        String binary = Integer.toBinaryString(number);
        int firstOne = binary.indexOf('1');
        return firstOne >= 0 ? binary.substring(firstOne, binary.length()) : "0";
    }
}
