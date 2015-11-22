package sponsoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ColumnNames {
    private static final int BASE = 26;
    private static final char FIRST = 'A';

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            int column = Integer.parseInt(line);
            System.out.println(intToExcel(column));
        }
    }

    private static String intToExcel(int number) {
        int [] aux = new int[4]; // ZZZ is maximum

        int next = number;
        int reminder;
        int i = aux.length - 1;
        while (true) {
            reminder = next % BASE;
            aux[i] = reminder;
            i--;
            next = next / BASE;
            if (next == 0) {
                break;
            }
        }

        return merge(aux, i);
    }

    private static String merge(int [] aux, int maxIndex) {
        StringBuilder result = new StringBuilder();
        boolean decreaseNext = false;
        for (int i = aux.length - 1; i > maxIndex; i--) {
            int number = aux[i];
            if (decreaseNext && number != 0) {
                number--;
                decreaseNext = false;
            }
            if (number == 0) {
                decreaseNext = true;
                number = BASE;
                if (i - 1 == maxIndex) {
                    break;
                }
            }
            result.append((char) (FIRST + number - 1));
        }
        return result.reverse().toString();
    }
}
