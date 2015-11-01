package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LuckyTickets {

    private static Map<Integer, int []> map = new HashMap<>();

    public static void main (String[] args) throws IOException {
        map.put(1, new int [] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}); // for level 1
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(countOfLuckyTickets(line));
        }
    }

    static int countOfLuckyTickets(String line) {
        int digitsCount = Integer.parseInt(line) / 2;

        if (digitsCount == 1) {
            return 10;
        }

        buildTableForLevel(digitsCount);

        return sumAllSums(map.get(digitsCount));
    }

    static int sumAllSums(int [] sumCount) {
        int result = 0;
        for (int sum : sumCount) {
            result += sum * sum;
        }
        return result;
    }

    static void buildTableForLevel(int level) {
        int curLevel = map.size();
        if (curLevel >= level) {
            return;
        }

        for (int i = curLevel + 1; i <= level; i++) {
            int [] prevLevel = map.get(i - 1);
            map.put(i, buildLevel(prevLevel, i));
        }
    }

    static int [] buildLevel(int [] prevLevel, int level) {
        int [] newLevel = new int[9 * level + 1];
        for (int i = 0; i < newLevel.length; i++) {
            newLevel[i] = getSumTenPrevSum(prevLevel, i);
        }
        return newLevel;
    }

    static int getSumTenPrevSum(int [] level, int index) {
        int startIndex = index - 9;
        if (startIndex < 0) {
            startIndex = 0;
        }

        if (index >= level.length) {
            index = level.length - 1;
        }
        int result = 0;
        for (int i = startIndex; i <= index; i++) {
            result += level[i];
        }
        return result;
    }
}
