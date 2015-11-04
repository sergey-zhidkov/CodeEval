package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LowestCommonAncestor {
    static BST bst;
    public static void main (String[] args) throws IOException {
        hardCodeBST();
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            String [] keys = line.split(" ");
            System.out.println(bst.lca(Integer.parseInt(keys[0]), Integer.parseInt(keys[1])));
        }
    }

    static void hardCodeBST() {
        bst = new BST();
        bst.put(30);
        bst.put(52);
        bst.put(8);
        bst.put(3);
        bst.put(20);
        bst.put(10);
        bst.put(29);
    }

}

class BST {
    private Node root;

    private class Node {
        private int key;
        private Node left;
        private Node right;
        public Node(int key) {
            this.key = key;
        }
    }

    public int lca(int key1, int key2) {
        return lca(root, key1, key2);
    }

    public int lca(Node node, int k1, int k2) {
        if (node == null) {
            throw new IllegalArgumentException();
        }

        int key = node.key;
        if (k1 < key && k2 < key) {
            return lca(node.left, k1, k2);
        } else if (k1 > key && k2 > key) {
            return lca(node.right, k1, k2);
        }
        return node.key;
    }

    public void put(int key) {
        root = put(root, key);
    }

    private Node put(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            node.left = put(node.left, key);
        } else if (key > node.key) {
            node.right = put(node.right, key);
        } else {
            node.key = key;
        }
        return node;
    }
}
