package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BlackCard {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split("\\|");
            System.out.println(getWinner(parts[0].trim().split(" "), Integer.parseInt(parts[1].trim())));
        }
    }

    static String getWinner(String [] pirates, int number) {
        boolean [] dead = new boolean[pirates.length];

        for (int i = 0; i < pirates.length - 1; i++) {
            int j = 1;
            int k = 0;
            while (true) {
                if (!dead[k]) {
                    j++;
                    if (j - 1 == number) {
                        dead[k] = true;
                        break;
                    }
                }
                k++;
                if (k == pirates.length) {
                    k = 0;
                }
            }
        }

        String result = "";
        for (int i = 0; i < pirates.length; i++) {
            if (!dead[i]) {
                result = pirates[i];
                break;
            }
        }

        return result;
    }

}
