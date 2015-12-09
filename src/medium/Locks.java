package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Locks {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] parts = line.split(" ");
            System.out.println(countUnlockedDoors(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
    }

    static int countUnlockedDoors(int doorCount, int iterations) {
        boolean [] doors = new boolean[doorCount]; // all unlocked = false
        iterations -= 1;

        for (int i = 0; i < iterations; i++) {
            for (int j = 1; j < doorCount; j += 2) {
                doors[j] = true; // lock
            }
            for (int j = 2; j < doorCount; j += 3) {
                doors[j] = !doors[j];
            }
        }

        doors[doorCount - 1] = !doors[doorCount - 1]; // last door
        int result = 0;
        for (int i = 0; i < doorCount; i++) {
            if (!doors[i]) {
                result++;
            }
        }
        return result;
    }
}
