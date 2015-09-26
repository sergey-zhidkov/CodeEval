package easy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class TimeToEat {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
            // Process line of input Here
            printSortedSchedule(line.split("\\s+"));
        }
    }

    static void printSortedSchedule(String [] stringTimeStamps) {
        TimeStamp [] timeStamps = new TimeStamp[stringTimeStamps.length];
        for (int i = 0; i < stringTimeStamps.length; i++) {
            timeStamps[i] = new TimeStamp(stringTimeStamps[i]);
        }

        Arrays.sort(timeStamps);
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (TimeStamp timeStamp : timeStamps) {
            sb.append(prefix);
            prefix = " ";
            sb.append(timeStamp.toString());
        }
        System.out.println(sb);
    }

    private static class TimeStamp implements Comparable<TimeStamp> {
        int hours;
        int minutes;
        int seconds;
        TimeStamp(String timestamp) {
            String [] temp = timestamp.split(":");
            hours = Integer.parseInt(temp[0]);
            minutes = Integer.parseInt(temp[1]);
            seconds = Integer.parseInt(temp[2]);
        }

        @Override
        public int compareTo(TimeStamp t) {
            if (hours < t.hours) {
                return 1;
            } else if (hours > t.hours) {
                return -1;
            }
            // hours are equal
            if (minutes < t.minutes) {
                return 1;
            } else if (minutes > t.minutes) {
                return -1;
            }
            // minutes are equal
            if (seconds < t.seconds) {
                return 1;
            } else if (seconds > t.seconds) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return (hours < 10 ? "0" + hours : hours)
                    + ":" + (minutes < 10 ? "0" + minutes : minutes)
                    + ":" + (seconds < 10 ? "0" + seconds : seconds);
        }
    }
}

