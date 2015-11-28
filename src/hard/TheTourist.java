package hard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheTourist {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] edges = line.split("\\|");
//            SymbolGraph symbolGraph = new SymbolGraph(edges);
            EdgeWeightedGraph graph = new EdgeWeightedGraph();
            for (String edgeString : edges) {
                String [] parts = edgeString.trim().split(" ");
                Edge edge = new Edge(Integer.parseInt(parts[0]) - 1, Integer.parseInt(parts[1]) - 1,
                                     Integer.parseInt(parts[2]));
                graph.addEdge(edge);
            }

            graph.sortEdgesByWeight();

            if (graph.isConnected()) {
                KruskalMST mst = new KruskalMST(graph);
                System.out.println(mst.weight());
            } else {
                System.out.println("False");
            }
        }
    }
}

//class SymbolGraph {
//
//    private EdgeWeightedGraph graph;  // the graph
//    public SymbolGraph(String [] edges) {
//
//        Map<String, Integer> st = new HashMap<>();
//        for (String e : edges) {
//            String[] edgeParts = e.trim().split(" ");
//            for (int i = 0; i < 2; i++) {  // to associate each
//                if (!st.containsKey(edgeParts[i])) {  // distinct string
//                    st.put(edgeParts[i], st.size());  // with an index.
//                }
//            }
//        }
//
//        graph = new EdgeWeightedGraph(st.size());
//        // Second pass
//        for (String e : edges) {
//            String[] edgeParts = e.trim().split(" ");
//            int v = st.get(edgeParts[0]);
//            int w = st.get(edgeParts[1]);
//            Edge edge = new Edge(v, w, Integer.parseInt(edgeParts[2]));
//            graph.addEdge(edge);
//        }
//    }
//
//    public EdgeWeightedGraph G() {
//        return graph;
//    }
//}

class EdgeWeightedGraph {

    private List<Edge> edges = new ArrayList<>();
    private Map<Integer, Integer> duplicates = new HashMap();
    private int [] visited = new int[100];
//    private List<Edge>[] adj;
    private int maxCityIndex = 0;
//    private UF uf;

//    public EdgeWeightedGraph(int V) {
//        this.V = V;
//        adj = (ArrayList<Edge>[]) new ArrayList[V];
//        for (int v = 0; v < V; v++)
//            adj[v] = new ArrayList<>();
//        uf = new UF(V);
//    }

    public int V() {
        return maxCityIndex + 1;
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        if (v > maxCityIndex) {
            maxCityIndex = v;
        }
        if (w > maxCityIndex) {
            maxCityIndex = w;
        }
        visited[v]++;
        visited[w]++;

        Integer duplicateW = duplicates.get(v);
        Integer duplicateV = duplicates.get(w);
        if (duplicateW != null && duplicateW == w) {
            removeDuplicate(e);
        } else if (duplicateV != null && duplicateV == v) {
            removeDuplicate(e);
        }

        duplicates.put(v, w);
        duplicates.put(w, v);

//        adj[v].add(e);
//        adj[w].add(e);

        edges.add(e);
//        uf.union(v, w);
    }

    void removeDuplicate(Edge edge) {
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            int v1 = e.either();
            int w1 = e.other(v1);

            int v2 = edge.either();
            int w2 = edge.other(v2);

            if ((v1 == v2 && w1 == w2) || (v1 == w2 && w1 == v2)) {
//                System.out.println("Edge Removed " + e);
                edges.remove(i);
                break;
            }
        }
    }


    public void sortEdgesByWeight() {
        Collections.sort(edges);
    }

    public Iterable<Edge> getEdges() {
        return edges;
    }

    public boolean isConnected() {
        for (int i = 0; i <= maxCityIndex; i++) {
            if (visited[i] == 0) {
                return false;
            }
        }

        UF uf = new UF(V());
        for (Edge edge : edges) {
            int v = edge.either();
            int w = edge.other(v);
            uf.union(v, w);
        }

        return uf.isConnected();
    }
}


class Edge implements Comparable<Edge> {

    private final int v;  // one vertex
    private final int w;  // the other vertex
    private final int weight;  // edge weight

    public Edge(int v, int w, int weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) {
            return w;
        } else if (vertex == w) {
            return v;
        } else {
            throw new RuntimeException("Inconsistent edge");
        }
    }

    public int compareTo(Edge that) {
        if (this.weight() < that.weight()) {
            return -1;
        } else if (this.weight() > that.weight()) {
            return +1;
        } else {
            return 0;
        }
    }
}

class KruskalMST {

    private List<Edge> mst;

    public KruskalMST(EdgeWeightedGraph graph) {
        mst = new ArrayList<>();
        List<Edge> pq = (List<Edge>) graph.getEdges();

        UF uf = new UF(graph.V());
        for (int i = 0; i < pq.size() || mst.size() < graph.V() - 1; i++) {
            Edge e = pq.get(i);
            int v = e.either(), w = e.other(v); // and its vertices.
            if (uf.connected(v, w)) {
                continue;  // Ignore ineligible edges.
            }
            uf.union(v, w);  // Merge components.
            mst.add(e);  // Add edge to mst.
        }
    }

    public int weight() {
        int result = 0;
        for (Edge edge : mst) {
            result += edge.weight();
        }
        return result;
    }
}

class UF {

    private int[] id;  // access to component id (site indexed)

    public UF(int N) { // Initialize component id array.
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        return id[p];
    }

    public void union(int p, int q) { // Put p and q into the same component.
        int pID = find(p);
        int qID = find(q);
        if (pID == qID) {
            return;
        }
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) {
                id[i] = qID;
            }
        }
    }

    public boolean isConnected() {
        int first = id[1];
        for (int i = 1; i < id.length; i++) {
            if (id[i] != first) {
                return false;
            }
        }
        return true;
    }
}