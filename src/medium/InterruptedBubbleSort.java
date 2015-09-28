package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InterruptedBubbleSort {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            doBubbleSortAndPrintResult(line);
        }
    }

    static void doBubbleSortAndPrintResult(String line) {
        String [] splittedLine = line.split("\\|");
        long loopCount = Long.parseLong(splittedLine[1].trim());
        long [] arrayToSort = parseArrayForSorting(splittedLine[0]);
        bubbleSortNTimes(arrayToSort, loopCount);

        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (long value : arrayToSort) {
            result.append(prefix);
            prefix = " ";
            result.append(value);
        }
        System.out.println(result);
    }

    static void bubbleSortNTimes(long [] array, long loopCount) {
        for (int i = 0; i < loopCount; i++) {
            bubbleSortOneIteration(array);
        }
    }

    static void bubbleSortOneIteration(long [] array) {
        if (array.length <= 1) {
            return;
        }
        long temp;
        for (int i = 1; i < array.length; i++) {
            temp = array[i];
            array[i] = array[i - 1];
            array[i - 1] = temp;
        }
    }

    static long [] parseArrayForSorting(String line) {
        String [] temp = line.trim().split("\\s+");
        long [] result = new long[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = Long.parseLong(temp[i]);
        }
        return result;
    }
}

