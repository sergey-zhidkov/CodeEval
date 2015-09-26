package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class APileOfBricks {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
            // Process line of input Here
            // [-1,-5] [5,-2]|(1 [4,7,8] [2,9,0]);(2 [0,7,1] [5,9,8])
            printBricksThatCanPassThroughTheHole(line.split("\\|"));
        }
    }

    static void printBricksThatCanPassThroughTheHole(String [] holeAndBricks) {
        Hole hole = new Hole(holeAndBricks[0]);
        String [] bricks = holeAndBricks[1].split(";");

        List<Integer> indexes = new ArrayList<Integer>();


        for (String brickString : bricks) {
            Brick brick = new Brick(brickString);
            if (hole.isBrickCanPassThrough(brick)) {
                indexes.add(brick.index);
            }
        }

        Collections.sort(indexes);
        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (Integer index : indexes) {
            result.append(prefix);
            prefix = ",";
            result.append(index);
        }

        if (result.length() == 0) {
            result.append("-");
        }

        System.out.println(result);
    }

    private static class Hole {
        int width;
        int height;
        Hole(String stringToParse) {
            parseString(stringToParse);
        }

        private void parseString(String stringToParse) {
            // [-1,-5] [5,-2]
            String [] coords = stringToParse.split("\\s+");
            String [] firstCoord = coords[0].substring(1, coords[0].length() - 1).split(",");
            String [] secondCoord = coords[1].substring(1, coords[1].length() - 1).split(",");

            int x1 = Integer.parseInt(firstCoord[0]);
            int y1 = Integer.parseInt(firstCoord[1]);

            int x2 = Integer.parseInt(secondCoord[0]);
            int y2 = Integer.parseInt(secondCoord[1]);

            width = Math.abs(x1 - x2);
            height = Math.abs(y1 - y2);
        }

        boolean isBrickCanPassThrough(Brick brick) {
            // height and width
            if (brick.height <= height && brick.width <= width) {
                return true;
            }
            if (brick.width <= height && brick.height <= width) {
                return true;
            }
            // length and height
            if (brick.length <= height && brick.height <= width) {
                return true;
            }
            if (brick.height <= height && brick.length <= width) {
                return true;
            }
            // length and width
            if (brick.length <= height && brick.width <= width) {
                return true;
            }
            if (brick.width <= height && brick.length <= width) {
                return true;
            }
            return false;
        }
    }

    private static class Brick {
        int index;
        int width;
        int height;
        int length;

        Brick(String stringToParse) {
            parseString(stringToParse);
        }

        private void parseString(String stringToParse) {
            // (1 [4,7,8] [2,9,0])
            stringToParse = stringToParse.substring(1, stringToParse.length() - 1); // remove '(' and ')'
            String [] temp = stringToParse.split("\\s+");
            index = Integer.parseInt(temp[0]);

            String [] firstCoords = temp[1].substring(1, temp[1].length() - 1).split(",");
            String [] secondCoords = temp[2].substring(1, temp[2].length() - 1).split(",");

            int x1 = Integer.parseInt(firstCoords[0]);
            int y1 = Integer.parseInt(firstCoords[1]);
            int z1 = Integer.parseInt(firstCoords[2]);

            int x2 = Integer.parseInt(secondCoords[0]);
            int y2 = Integer.parseInt(secondCoords[1]);
            int z2 = Integer.parseInt(secondCoords[2]);

            width = Math.abs(x1 - x2);
            height = Math.abs(y1 - y2);
            length = Math.abs(z1 - z2);
        }
    }
}

