package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class EmailValidation {
//    static String PATTERN = "^[_a-zA-Z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]+)$";
//    static String PATTERN = "^[^@<>\\\\\"]+@[^@\\.]+(\\.[^@\\.*]+)+$";
    static String PATTERN = "^((\".*\")|([^@\\\\<>\"]+))@[^@\\.]+(\\.[^@\\.*]+)+$";
    static Pattern pattern = Pattern.compile(PATTERN);

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
            // Process line of input Here
//            validateEmail(line);
            if (pattern.matcher(line).matches()) {
                System.out.println("true :" + line);
                System.out.println("true");
            } else {
                System.out.println("false :" + line);
                System.out.println("false");
            }
        }
    }

    static void validateEmail(String email) {
        int atIndex = email.indexOf("@");
        int lastAtIndex = email.lastIndexOf("@");
        int spaceIndex = email.indexOf(" ");
        String partBeforAt = email.substring(0, atIndex >= 0 ? atIndex : email.length());
        String partAfterAt = email.substring(atIndex + 1, email.length());
        int dotIndex = partAfterAt.indexOf(".");
        int lastDotIndex = partAfterAt.lastIndexOf(".");

        if (atIndex >= 1
                && atIndex == lastAtIndex
                && (atIndex != email.length() - 1)
                && spaceIndex < 0
                && email.length() >= 3
                && dotIndex > 0
                && (lastDotIndex != partAfterAt.length() - 1)
                && !isThereIsTwoDotsInRow(partAfterAt)) {

            System.out.println("true : " + email);
            return;
        }
        System.out.println("false" + ": " + email);
    }

    static boolean isThereIsTwoDotsInRow(String partAfterAt) {
        boolean result = false;
        int len = partAfterAt.length();
        for (int i = 0; i < len; i++) {
            char first = partAfterAt.charAt(i);
            if (i + 1 >= len) {
                break;
            }
            if (first == '.' && partAfterAt.charAt(i + 1) == '.') {
                result = true;
                break;
            }
        }

        return result;
    }
}

