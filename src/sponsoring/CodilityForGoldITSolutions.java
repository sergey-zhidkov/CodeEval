package sponsoring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CodilityForGoldITSolutions {
    public static void main (String[] args) throws IOException {
        String test1 = "00:01:07,400-234-090\n00:05:01,701-080-080\n00:05:00,400-234-090";
        System.out.println(new Solution().solution(test1));
        String test2 = "00:01:00,400-234-090\n00:01:00,701-080-080\n00:00:00,400-234-090\n00:00:05,401-234-090";
        System.out.println(new Solution().solution(test2));
    }
}

class Solution {
    private static final int ONE_MINUTE_IN_SECONDS = 60;
    private static final int FIVE_MINUTES_IN_SECONDS = 5 * ONE_MINUTE_IN_SECONDS;

    private static final int CHARGE_PLAN1 = 3;
    private static final int CHARGE_PLAN2 = 150;

    private Map<String, Integer> phoneToTimeMap;

    public int solution(String S) {
        String [] lines = S.split("\\n");
        phoneToTimeMap = new HashMap<>();
        for (String line : lines) {
            String [] parts = line.split(",");
            String phone = parts[1];
            int time = parseStringToSeconds(parts[0]);
            addPhoneAndTimeToMap(phone, time);
        }

        removePhoneWithLongestTotalDuration();
        return getBill();
    }

    private void addPhoneAndTimeToMap(String phone, int time) {
        Integer value = phoneToTimeMap.get(phone);
        if (value == null) {
            phoneToTimeMap.put(phone, time);
        } else {
            phoneToTimeMap.put(phone, time + value);
        }
    }

    private void removePhoneWithLongestTotalDuration() {
        phoneToTimeMap.remove(getPhoneWithLongestDuration());
    }

    private String getPhoneWithLongestDuration() {
        Set<String> phones = phoneToTimeMap.keySet();
        List<String> longestDurationPhones = new ArrayList<>();
        int longestDuration = 0;
        for (String phone : phones) {
            int duration = phoneToTimeMap.get(phone);
            if (duration == longestDuration) {
                longestDurationPhones.add(phone);
            } else if (duration > longestDuration) {
                longestDuration = duration;
                longestDurationPhones.clear();
                longestDurationPhones.add(phone);
            }
        }

        return getPhoneWithSmallestNumericalValue(longestDurationPhones);
    }

    private String getPhoneWithSmallestNumericalValue(List<String> phones) {
        if (phones.size() == 1) {
            return phones.get(0);
        }

        int [] numericalValues = new int[phones.size()];
        for (int i = 0; i < phones.size(); i++) {
            String phone = phones.get(i);
            numericalValues[i] = Integer.parseInt(phone.replace("-", ""));
        }

        int smallestIndex = 0;
        int smallestValue = numericalValues[0];
        for (int i = 1; i < numericalValues.length; i++) {
            if (numericalValues[i] > smallestValue) {
                smallestValue = numericalValues[i];
                smallestIndex = i;
            }
        }

        return phones.get(smallestIndex);
    }

    private int getBill() {
        int result = 0;
        Collection<Integer> values = phoneToTimeMap.values();
        for (Integer time : values) {
            result += getChargeAmountForTime(time);
        }

        return result;
    }

    private int getChargeAmountForTime(int time) {
        if (time < FIVE_MINUTES_IN_SECONDS) {
            return time * CHARGE_PLAN1;
        } else {
            int minutes = time / ONE_MINUTE_IN_SECONDS;
            if (time % ONE_MINUTE_IN_SECONDS != 0) {
                minutes += 1;
            }
            return minutes * CHARGE_PLAN2;
        }
    }

    private static int parseStringToSeconds(String stringValue) {
        int result = 0;
        String [] timeParts = stringValue.split(":");
        result += Integer.parseInt(timeParts[0]) * 3600; // hours
        result += Integer.parseInt(timeParts[1]) * ONE_MINUTE_IN_SECONDS; // minutes
        result += Integer.parseInt(timeParts[2]); // seconds
        return result;
    }
}