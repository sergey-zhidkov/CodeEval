package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FizzBuzz {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
            // Process line of input Here
            printFizzBuzz(line);
        }
    }

    static void printFizzBuzz(String data) {
        String[] dataArray = data.split("\\s+");
        int first = Integer.parseInt(dataArray[0]);
        int second = Integer.parseInt(dataArray[1]);
        int howMany = Integer.parseInt(dataArray[2]);

        for (int i = 1; i <= howMany; i++) {
            if (i % first == 0 && i % second == 0) {
                System.out.print("FB");
            } else if (i % first == 0) {
                System.out.print("F");
            } else if (i % second == 0) {
                System.out.print("B");
            } else {
                System.out.print(i);
            }
            System.out.print(" ");
        }
        System.out.println();
    }
}

