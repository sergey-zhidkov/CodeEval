package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FlaviusJosephus {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split(",");
            System.out.println(getExecutionList(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
    }

    static String getExecutionList(int num, int period) {
        boolean [] executed = new boolean[num];
        StringBuilder result = new StringBuilder();


        int next = period - 1;
        int executedNum = 0;
//        result.append(period).append(" ");
//        executed[next] = true;
//        executedNum++;
        while (true) {
            while (true) {
                if (executed[next]) {
                    next++;
                    if (next >= num) {
                        next = 0;
                    }
                } else {
                    executed[next] = true;
                    executedNum++;
                    result.append(next).append(" ");
                    break;
                }
            }

            if (executedNum == num) {
                break;
            }

            int steps = 0;
            while (true) {
                next++;
                if (next >= num) {
                    next = 0;
                }
                if (!executed[next]) {
                    steps++;
                }
                if (steps == period) {
                    break;
                }
            }
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }
}
