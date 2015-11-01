package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class LuckyTickets {

    private static Map<Integer, BigInteger []> map = new HashMap<>();
    private static final BigInteger ZERO = BigInteger.valueOf(0);

    public static void main (String[] args) throws IOException {
        map.put(1, new BigInteger[] {
                new BigInteger("1"),
                new BigInteger("1"),
                new BigInteger("1"),
                new BigInteger("1"),
                new BigInteger("1"),
                new BigInteger("1"),
                new BigInteger("1"),
                new BigInteger("1"),
                new BigInteger("1"),
                new BigInteger("1")
        }); // for level 1
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(countOfLuckyTickets(line));
        }
    }

    static BigInteger countOfLuckyTickets(String line) {
        int digitsCount = Integer.parseInt(line) / 2;

        if (digitsCount == 1) {
            return BigInteger.valueOf(10);
        }

        buildTableForLevel(digitsCount);

        return sumAllSums(map.get(digitsCount));
    }

    static BigInteger sumAllSums(BigInteger [] sumCount) {
        BigInteger result = ZERO;
        for (BigInteger sum : sumCount) {
            result = sum.multiply(sum).add(result);
        }
        return result;
    }

    static void buildTableForLevel(int level) {
        int curLevel = map.size();
        if (curLevel >= level) {
            return;
        }

        for (int i = curLevel + 1; i <= level; i++) {
            BigInteger [] prevLevel = map.get(i - 1);
            map.put(i, buildLevel(prevLevel, i));
        }
    }

    static BigInteger [] buildLevel(BigInteger [] prevLevel, int level) {
        BigInteger [] newLevel = new BigInteger[9 * level + 1];
        for (int i = 0; i < newLevel.length; i++) {
            newLevel[i] = getSumTenPrevSum(prevLevel, i);
        }
        return newLevel;
    }

    static BigInteger getSumTenPrevSum(BigInteger [] level, int index) {
        int startIndex = index - 9;
        if (startIndex < 0) {
            startIndex = 0;
        }

        if (index >= level.length) {
            index = level.length - 1;
        }
        BigInteger result = ZERO;
        for (int i = startIndex; i <= index; i++) {
            result = result.add(level[i]);
        }
        return result;
    }
}
