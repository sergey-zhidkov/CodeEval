package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ValidParentheses {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            System.out.println(isValidParentheses(line) ? "True" : "False");
        }
    }

    static boolean isValidParentheses(String line) {
        if (line.length() % 2 != 0) {
            return false;
        }

        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);

            if (c == ')' || c == ']' || c == '}') {
                if (stack.size() == 0) {
                    return false;
                }
                Character last = stack.pop();
                if ((last == '(' && c != ')')
                    || (last == '[' && c != ']')
                    || (last == '{' && c != '}')) {
                    return false;
                }
            } else {
                stack.push(c);
            }

        }

        return true;
    }
}
