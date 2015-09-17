package sponsoring;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class StringPermutations {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
            // Process line of input Here
            permutate(line);
        }
    }

    public static void permutate(String line) {
        StringBuilder result = new StringBuilder();
        permutate("", sortText(line), result);
        result.deleteCharAt(result.length() - 1);
        System.out.println(result.toString());
    }

    public static void permutate(String prefix, String line, StringBuilder result) {
        if (line.isEmpty()) {
            result.append(prefix).append(',');
        } else {
            for (int i = 0; i < line.length(); i++) {
                permutate(prefix + line.charAt(i), line.substring(0, i) + line.substring(i + 1, line.length()), result);
            }
        }
    }

    public static String sortText(String line) {
        char[] array = line.toCharArray();
        Arrays.sort(array);
        return new String(array);
    }

}
//hat
//abc
//Zu6