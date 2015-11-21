package sponsoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AlternativeReality {

    static int [] COINS = {1, 5, 10, 25, 50};

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            int sum = Integer.parseInt(line);
            System.out.println(calcCoinChange(sum));
        }
    }

    private static int calcCoinChange(final int sum) {
        return countCoinChange(COINS, COINS.length, sum);
    }

    private static int countCoinChange(int [] array, int m, int n) {
        int [][] table = new int [n + 1][m]; // add 1 base case where n == 0
        for (int i = 0; i < m; i++) { // if n == 0, there is only 1 answer
            table[0][i] = 1;
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < m; j++) {
                int x = (i - array[j] >= 0) ? table[i - array[j]][j] : 0;

                int y = (j >= 1) ? table[i][j - 1] : 0;
                table[i][j] = x + y;
            }
        }
        return table[n][m - 1];
    }
}
