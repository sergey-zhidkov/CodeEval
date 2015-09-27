package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StackImplementation {

    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            pushAndPopValues(line.trim().split("\\s+"));
        }
    }

    static void pushAndPopValues(String [] values) {
        AlternateStack stack = new AlternateStack();
        for (String value : values) {
            stack.push(value);
        }

        StringBuilder result = new StringBuilder();
        String prefix = "";
        while (stack.canPop()) {
            result.append(prefix);
            prefix = " ";
            result.append(stack.pop());
        }
        System.out.println(result);
    }

    private static class AlternateStack {
        List<String> stack = new ArrayList<>();

        void push(String value) {
            stack.add(value);
        }

        String pop() {
            String result = stack.remove(stack.size() - 1);
            if (stack.size() > 0) stack.remove(stack.size() - 1);
            return result;
        }

        boolean canPop() {
            return stack.size() > 0;
        }
    }
}
