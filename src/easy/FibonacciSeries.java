package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FibonacciSeries {

    static int [] cachedFibs = new int [100];

    public static void main(String [] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            printFibonacci(line);
        }
    }

    public static void printFibonacci(String nString) {
        int n = Integer.parseInt(nString);

        System.out.println(fib(n));
    }

    public static int fib(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int fib1 = cachedFibs[n - 1];
        if (fib1 == 0) {
            cachedFibs[n - 1] = fib(n - 1);
            fib1 = cachedFibs[n - 1];
        }
        int fib2 = cachedFibs[n - 2];
        if (fib2 == 0) {
            cachedFibs[n - 2] = fib(n - 2);
            fib2 = cachedFibs[n - 2];
        }
        return fib1 + fib2;
    }
}
