package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BrokenLcd {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split(";");
            String [] lcd = parts[0].split(" ");
            String numberToShow = parts[1];
            System.out.println(isLcdBrokened(lcd, numberToShow));
        }
    }

    static int isLcdBrokened(String [] lcd, String number) {

        boolean isDot = number.contains(".");
        int checkUntil = lcd.length - number.length();
        if (isDot) {
            checkUntil++;
        }


        outer: for (int k = 0; k <= checkUntil; k++) {
            int j = k;
            for (int i = 0; i < number.length(); i++) {
                char c = number.charAt(i);
                boolean withDot = false;
                if (i < number.length() - 1 && number.charAt(i + 1) == '.') {
                    i++;
                    withDot = true;
                }
                if (!isPossibleToShow(lcd[j++], c, withDot)) {
                    continue outer;
                }
            }
            return 1;
        }

        return 0;
    }

    static boolean isPossibleToShow(String lcdPart, char c, boolean withDot) {
        if (withDot && lcdPart.charAt(7) != '1') {
            return false;
        }
        switch (c) {
            case '0': return check(lcdPart, 1, 2, 3, 4, 5, 6);
            case '1': return check(lcdPart, 2, 3);
            case '2': return check(lcdPart, 1, 2, 4, 5, 7);
            case '3': return check(lcdPart, 1, 2, 3, 4, 7);
            case '4': return check(lcdPart, 2, 3, 6, 7);
            case '5': return check(lcdPart, 1, 3, 4, 6, 7);
            case '6': return check(lcdPart, 1, 3, 4, 5, 6, 7);
            case '7': return check(lcdPart, 1, 2, 3);
            case '8': return check(lcdPart, 1, 2, 3, 4, 5, 6, 7);
            case '9': return check(lcdPart, 1, 2, 3, 4, 6, 7);
        }
        return false;
    }

    static boolean check(String lcdPart, int... parts) {
        for (int part : parts) {
            if (lcdPart.charAt(part - 1) != '1') {
                return false;
            }
        }
        return true;
    }
}
