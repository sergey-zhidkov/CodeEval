package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MinimumCoins {
    private static final int [] COINS = {1, 3, 5};

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getMininumCoins(Integer.parseInt(line)));
        }
    }

    private static int getMininumCoins(int sum) {
        int table[] = new int[sum + 1];
        table[0] = 0;

        for (int i = 1; i < table.length; i++) {
            table[i] = Integer.MAX_VALUE;
        }

        for (int i = 1; i <= sum; i++) {
            for (int j = 0; j < COINS.length; j++) {
                if (COINS[j] <= i) {
                    int subResult = table[i - COINS[j]];
                    if (subResult != Integer.MAX_VALUE && subResult + 1 < table[i]) {
                        table[i] = subResult + 1;
                    }
                }
            }
        }

        return table[sum];
    }
}
