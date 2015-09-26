package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FindTheHighestScore {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
            // Process line of input Here
            printHighestScores(line.split("\\|"));
        }
    }

    static void printHighestScores(String [] lines) {
        String [] firstLine = lines[0].trim().split("\\s+");
        int len = firstLine.length;
        int [] highestScores = new int[len];
        for (int i = 0; i < len; i++) {
            highestScores[i] = Integer.parseInt(firstLine[i]);
        }

        // find highest scores
        for (int i = 1; i < lines.length; i++) {
            replaceHighestScores(lines[i].trim().split("\\s+"), highestScores);
        }

        // print
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (int score : highestScores) {
            sb.append(prefix);
            prefix = " ";
            sb.append(score);
        }
        System.out.println(sb);
    }

    static void replaceHighestScores(String [] scores, int [] highestScores) {
        for (int i = 0; i < highestScores.length; i++) {
            int score = Integer.parseInt(scores[i]);
            if (score > highestScores[i]) {
                highestScores[i] = score;
            }
        }
    }
}

