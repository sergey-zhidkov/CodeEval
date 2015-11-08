package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrganizationalHierarchy {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        Tree tree;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            tree = new Tree(line.split("\\|"));
            System.out.println(tree.toString());
        }
    }
}

class Tree {
    private Node root;
    public Tree(String [] chunks) {
        root = new Node(chunks[0].trim().charAt(0));

        for (String chunk1 : chunks) {
            String chunk = chunk1.trim();
            put(chunk.charAt(0), chunk.charAt(1));
        }
    }

    public Node get(char c) {
        return get(root, c);
    }

    private Node get(Node node, char c) {
        if (c == node.name) { return node; }

        Node result = null;
        for (Node n : node.subordinates) {
            result = get(n, c);
            if (result != null) {
                return result;
            }
        }
        return result;
    }

    public void put(char manager, char subordinate) {
        Node node = get(manager);
        node.put(subordinate);
    }

    @Override
    public String toString() {
        return root.toString();
    }

    private static class Node implements Comparable<Node> {
        List<Node> subordinates = new ArrayList<>();
        char name;

        Node(char c) {
            this.name = c;
        }

        public void put(char c) {
            subordinates.add(new Node(c));
        }

        @Override
        public String toString() {
            return String.valueOf(name) + subordinatesToString();
        }

        private String subordinatesToString() {
            if (subordinates.isEmpty()) {
                return "";
            }
            StringBuilder result = new StringBuilder();
            result.append(" [");
            Collections.sort(subordinates);
            String prefix = "";
            for (Node node : subordinates) {
                result.append(prefix);
                prefix = ", ";
                result.append(node.toString());
            }
            result.append("]");
            return result.toString();
        }

        @Override
        public int compareTo(Node o) {
            if (name < o.name) return -1;
            if (name > o.name) return 1;
            return 0;
        }
    }
}