package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

public class SimpleSorting {
    private static DecimalFormat df = new DecimalFormat("0.000");
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getSortedNumbers(line));
        }
    }

    static String getSortedNumbers(String line) {
        String [] numbersString = line.split("\\s+");

        double [] numbers = new double[numbersString.length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Double.parseDouble(numbersString[i]);
        }
        Arrays.sort(numbers);

        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (int i = 0; i < numbers.length; i++) {
            sb.append(prefix);
            prefix = " ";
            sb.append(df.format(numbers[i]));
        }
        return sb.toString();
    }
}
