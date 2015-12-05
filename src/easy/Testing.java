package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Testing {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            String [] parts = line.split("\\|");
            System.out.println(getButStatus(parts[0].trim(), parts[1].trim()));
        }
    }

    static String getButStatus(String error, String template) {
        int errorsNum = 0;
        for (int i = 0; i < template.length(); i++) {
            if (template.charAt(i) != error.charAt(i)) {
                errorsNum++;
            }
        }

        if (errorsNum == 0) {
            return "Done";
        } else if (errorsNum <= 2) {
            return "Low";
        } else if (errorsNum <= 4) {
            return "Medium";
        } else if (errorsNum <= 6) {
            return "High";
        } else {
            return "Critical";
        }
    }
}
