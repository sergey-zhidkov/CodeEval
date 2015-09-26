package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;

public class LongestLines {

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        int linesCount = Integer.parseInt(buffer.readLine());
        TreeMap<Integer, String> longestLines = new TreeMap<>();
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            if (longestLines.size() >= linesCount) {
                Integer firstKey = longestLines.firstKey();
                if (firstKey < line.length()) {
                    longestLines.remove(firstKey);
                    longestLines.put(line.length(), line);
                }
            } else {
                longestLines.put(line.length(), line);
            }
        }

        NavigableMap<Integer, String> reverseLongestLines = longestLines.descendingMap();
        Collection<String> collection = reverseLongestLines.values();

        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (String longestLine : collection) {
            result.append(prefix);
            prefix = "\n";
            result.append(longestLine);
        }
        System.out.println(result);
    }
}
