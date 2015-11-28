package hard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MarsNetworks {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            // Process line of input Here
            String [] pointsToParse = line.split(" ");
            List<Point2D> points = new ArrayList<>();
            for (String p : pointsToParse) {
                String [] parts = p.split(",");
                Point2D point = new Point2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                points.add(point);
            }

            EdgeWeightedGraph graph = new EdgeWeightedGraph(points.size());
            Point2D p1;
            Point2D p2;
            for (int i = 0; i < points.size(); i++) {
                p1 = points.get(i);
                for (int j = i + 1; j < points.size(); j++) {
                    p2 = points.get(j);
                    double squareDist = p2.squareDistTo(p1);
                    if (squareDist > 0) {
                        Edge edge = new Edge(i, j, squareDist);
                        graph.addEdge(edge);
                    }
                }
            }

            graph.sortEdgesByWeight();
            KruskalMST mst = new KruskalMST(graph);
            System.out.println(mst.weight());
        }
    }

    static class Point2D {
        private final int x;
        private final int y;
        public Point2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double squareDistTo(Point2D p) {
            int diffX = p.x - x;
            int diffY = p.y - y;
            return diffX * diffX + diffY * diffY;
        }
    }


    static class EdgeWeightedGraph {

        private List<Edge> edges = new ArrayList<>();
        private final int V;

        public EdgeWeightedGraph(int V) {
            this.V = V;
        }

        public int V() {
            return V;
        }

        public void addEdge(Edge e) {
            edges.add(e);
        }

        public void sortEdgesByWeight() {
            Collections.sort(edges);
        }

        public Iterable<Edge> getEdges() {
            return edges;
        }
    }


    static class Edge implements Comparable<Edge> {

        private final int v;  // one vertex
        private final int w;  // the other vertex
        private final double weight;  // edge weight

        public Edge(int v, int w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        public double weight() {
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

    static class KruskalMST {

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
            double result = 0;
            for (Edge edge : mst) {
                result += Math.sqrt(edge.weight());
            }
            return (int) Math.ceil(result);
        }
    }

    static class UF {

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
}


