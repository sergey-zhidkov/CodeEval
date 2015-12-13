package medium;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PassTriangle {

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        int [][] triangle = new int[100][];
        int i = 0;
        while ((line = buffer.readLine()) != null) {
            String [] triangleRow = line.split(" ");
            triangle[i++] = stringToIntArray(triangleRow);
        }
        System.out.println(getShortestPath(triangle));
    }

    // Solution from Robert Sedgewick (Shortest Paths)
    static int getShortestPath(int [][] triangle) {
        EdgeWeightedDigraph graph = new  EdgeWeightedDigraph(triangle);
        AcyclicSP shortestPath = new AcyclicSP(graph, 0);
        int lastIndex = graph.V() - 1;
        double max = 0;
        for (int i = lastIndex; i > (lastIndex - 100); i--) {
            double path = shortestPath.distTo(i);
            if (path > max) {
                max = path;
            }
        }

        return (int) max;
    }

    private static int [] stringToIntArray(String [] array) {
        int [] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }
}

class  EdgeWeightedDigraph {

    private final int V; // number of vertices
    private List<DirectedEdge>[] adj; // adjacency lists

    public  EdgeWeightedDigraph(int [][] triangle) {
        int len = triangle.length;
        V = (((len + 1) * len) / 2) + 1;
        adj = (ArrayList<DirectedEdge>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }

        int rootIndex = 0;
        int bottomIndex;
        DirectedEdge edge = new DirectedEdge(rootIndex, rootIndex + 1, triangle[0][0]);
        addEdge(edge);
        rootIndex++;
        for (int i = 0; i < len - 1; i++) {
            int [] rowTop = triangle[i];
            int [] rowBottom = triangle[i + 1];
            for (int j = 0; j < rowTop.length; j++) {
                bottomIndex = rootIndex + j + rowTop.length;
                // add edges from left to right
                edge = new DirectedEdge(rootIndex + j, bottomIndex, rowBottom[j]);
                addEdge(edge);
                edge = new DirectedEdge(rootIndex + j, bottomIndex + 1, rowBottom[j + 1]);
                addEdge(edge);
            }
            rootIndex += rowTop.length;
        }
    }

    public int V() {
        return V;
    }

    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }
}

class DirectedEdge {

    private final int v;  // edge source
    private final int w;  // edge target
    private final int weight;  // edge weight

    public DirectedEdge(int v, int w, int weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int weight() {
        return weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }
}

class AcyclicSP {
    private DirectedEdge[] edgeTo;
    private double [] distTo;

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
//        for (int v = 0; v < G.V(); v++) {
//            distTo[v] = Double.NEGATIVE_INFINITY;
//        }

        distTo[s] = 0.0;
        Topological topological = new Topological(G);
        for (int v : topological.order()) {
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] < distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }
}

class Topological {
    private Iterable<Integer> order;   // topological order

    public Topological(EdgeWeightedDigraph G) {
        DepthFirstOrder dfs = new DepthFirstOrder(G);
        order = dfs.reversePost();
    }

    public Iterable<Integer> order() {
        return order;
    }
}

class DepthFirstOrder {
    private boolean[] marked;
    private ArrayList<Integer> reversePost; // vertices in reverse postorder

    public DepthFirstOrder(EdgeWeightedDigraph G) {
        reversePost = new ArrayList<>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        for (DirectedEdge edge : G.adj(v)) {
            if (!marked[edge.to()]) {
                dfs(G, edge.to());
            }
        }
        reversePost.add(v);
    }

    public Iterable<Integer> reversePost() {
        Collections.reverse(reversePost);
        return reversePost;
    }
}
