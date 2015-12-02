package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UniqueElements {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(getUniqueList(line.split(",")));
        }
    }

    static String getUniqueList(String [] list) {
        StringBuilder result = new StringBuilder();
        String next = list[0];
        result.append(next).append(",");
        for (int i = 1; i < list.length; i++) {
            if (!next.equals(list[i])) {
                next = list[i];
                result.append(next).append(",");
            }
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }
}
