//package hard;
//
//import com.sun.corba.se.impl.orbutil.graph.Graph;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.Socket;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.InputMismatchException;
//import java.util.Iterator;
//import java.util.Locale;
//import java.util.NoSuchElementException;
//import java.util.Scanner;
//import java.util.Stack;
//import java.util.TreeMap;
//import java.util.regex.Pattern;
//
///******************************************************************************
// *  Compilation:  javac KruskalMST.java
// *  Execution:    java  KruskalMST filename.txt
// *  Dependencies: EdgeWeightedGraph.java Edge.java Queue.java
// *                UF.java In.java StdOut.java
// *  Data files:   http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
// *                http://algs4.cs.princeton.edu/43mst/mediumEWG.txt
// *                http://algs4.cs.princeton.edu/43mst/largeEWG.txt
// *
// *  Compute a minimum spanning forest using Kruskal's algorithm.
// *
// *  %  java KruskalMST tinyEWG.txt
// *  0-7 0.16000
// *  2-3 0.17000
// *  1-7 0.19000
// *  0-2 0.26000
// *  5-7 0.28000
// *  4-5 0.35000
// *  6-2 0.40000
// *  1.81000
// *
// *  % java KruskalMST mediumEWG.txt
// *  168-231 0.00268
// *  151-208 0.00391
// *  7-157   0.00516
// *  122-205 0.00647
// *  8-152   0.00702
// *  156-219 0.00745
// *  28-198  0.00775
// *  38-126  0.00845
// *  10-123  0.00886
// *  ...
// *  10.46351
// *
// ******************************************************************************/
//
///**
// *  The <tt>KruskalMST</tt> class represents a data type for computing a
// *  <em>minimum spanning tree</em> in an edge-weighted graph.
// *  The edge weights can be positive, zero, or negative and need not
// *  be distinct. If the graph is not connected, it computes a <em>minimum
// *  spanning forest</em>, which is the union of minimum spanning trees
// *  in each connected component. The <tt>weight()</tt> method returns the
// *  weight of a minimum spanning tree and the <tt>edges()</tt> method
// *  returns its edges.
// *  <p>
// *  This implementation uses <em>Krusal's algorithm</em> and the
// *  union-find data type.
// *  The constructor takes time proportional to <em>E</em> log <em>E</em>
// *  and extra space (not including the graph) proportional to <em>V</em>,
// *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
// *  Afterwards, the <tt>weight()</tt> method takes constant time
// *  and the <tt>edges()</tt> method takes time proportional to <em>V</em>.
// *  <p>
// *  For additional documentation,
// *  see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
// *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
//class KruskalMST {
//    private static final double FLOATING_POINT_EPSILON = 1E-12;
//
//    private double weight;                        // weight of MST
//    private Queue<Edge> mst = new Queue<Edge>();  // edges in MST
//
//    /**
//     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
//     * @param G the edge-weighted graph
//     */
//    public KruskalMST(EdgeWeightedGraph G) {
//        // more efficient to build heap by passing array of edges
//        MinPQ<Edge> pq = new MinPQ<Edge>();
//        for (Edge e : G.edges()) {
//            pq.insert(e);
//        }
//
//        // run greedy algorithm
//        UF uf = new UF(G.V());
//        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
//            Edge e = pq.delMin();
//            int v = e.either();
//            int w = e.other(v);
//            if (!uf.connected(v, w)) { // v-w does not create a cycle
//                uf.union(v, w);  // merge v and w components
//                mst.enqueue(e);  // add edge e to mst
//                weight += e.weight();
//            }
//        }
//
//        // check optimality conditions
//        assert check(G);
//    }
//
//    /**
//     * Returns the edges in a minimum spanning tree (or forest).
//     * @return the edges in a minimum spanning tree (or forest) as
//     *    an iterable of edges
//     */
//    public Iterable<Edge> edges() {
//        return mst;
//    }
//
//    /**
//     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
//     * @return the sum of the edge weights in a minimum spanning tree (or forest)
//     */
//    public double weight() {
//        return weight;
//    }
//
//    // check optimality conditions (takes time proportional to E V lg* V)
//    private boolean check(EdgeWeightedGraph G) {
//
//        // check total weight
//        double total = 0.0;
//        for (Edge e : edges()) {
//            total += e.weight();
//        }
//        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
//            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
//            return false;
//        }
//
//        // check that it is acyclic
//        UF uf = new UF(G.V());
//        for (Edge e : edges()) {
//            int v = e.either(), w = e.other(v);
//            if (uf.connected(v, w)) {
//                System.err.println("Not a forest");
//                return false;
//            }
//            uf.union(v, w);
//        }
//
//        // check that it is a spanning forest
//        for (Edge e : G.edges()) {
//            int v = e.either(), w = e.other(v);
//            if (!uf.connected(v, w)) {
//                System.err.println("Not a spanning forest");
//                return false;
//            }
//        }
//
//        // check that it is a minimal spanning forest (cut optimality conditions)
//        for (Edge e : edges()) {
//
//            // all edges in MST except e
//            uf = new UF(G.V());
//            for (Edge f : mst) {
//                int x = f.either(), y = f.other(x);
//                if (f != e) uf.union(x, y);
//            }
//
//            // check that e is min weight edge in crossing cut
//            for (Edge f : G.edges()) {
//                int x = f.either(), y = f.other(x);
//                if (!uf.connected(x, y)) {
//                    if (f.weight() < e.weight()) {
//                        System.err.println("Edge " + f + " violates cut optimality conditions");
//                        return false;
//                    }
//                }
//            }
//
//        }
//
//        return true;
//    }
//}
//
//
///**
// *  The <tt>SymbolGraph</tt> class represents an undirected graph, where the
// *  vertex names are arbitrary strings.
// *  By providing mappings between string vertex names and integers,
// *  it serves as a wrapper around the
// *  {@link Graph} data type, which assumes the vertex names are integers
// *  between 0 and <em>V</em> - 1.
// *  It also supports initializing a symbol graph from a file.
// *  <p>
// *  This implementation uses an {@link ST} to map from strings to integers,
// *  an array to map from integers to strings, and a {@link Graph} to store
// *  the underlying graph.
// *  The <em>index</em> and <em>contains</em> operations take time
// *  proportional to log <em>V</em>, where <em>V</em> is the number of vertices.
// *  The <em>name</em> operation takes constant time.
// *  <p>
// *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a> of
// *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
//class SymbolGraph {
//    private ST<String, Integer> st;  // string -> index
//    private String[] keys;           // index  -> string
//    private EdgeWeightedGraph G;
//
//    /**
//     * Initializes a graph from a file using the specified delimiter.
//     * Each line in the file contains
//     * the name of a vertex, followed by a list of the names
//     * of the vertices adjacent to that vertex, separated by the delimiter.
//     * @param filename the name of the file
//     */
//    public SymbolGraph(String filename) {
//        st = new ST<String, Integer>();
//        String delimiter = " ";
//
//        // First pass builds the index by reading strings to associate
//        // distinct strings with an index
//        In in = new In(filename);
//        // while (in.hasNextLine()) {
//        while (!in.isEmpty()) {
//            String[] a = in.readLine().split(delimiter);
//            for (int i = 0; i < a.length - 1; i++) {
//                if (!st.contains(a[i]))
//                    st.put(a[i], st.size());
//            }
//        }
//        StdOut.println("Done reading " + filename);
//
//        // inverted index to get string keys in an aray
//        keys = new String[st.size()];
//        for (String name : st.keys()) {
//            keys[st.get(name)] = name;
//        }
//
//        // second pass builds the graph by connecting first vertex on each
//        // line to all others
//        G = new EdgeWeightedGraph(st.size());
//        in = new In(filename);
//        while (in.hasNextLine()) {
//            String[] a = in.readLine().split(delimiter);
//            int v = st.get(a[0]);
//            int w = st.get(a[1]);
//            Edge e = new Edge(v, w, Double.parseDouble(a[2]));
//            G.addEdge(e);
////            for (int i = 1; i < a.length; i++) {
////                int w = st.get(a[i]);
////                G.addEdge(v, w);
////            }
//        }
//    }
//
//    /**
//     * Does the graph contain the vertex named <tt>s</tt>?
//     * @param s the name of a vertex
//     * @return <tt>true</tt> if <tt>s</tt> is the name of a vertex, and <tt>false</tt> otherwise
//     */
//    public boolean contains(String s) {
//        return st.contains(s);
//    }
//
//    /**
//     * Returns the integer associated with the vertex named <tt>s</tt>.
//     * @param s the name of a vertex
//     * @return the integer (between 0 and <em>V</em> - 1) associated with the vertex named <tt>s</tt>
//     */
//    public int index(String s) {
//        return st.get(s);
//    }
//
//    /**
//     * Returns the name of the vertex associated with the integer <tt>v</tt>.
//     * @param v the integer corresponding to a vertex (between 0 and <em>V</em> - 1)
//     * @return the name of the vertex associated with the integer <tt>v</tt>
//     */
//    public String name(int v) {
//        return keys[v];
//    }
//
//    /**
//     * Returns the graph assoicated with the symbol graph. It is the client's responsibility
//     * not to mutate the graph.
//     * @return the graph associated with the symbol graph
//     */
//    public EdgeWeightedGraph G() {
//        return G;
//    }
//
//
//    /**
//     * Unit tests the <tt>SymbolGraph</tt> data type.
//     */
//    public static void main(String[] args) {
////        String filename  = args[0];
//        SymbolGraph sg = new SymbolGraph("D:\\idea\\codeeval\\src\\hard\\test3.txt");
//        EdgeWeightedGraph G = sg.G();
//        CC cc = new CC(G);
//        if (cc.count() == 1) {
//            KruskalMST mst = new KruskalMST(G);
//            System.out.println(mst.weight());
//        } else {
//            System.out.println("FALSE" + " " + cc.count());
//        }
////        while (StdIn.hasNextLine()) {
////            String source = StdIn.readLine();
////            if (sg.contains(source)) {
////                int s = sg.index(source);
////                for (int v : G.adj(s)) {
////                    StdOut.println("   " + sg.name(v));
////                }
////            }
////            else {
////                StdOut.println("input not contain '" + source + "'");
////            }
////        }
//    }
//}
//
//
///******************************************************************************
// *  Compilation:  javac Edge.java
// *  Execution:    java Edge
// *  Dependencies: StdOut.java
// *
// *  Immutable weighted edge.
// *
// ******************************************************************************/
//
///**
// *  The <tt>Edge</tt> class represents a weighted edge in an
// *  {@link EdgeWeightedGraph}. Each edge consists of two integers
// *  (naming the two vertices) and a real-value weight. The data type
// *  provides methods for accessing the two endpoints of the edge and
// *  the weight. The natural order for this data type is by
// *  ascending order of weight.
// *  <p>
// *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
// *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
//class Edge implements Comparable<Edge> {
//
//    private final int v;
//    private final int w;
//    private final double weight;
//
//    /**
//     * Initializes an edge between vertices <tt>v</tt> and <tt>w</tt> of
//     * the given <tt>weight</tt>.
//     *
//     * @param  v one vertex
//     * @param  w the other vertex
//     * @param  weight the weight of this edge
//     * @throws IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt>
//     *         is a negative integer
//     * @throws IllegalArgumentException if <tt>weight</tt> is <tt>NaN</tt>
//     */
//    public Edge(int v, int w, double weight) {
//        if (v < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
//        if (w < 0) throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
//        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
//        this.v = v;
//        this.w = w;
//        this.weight = weight;
//    }
//
//    /**
//     * Returns the weight of this edge.
//     *
//     * @return the weight of this edge
//     */
//    public double weight() {
//        return weight;
//    }
//
//    /**
//     * Returns either endpoint of this edge.
//     *
//     * @return either endpoint of this edge
//     */
//    public int either() {
//        return v;
//    }
//
//    /**
//     * Returns the endpoint of this edge that is different from the given vertex.
//     *
//     * @param  vertex one endpoint of this edge
//     * @return the other endpoint of this edge
//     * @throws IllegalArgumentException if the vertex is not one of the
//     *         endpoints of this edge
//     */
//    public int other(int vertex) {
//        if      (vertex == v) return w;
//        else if (vertex == w) return v;
//        else throw new IllegalArgumentException("Illegal endpoint");
//    }
//
//    /**
//     * Compares two edges by weight.
//     * Note that <tt>compareTo()</tt> is not consistent with <tt>equals()</tt>,
//     * which uses the reference equality implementation inherited from <tt>Object</tt>.
//     *
//     * @param  that the other edge
//     * @return a negative integer, zero, or positive integer depending on whether
//     *         the weight of this is less than, equal to, or greater than the
//     *         argument edge
//     */
//    @Override
//    public int compareTo(Edge that) {
//        if      (this.weight() < that.weight()) return -1;
//        else if (this.weight() > that.weight()) return +1;
//        else                                    return  0;
//    }
//
//    /**
//     * Returns a string representation of this edge.
//     *
//     * @return a string representation of this edge
//     */
//    public String toString() {
//        return String.format("%d-%d %.5f", v, w, weight);
//    }
//}
//
//
///******************************************************************************
// *  Compilation:  javac EdgeWeightedGraph.java
// *  Execution:    java EdgeWeightedGraph filename.txt
// *  Dependencies: Bag.java Edge.java In.java StdOut.java
// *  Data files:   http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
// *
// *  An edge-weighted undirected graph, implemented using adjacency lists.
// *  Parallel edges and self-loops are permitted.
// *
// *  % java EdgeWeightedGraph tinyEWG.txt
// *  8 16
// *  0: 6-0 0.58000  0-2 0.26000  0-4 0.38000  0-7 0.16000
// *  1: 1-3 0.29000  1-2 0.36000  1-7 0.19000  1-5 0.32000
// *  2: 6-2 0.40000  2-7 0.34000  1-2 0.36000  0-2 0.26000  2-3 0.17000
// *  3: 3-6 0.52000  1-3 0.29000  2-3 0.17000
// *  4: 6-4 0.93000  0-4 0.38000  4-7 0.37000  4-5 0.35000
// *  5: 1-5 0.32000  5-7 0.28000  4-5 0.35000
// *  6: 6-4 0.93000  6-0 0.58000  3-6 0.52000  6-2 0.40000
// *  7: 2-7 0.34000  1-7 0.19000  0-7 0.16000  5-7 0.28000  4-7 0.37000
// *
// ******************************************************************************/
//
///**
// *  The <tt>EdgeWeightedGraph</tt> class represents an edge-weighted
// *  graph of vertices named 0 through <em>V</em> - 1, where each
// *  undirected edge is of type {@link Edge} and has a real-valued weight.
// *  It supports the following two primary operations: add an edge to the graph,
// *  iterate over all of the edges incident to a vertex. It also provides
// *  methods for returning the number of vertices <em>V</em> and the number
// *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
// *  <p>
// *  This implementation uses an adjacency-lists representation, which
// *  is a vertex-indexed array of @link{Bag} objects.
// *  All operations take constant time (in the worst case) except
// *  iterating over the edges incident to a given vertex, which takes
// *  time proportional to the number of such edges.
// *  <p>
// *  For additional documentation,
// *  see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
// *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
//class EdgeWeightedGraph {
//    private static final String NEWLINE = System.getProperty("line.separator");
//
//    private final int V;
//    private int E;
//    private Bag<Edge>[] adj;
//
//    /**
//     * Initializes an empty edge-weighted graph with <tt>V</tt> vertices and 0 edges.
//     *
//     * @param  V the number of vertices
//     * @throws IllegalArgumentException if <tt>V</tt> < 0
//     */
//    public EdgeWeightedGraph(int V) {
//        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
//        this.V = V;
//        this.E = 0;
//        adj = (Bag<Edge>[]) new Bag[V];
//        for (int v = 0; v < V; v++) {
//            adj[v] = new Bag<Edge>();
//        }
//    }
//
////    /**
////     * Initializes a random edge-weighted graph with <tt>V</tt> vertices and <em>E</em> edges.
////     *
////     * @param  V the number of vertices
////     * @param  E the number of edges
////     * @throws IllegalArgumentException if <tt>V</tt> < 0
////     * @throws IllegalArgumentException if <tt>E</tt> < 0
////     */
////    public EdgeWeightedGraph(int V, int E) {
////        this(V);
////        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
////        for (int i = 0; i < E; i++) {
////            int v = StdRandom.uniform(V);
////            int w = StdRandom.uniform(V);
////            double weight = Math.round(100 * StdRandom.uniform()) / 100.0;
////            Edge e = new Edge(v, w, weight);
////            addEdge(e);
////        }
////    }
//
//    /**
//     * Initializes an edge-weighted graph from an input stream.
//     * The format is the number of vertices <em>V</em>,
//     * followed by the number of edges <em>E</em>,
//     * followed by <em>E</em> pairs of vertices and edge weights,
//     * with each entry separated by whitespace.
//     *
//     * @param  in the input stream
//     * @throws IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
//     * @throws IllegalArgumentException if the number of vertices or edges is negative
//     */
//    public EdgeWeightedGraph(In in) {
//        this(in.readInt());
//        int E = in.readInt();
//        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
//        for (int i = 0; i < E; i++) {
//            int v = in.readInt();
//            int w = in.readInt();
//            double weight = in.readDouble();
//            Edge e = new Edge(v, w, weight);
//            addEdge(e);
//        }
//    }
//
//    /**
//     * Initializes a new edge-weighted graph that is a deep copy of <tt>G</tt>.
//     *
//     * @param  G the edge-weighted graph to copy
//     */
//    public EdgeWeightedGraph(EdgeWeightedGraph G) {
//        this(G.V());
//        this.E = G.E();
//        for (int v = 0; v < G.V(); v++) {
//            // reverse so that adjacency list is in same order as original
//            Stack<Edge> reverse = new Stack<Edge>();
//            for (Edge e : G.adj[v]) {
//                reverse.push(e);
//            }
//            for (Edge e : reverse) {
//                adj[v].add(e);
//            }
//        }
//    }
//
//
//    /**
//     * Returns the number of vertices in this edge-weighted graph.
//     *
//     * @return the number of vertices in this edge-weighted graph
//     */
//    public int V() {
//        return V;
//    }
//
//    /**
//     * Returns the number of edges in this edge-weighted graph.
//     *
//     * @return the number of edges in this edge-weighted graph
//     */
//    public int E() {
//        return E;
//    }
//
//    // throw an IndexOutOfBoundsException unless 0 <= v < V
//    private void validateVertex(int v) {
//        if (v < 0 || v >= V)
//            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
//    }
//
//    /**
//     * Adds the undirected edge <tt>e</tt> to this edge-weighted graph.
//     *
//     * @param  e the edge
//     * @throws IndexOutOfBoundsException unless both endpoints are between 0 and V-1
//     */
//    public void addEdge(Edge e) {
//        int v = e.either();
//        int w = e.other(v);
//        validateVertex(v);
//        validateVertex(w);
//        adj[v].add(e);
//        adj[w].add(e);
//        E++;
//    }
//
//    /**
//     * Returns the edges incident on vertex <tt>v</tt>.
//     *
//     * @param  v the vertex
//     * @return the edges incident on vertex <tt>v</tt> as an Iterable
//     * @throws IndexOutOfBoundsException unless 0 <= v < V
//     */
//    public Iterable<Edge> adj(int v) {
//        validateVertex(v);
//        return adj[v];
//    }
//
//    /**
//     * Returns the degree of vertex <tt>v</tt>.
//     *
//     * @param  v the vertex
//     * @return the degree of vertex <tt>v</tt>
//     * @throws IndexOutOfBoundsException unless 0 <= v < V
//     */
//    public int degree(int v) {
//        validateVertex(v);
//        return adj[v].size();
//    }
//
//    /**
//     * Returns all edges in this edge-weighted graph.
//     * To iterate over the edges in this edge-weighted graph, use foreach notation:
//     * <tt>for (Edge e : G.edges())</tt>.
//     *
//     * @return all edges in this edge-weighted graph, as an iterable
//     */
//    public Iterable<Edge> edges() {
//        Bag<Edge> list = new Bag<Edge>();
//        for (int v = 0; v < V; v++) {
//            int selfLoops = 0;
//            for (Edge e : adj(v)) {
//                if (e.other(v) > v) {
//                    list.add(e);
//                }
//                // only add one copy of each self loop (self loops will be consecutive)
//                else if (e.other(v) == v) {
//                    if (selfLoops % 2 == 0) list.add(e);
//                    selfLoops++;
//                }
//            }
//        }
//        return list;
//    }
//
//    /**
//     * Returns a string representation of the edge-weighted graph.
//     * This method takes time proportional to <em>E</em> + <em>V</em>.
//     *
//     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
//     *         followed by the <em>V</em> adjacency lists of edges
//     */
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        s.append(V + " " + E + NEWLINE);
//        for (int v = 0; v < V; v++) {
//            s.append(v + ": ");
//            for (Edge e : adj[v]) {
//                s.append(e + "  ");
//            }
//            s.append(NEWLINE);
//        }
//        return s.toString();
//    }
//}
//
//
//
///******************************************************************************
// *  Compilation:  javac Bag.java
// *  Execution:    java Bag < input.txt
// *  Dependencies: StdIn.java StdOut.java
// *
// *  A generic bag or multiset, implemented using a singly-linked list.
// *
// *  % more tobe.txt
// *  to be or not to - be - - that - - - is
// *
// *  % java Bag < tobe.txt
// *  size of bag = 14
// *  is
// *  -
// *  -
// *  -
// *  that
// *  -
// *  -
// *  be
// *  -
// *  to
// *  not
// *  or
// *  be
// *  to
// *
// ******************************************************************************/
//
///**
// *  The <tt>Bag</tt> class represents a bag (or multiset) of
// *  generic items. It supports insertion and iterating over the
// *  items in arbitrary order.
// *  <p>
// *  This implementation uses a singly-linked list with a static nested class Node.
// *  textbook that uses a non-static nested class.
// *  The <em>add</em>, <em>isEmpty</em>, and <em>size</em> operations
// *  take constant time. Iteration takes time proportional to the number of items.
// *  <p>
// *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
// *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// *
// *  @param <Item> the generic type of an item in this bag
// */
//class Bag<Item> implements Iterable<Item> {
//    private Node<Item> first;    // beginning of bag
//    private int N;               // number of elements in bag
//
//    // helper linked list class
//    private static class Node<Item> {
//        private Item item;
//        private Node<Item> next;
//    }
//
//    /**
//     * Initializes an empty bag.
//     */
//    public Bag() {
//        first = null;
//        N = 0;
//    }
//
//    /**
//     * Returns true if this bag is empty.
//     *
//     * @return <tt>true</tt> if this bag is empty;
//     *         <tt>false</tt> otherwise
//     */
//    public boolean isEmpty() {
//        return first == null;
//    }
//
//    /**
//     * Returns the number of items in this bag.
//     *
//     * @return the number of items in this bag
//     */
//    public int size() {
//        return N;
//    }
//
//    /**
//     * Adds the item to this bag.
//     *
//     * @param  item the item to add to this bag
//     */
//    public void add(Item item) {
//        Node<Item> oldfirst = first;
//        first = new Node<Item>();
//        first.item = item;
//        first.next = oldfirst;
//        N++;
//    }
//
//
//    /**
//     * Returns an iterator that iterates over the items in this bag in arbitrary order.
//     *
//     * @return an iterator that iterates over the items in this bag in arbitrary order
//     */
//    public Iterator<Item> iterator()  {
//        return new ListIterator<Item>(first);
//    }
//
//    // an iterator, doesn't implement remove() since it's optional
//    private class ListIterator<Item> implements Iterator<Item> {
//        private Node<Item> current;
//
//        public ListIterator(Node<Item> first) {
//            current = first;
//        }
//
//        public boolean hasNext()  { return current != null;                     }
//        public void remove()      { throw new UnsupportedOperationException();  }
//
//        public Item next() {
//            if (!hasNext()) throw new NoSuchElementException();
//            Item item = current.item;
//            current = current.next;
//            return item;
//        }
//    }
//}
//
//
///******************************************************************************
// *  Compilation:  javac UF.java
// *  Execution:    java UF < input.txt
// *  Dependencies: StdIn.java StdOut.java
// *  Data files:   http://algs4.cs.princeton.edu/15uf/tinyUF.txt
// *                http://algs4.cs.princeton.edu/15uf/mediumUF.txt
// *                http://algs4.cs.princeton.edu/15uf/largeUF.txt
// *
// *  Weighted quick-union by rank with path compression by halving.
// *
// *  % java UF < tinyUF.txt
// *  4 3
// *  3 8
// *  6 5
// *  9 4
// *  2 1
// *  5 0
// *  7 2
// *  6 1
// *  2 components
// *
// ******************************************************************************/
//
//
///**
// *  The <tt>UF</tt> class represents a <em>union-find data type</em>
// *  (also known as the <em>disjoint-sets data type</em>).
// *  It supports the <em>union</em> and <em>find</em> operations,
// *  along with a <em>connected</em> operation for determining whether
// *  two sites are in the same component and a <em>count</em> operation that
// *  returns the total number of components.
// *  <p>
// *  The union-find data type models connectivity among a set of <em>N</em>
// *  sites, named 0 through <em>N</em> &ndash; 1.
// *  The <em>is-connected-to</em> relation must be an
// *  <em>equivalence relation</em>:
// *  <ul>
// *  <p><li> <em>Reflexive</em>: <em>p</em> is connected to <em>p</em>.
// *  <p><li> <em>Symmetric</em>: If <em>p</em> is connected to <em>q</em>,
// *          then <em>q</em> is connected to <em>p</em>.
// *  <p><li> <em>Transitive</em>: If <em>p</em> is connected to <em>q</em>
// *          and <em>q</em> is connected to <em>r</em>, then
// *          <em>p</em> is connected to <em>r</em>.
// *  </ul>
// *  <p>
// *  An equivalence relation partitions the sites into
// *  <em>equivalence classes</em> (or <em>components</em>). In this case,
// *  two sites are in the same component if and only if they are connected.
// *  Both sites and components are identified with integers between 0 and
// *  <em>N</em> &ndash; 1.
// *  Initially, there are <em>N</em> components, with each site in its
// *  own component.  The <em>component identifier</em> of a component
// *  (also known as the <em>root</em>, <em>canonical element</em>, <em>leader</em>,
// *  or <em>set representative</em>) is one of the sites in the component:
// *  two sites have the same component identifier if and only if they are
// *  in the same component.
// *  <ul>
// *  <p><li><em>union</em>(<em>p</em>, <em>q</em>) adds a
// *         connection between the two sites <em>p</em> and <em>q</em>.
// *         If <em>p</em> and <em>q</em> are in different components,
// *         then it replaces
// *         these two components with a new component that is the union of
// *         the two.
// *  <p><li><em>find</em>(<em>p</em>) returns the component
// *         identifier of the component containing <em>p</em>.
// *  <p><li><em>connected</em>(<em>p</em>, <em>q</em>)
// *         returns true if both <em>p</em> and <em>q</em>
// *         are in the same component, and false otherwise.
// *  <p><li><em>count</em>() returns the number of components.
// *  </ul>
// *  <p>
// *  The component identifier of a component can change
// *  only when the component itself changes during a call to
// *  <em>union</em>&mdash;it cannot change during a call
// *  to <em>find</em>, <em>connected</em>, or <em>count</em>.
// *  <p>
// *  This implementation uses weighted quick union by rank with path compression
// *  by halving.
// *  Initializing a data structure with <em>N</em> sites takes linear time.
// *  Afterwards, the <em>union</em>, <em>find</em>, and <em>connected</em>
// *  operations take logarithmic time (in the worst case) and the
// *  <em>count</em> operation takes constant time.
// *  Moreover, the amortized time per <em>union</em>, <em>find</em>,
// *  and <em>connected</em> operation has inverse Ackermann complexity.
// *  For alternate implementations of the same API, see
// *
// *  <p>
// *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
// *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
//
//class UF {
//
//    private int[] parent;  // parent[i] = parent of i
//    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
//    private int count;     // number of components
//
//    /**
//     * Initializes an empty union-find data structure with <tt>N</tt> sites
//     * <tt>0</tt> through <tt>N-1</tt>. Each site is initially in its own
//     * component.
//     *
//     * @param  N the number of sites
//     * @throws IllegalArgumentException if <tt>N &lt; 0</tt>
//     */
//    public UF(int N) {
//        if (N < 0) throw new IllegalArgumentException();
//        count = N;
//        parent = new int[N];
//        rank = new byte[N];
//        for (int i = 0; i < N; i++) {
//            parent[i] = i;
//            rank[i] = 0;
//        }
//    }
//
//    /**
//     * Returns the component identifier for the component containing site <tt>p</tt>.
//     *
//     * @param  p the integer representing one site
//     * @return the component identifier for the component containing site <tt>p</tt>
//     * @throws IndexOutOfBoundsException unless <tt>0 &le; p &lt; N</tt>
//     */
//    public int find(int p) {
//        validate(p);
//        while (p != parent[p]) {
//            parent[p] = parent[parent[p]];    // path compression by halving
//            p = parent[p];
//        }
//        return p;
//    }
//
//    /**
//     * Returns the number of components.
//     *
//     * @return the number of components (between <tt>1</tt> and <tt>N</tt>)
//     */
//    public int count() {
//        return count;
//    }
//
//    /**
//     * Returns true if the the two sites are in the same component.
//     *
//     * @param  p the integer representing one site
//     * @param  q the integer representing the other site
//     * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt> are in the same component;
//     *         <tt>false</tt> otherwise
//     * @throws IndexOutOfBoundsException unless
//     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
//     */
//    public boolean connected(int p, int q) {
//        return find(p) == find(q);
//    }
//
//    /**
//     * Merges the component containing site <tt>p</tt> with the
//     * the component containing site <tt>q</tt>.
//     *
//     * @param  p the integer representing one site
//     * @param  q the integer representing the other site
//     * @throws IndexOutOfBoundsException unless
//     *         both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
//     */
//    public void union(int p, int q) {
//        int rootP = find(p);
//        int rootQ = find(q);
//        if (rootP == rootQ) return;
//
//        // make root of smaller rank point to root of larger rank
//        if      (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
//        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
//        else {
//            parent[rootQ] = rootP;
//            rank[rootP]++;
//        }
//        count--;
//    }
//
//    // validate that p is a valid index
//    private void validate(int p) {
//        int N = parent.length;
//        if (p < 0 || p >= N) {
//            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (N-1));
//        }
//    }
//}
//
//
///**
// *  The <tt>Queue</tt> class represents a first-in-first-out (FIFO)
// *  queue of generic items.
// *  It supports the usual <em>enqueue</em> and <em>dequeue</em>
// *  operations, along with methods for peeking at the first item,
// *  testing if the queue is empty, and iterating through
// *  the items in FIFO order.
// *  <p>
// *  This implementation uses a singly-linked list with a static nested class for
// *  textbook that uses a non-static nested class.
// *  The <em>enqueue</em>, <em>dequeue</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
// *  operations all take constant time in the worst case.
// *  <p>
// *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
// *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// *
// *  @param <Item> the generic type of an item in this bag
// */
//class Queue<Item> implements Iterable<Item> {
//    private Node<Item> first;    // beginning of queue
//    private Node<Item> last;     // end of queue
//    private int N;               // number of elements on queue
//
//    // helper linked list class
//    private static class Node<Item> {
//        private Item item;
//        private Node<Item> next;
//    }
//
//    /**
//     * Initializes an empty queue.
//     */
//    public Queue() {
//        first = null;
//        last  = null;
//        N = 0;
//    }
//
//    /**
//     * Returns true if this queue is empty.
//     *
//     * @return <tt>true</tt> if this queue is empty; <tt>false</tt> otherwise
//     */
//    public boolean isEmpty() {
//        return first == null;
//    }
//
//    /**
//     * Returns the number of items in this queue.
//     *
//     * @return the number of items in this queue
//     */
//    public int size() {
//        return N;
//    }
//
//    /**
//     * Returns the item least recently added to this queue.
//     *
//     * @return the item least recently added to this queue
//     * @throws NoSuchElementException if this queue is empty
//     */
//    public Item peek() {
//        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
//        return first.item;
//    }
//
//    /**
//     * Adds the item to this queue.
//     *
//     * @param  item the item to add
//     */
//    public void enqueue(Item item) {
//        Node<Item> oldlast = last;
//        last = new Node<Item>();
//        last.item = item;
//        last.next = null;
//        if (isEmpty()) first = last;
//        else           oldlast.next = last;
//        N++;
//    }
//
//    /**
//     * Removes and returns the item on this queue that was least recently added.
//     *
//     * @return the item on this queue that was least recently added
//     * @throws NoSuchElementException if this queue is empty
//     */
//    public Item dequeue() {
//        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
//        Item item = first.item;
//        first = first.next;
//        N--;
//        if (isEmpty()) last = null;   // to avoid loitering
//        return item;
//    }
//
//    /**
//     * Returns a string representation of this queue.
//     *
//     * @return the sequence of items in FIFO order, separated by spaces
//     */
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        for (Item item : this)
//            s.append(item + " ");
//        return s.toString();
//    }
//
//    /**
//     * Returns an iterator that iterates over the items in this queue in FIFO order.
//     *
//     * @return an iterator that iterates over the items in this queue in FIFO order
//     */
//    public Iterator<Item> iterator()  {
//        return new ListIterator<Item>(first);
//    }
//
//    // an iterator, doesn't implement remove() since it's optional
//    private class ListIterator<Item> implements Iterator<Item> {
//        private Node<Item> current;
//
//        public ListIterator(Node<Item> first) {
//            current = first;
//        }
//
//        public boolean hasNext()  { return current != null;                     }
//        public void remove()      { throw new UnsupportedOperationException();  }
//
//        public Item next() {
//            if (!hasNext()) throw new NoSuchElementException();
//            Item item = current.item;
//            current = current.next;
//            return item;
//        }
//    }
//}
//
//
///******************************************************************************
// *  Compilation:  javac MinPQ.java
// *  Execution:    java MinPQ < input.txt
// *  Dependencies: StdIn.java StdOut.java
// *
// *  Generic min priority queue implementation with a binary heap.
// *  Can be used with a comparator instead of the natural order.
// *
// *  % java MinPQ < tinyPQ.txt
// *  E A E (6 left on pq)
// *
// *  We use a one-based array to simplify parent and child calculations.
// *
// *  Can be optimized by replacing full exchanges with half exchanges
// *  (ala insertion sort).
// *
// ******************************************************************************/
//
///**
// *  The <tt>MinPQ</tt> class represents a priority queue of generic keys.
// *  It supports the usual <em>insert</em> and <em>delete-the-minimum</em>
// *  operations, along with methods for peeking at the minimum key,
// *  testing if the priority queue is empty, and iterating through
// *  the keys.
// *  <p>
// *  This implementation uses a binary heap.
// *  The <em>insert</em> and <em>delete-the-minimum</em> operations take
// *  logarithmic amortized time.
// *  The <em>min</em>, <em>size</em>, and <em>is-empty</em> operations take constant time.
// *  Construction takes time proportional to the specified capacity or the number of
// *  items used to initialize the data structure.
// *  <p>
// *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
// *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// *
// *  @param <Key> the generic type of key on this priority queue
// */
//class MinPQ<Key> implements Iterable<Key> {
//    private Key[] pq;                    // store items at indices 1 to N
//    private int N;                       // number of items on priority queue
//    private Comparator<Key> comparator;  // optional comparator
//
//    /**
//     * Initializes an empty priority queue with the given initial capacity.
//     *
//     * @param  initCapacity the initial capacity of this priority queue
//     */
//    public MinPQ(int initCapacity) {
//        pq = (Key[]) new Object[initCapacity + 1];
//        N = 0;
//    }
//
//    /**
//     * Initializes an empty priority queue.
//     */
//    public MinPQ() {
//        this(1);
//    }
//
//    /**
//     * Initializes an empty priority queue with the given initial capacity,
//     * using the given comparator.
//     *
//     * @param  initCapacity the initial capacity of this priority queue
//     * @param  comparator the order to use when comparing keys
//     */
//    public MinPQ(int initCapacity, Comparator<Key> comparator) {
//        this.comparator = comparator;
//        pq = (Key[]) new Object[initCapacity + 1];
//        N = 0;
//    }
//
//    /**
//     * Initializes an empty priority queue using the given comparator.
//     *
//     * @param  comparator the order to use when comparing keys
//     */
//    public MinPQ(Comparator<Key> comparator) {
//        this(1, comparator);
//    }
//
//    /**
//     * Initializes a priority queue from the array of keys.
//     * <p>
//     * Takes time proportional to the number of keys, using sink-based heap construction.
//     *
//     * @param  keys the array of keys
//     */
//    public MinPQ(Key[] keys) {
//        N = keys.length;
//        pq = (Key[]) new Object[keys.length + 1];
//        for (int i = 0; i < N; i++)
//            pq[i+1] = keys[i];
//        for (int k = N/2; k >= 1; k--)
//            sink(k);
//        assert isMinHeap();
//    }
//
//    /**
//     * Returns true if this priority queue is empty.
//     *
//     * @return <tt>true</tt> if this priority queue is empty;
//     *         <tt>false</tt> otherwise
//     */
//    public boolean isEmpty() {
//        return N == 0;
//    }
//
//    /**
//     * Returns the number of keys on this priority queue.
//     *
//     * @return the number of keys on this priority queue
//     */
//    public int size() {
//        return N;
//    }
//
//    /**
//     * Returns a smallest key on this priority queue.
//     *
//     * @return a smallest key on this priority queue
//     * @throws NoSuchElementException if this priority queue is empty
//     */
//    public Key min() {
//        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
//        return pq[1];
//    }
//
//    // helper function to double the size of the heap array
//    private void resize(int capacity) {
//        assert capacity > N;
//        Key[] temp = (Key[]) new Object[capacity];
//        for (int i = 1; i <= N; i++) {
//            temp[i] = pq[i];
//        }
//        pq = temp;
//    }
//
//    /**
//     * Adds a new key to this priority queue.
//     *
//     * @param  x the key to add to this priority queue
//     */
//    public void insert(Key x) {
//        // double size of array if necessary
//        if (N == pq.length - 1) resize(2 * pq.length);
//
//        // add x, and percolate it up to maintain heap invariant
//        pq[++N] = x;
//        swim(N);
//        assert isMinHeap();
//    }
//
//    /**
//     * Removes and returns a smallest key on this priority queue.
//     *
//     * @return a smallest key on this priority queue
//     * @throws NoSuchElementException if this priority queue is empty
//     */
//    public Key delMin() {
//        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
//        exch(1, N);
//        Key min = pq[N--];
//        sink(1);
//        pq[N+1] = null;         // avoid loitering and help with garbage collection
//        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length  / 2);
//        assert isMinHeap();
//        return min;
//    }
//
//
//    /***************************************************************************
//     * Helper functions to restore the heap invariant.
//     ***************************************************************************/
//
//    private void swim(int k) {
//        while (k > 1 && greater(k/2, k)) {
//            exch(k, k/2);
//            k = k/2;
//        }
//    }
//
//    private void sink(int k) {
//        while (2*k <= N) {
//            int j = 2*k;
//            if (j < N && greater(j, j+1)) j++;
//            if (!greater(k, j)) break;
//            exch(k, j);
//            k = j;
//        }
//    }
//
//    /***************************************************************************
//     * Helper functions for compares and swaps.
//     ***************************************************************************/
//    private boolean greater(int i, int j) {
//        if (comparator == null) {
//            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
//        }
//        else {
//            return comparator.compare(pq[i], pq[j]) > 0;
//        }
//    }
//
//    private void exch(int i, int j) {
//        Key swap = pq[i];
//        pq[i] = pq[j];
//        pq[j] = swap;
//    }
//
//    // is pq[1..N] a min heap?
//    private boolean isMinHeap() {
//        return isMinHeap(1);
//    }
//
//    // is subtree of pq[1..N] rooted at k a min heap?
//    private boolean isMinHeap(int k) {
//        if (k > N) return true;
//        int left = 2*k, right = 2*k + 1;
//        if (left  <= N && greater(k, left))  return false;
//        if (right <= N && greater(k, right)) return false;
//        return isMinHeap(left) && isMinHeap(right);
//    }
//
//
//    /**
//     * Returns an iterator that iterates over the keys on this priority queue
//     * in ascending order.
//     * <p>
//     * The iterator doesn't implement <tt>remove()</tt> since it's optional.
//     *
//     * @return an iterator that iterates over the keys in ascending order
//     */
//    public Iterator<Key> iterator() { return new HeapIterator(); }
//
//    private class HeapIterator implements Iterator<Key> {
//        // create a new pq
//        private MinPQ<Key> copy;
//
//        // add all items to copy of heap
//        // takes linear time since already in heap order so no keys move
//        public HeapIterator() {
//            if (comparator == null) copy = new MinPQ<Key>(size());
//            else                    copy = new MinPQ<Key>(size(), comparator);
//            for (int i = 1; i <= N; i++)
//                copy.insert(pq[i]);
//        }
//
//        public boolean hasNext()  { return !copy.isEmpty();                     }
//        public void remove()      { throw new UnsupportedOperationException();  }
//
//        public Key next() {
//            if (!hasNext()) throw new NoSuchElementException();
//            return copy.delMin();
//        }
//    }
//}
//
//
//
///******************************************************************************
// *  Compilation:  javac ST.java
// *  Execution:    java ST
// *  Dependencies: StdIn.java StdOut.java
// *
// *  Sorted symbol table implementation using a java.util.TreeMap.
// *  Does not allow duplicates.
// *
// *  % java ST
// *
// ******************************************************************************/
//
///**
// *  The <tt>ST</tt> class represents an ordered symbol table of generic
// *  key-value pairs.
// *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
// *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
// *  It also provides ordered methods for finding the <em>minimum</em>,
// *  <em>maximum</em>, <em>floor</em>, and <em>ceiling</em>.
// *  It also provides a <em>keys</em> method for iterating over all of the keys.
// *  A symbol table implements the <em>associative array</em> abstraction:
// *  when associating a value with a key that is already in the symbol table,
// *  the convention is to replace the old value with the new value.
// *  Unlike {@link java.util.Map}, this class uses the convention that
// *  values cannot be <tt>null</tt>&mdash;setting the
// *  value associated with a key to <tt>null</tt> is equivalent to deleting the key
// *  from the symbol table.
// *  <p>
// *  This implementation uses a balanced binary search tree. It requires that
// *  the key type implements the <tt>Comparable</tt> interface and calls the
// *  <tt>compareTo()</tt> and method to compare two keys. It does not call either
// *  <tt>equals()</tt> or <tt>hashCode()</tt>.
// *  The <em>put</em>, <em>contains</em>, <em>remove</em>, <em>minimum</em>,
// *  <em>maximum</em>, <em>ceiling</em>, and <em>floor</em> operations each take
// *  logarithmic time in the worst case.
// *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
// *  Construction takes constant time.
// *  <p>
// *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/35applications">Section 3.5</a> of
// *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// *
// *  @param <Key> the generic type of keys in this symbol table
// *  @param <Value> the generic type of values in this symbol table
// */
//class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {
//
//    private TreeMap<Key, Value> st;
//
//    /**
//     * Initializes an empty symbol table.
//     */
//    public ST() {
//        st = new TreeMap<Key, Value>();
//    }
//
//
//    /**
//     * Returns the value associated with the given key in this symbol table.
//     *
//     * @param  key the key
//     * @return the value associated with the given key if the key is in this symbol table;
//     *         <tt>null</tt> if the key is not in this symbol table
//     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
//     */
//    public Value get(Key key) {
//        if (key == null) throw new NullPointerException("called get() with null key");
//        return st.get(key);
//    }
//
//    /**
//     * Inserts the specified key-value pair into the symbol table, overwriting the old
//     * value with the new value if the symbol table already contains the specified key.
//     * Deletes the specified key (and its associated value) from this symbol table
//     * if the specified value is <tt>null</tt>.
//     *
//     * @param  key the key
//     * @param  val the value
//     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
//     */
//    public void put(Key key, Value val) {
//        if (key == null) throw new NullPointerException("called put() with null key");
//        if (val == null) st.remove(key);
//        else             st.put(key, val);
//    }
//
//    /**
//     * Removes the specified key and its associated value from this symbol table
//     * (if the key is in this symbol table).
//     *
//     * @param  key the key
//     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
//     */
//    public void delete(Key key) {
//        if (key == null) throw new NullPointerException("called delete() with null key");
//        st.remove(key);
//    }
//
//    /**
//     * Returns true if this symbol table contain the given key.
//     *
//     * @param  key the key
//     * @return <tt>true</tt> if this symbol table contains <tt>key</tt> and
//     *         <tt>false</tt> otherwise
//     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
//     */
//    public boolean contains(Key key) {
//        if (key == null) throw new NullPointerException("called contains() with null key");
//        return st.containsKey(key);
//    }
//
//    /**
//     * Returns the number of key-value pairs in this symbol table.
//     *
//     * @return the number of key-value pairs in this symbol table
//     */
//    public int size() {
//        return st.size();
//    }
//
//    /**
//     * Returns true if this symbol table is empty.
//     *
//     * @return <tt>true</tt> if this symbol table is empty and <tt>false</tt> otherwise
//     */
//    public boolean isEmpty() {
//        return size() == 0;
//    }
//
//    /**
//     * Returns all keys in this symbol table.
//     * <p>
//     * To iterate over all of the keys in the symbol table named <tt>st</tt>,
//     * use the foreach notation: <tt>for (Key key : st.keys())</tt>.
//     *
//     * @return all keys in this symbol table
//     */
//    public Iterable<Key> keys() {
//        return st.keySet();
//    }
//
//    /**
//     * Returns all of the keys in this symbol table.
//     * To iterate over all of the keys in a symbol table named <tt>st</tt>, use the
//     * foreach notation: <tt>for (Key key : st)</tt>.
//     * <p>
//     * This method is provided for backward compatibility with the version from
//     * <em>Introduction to Programming in Java: An Interdisciplinary Approach.</em>
//     *
//     * @return     an iterator to all of the keys in this symbol table
//     * @deprecated Replaced by {@link #keys()}.
//     */
//    public Iterator<Key> iterator() {
//        return st.keySet().iterator();
//    }
//
//    /**
//     * Returns the smallest key in this symbol table.
//     *
//     * @return the smallest key in this symbol table
//     * @throws NoSuchElementException if this symbol table is empty
//     */
//    public Key min() {
//        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
//        return st.firstKey();
//    }
//
//    /**
//     * Returns the largest key in this symbol table.
//     *
//     * @return the largest key in this symbol table
//     * @throws NoSuchElementException if this symbol table is empty
//     */
//    public Key max() {
//        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
//        return st.lastKey();
//    }
//
//    /**
//     * Returns the smallest key in this symbol table greater than or equal to <tt>key</tt>.
//     *
//     * @param  key the key
//     * @return the smallest key in this symbol table greater than or equal to <tt>key</tt>
//     * @throws NoSuchElementException if there is no such key
//     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
//     */
//    public Key ceiling(Key key) {
//        if (key == null) throw new NullPointerException("called ceiling() with null key");
//        Key k = st.ceilingKey(key);
//        if (k == null) throw new NoSuchElementException("all keys are less than " + key);
//        return k;
//    }
//
//    /**
//     * Returns the largest key in this symbol table less than or equal to <tt>key</tt>.
//     *
//     * @param  key the key
//     * @return the largest key in this symbol table less than or equal to <tt>key</tt>
//     * @throws NoSuchElementException if there is no such key
//     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
//     */
//    public Key floor(Key key) {
//        if (key == null) throw new NullPointerException("called floor() with null key");
//        Key k = st.floorKey(key);
//        if (k == null) throw new NoSuchElementException("all keys are greater than " + key);
//        return k;
//    }
//}
//
//
///******************************************************************************
// *  Compilation:  javac StdIn.java
// *  Execution:    java StdIn   (interactive test of basic functionality)
// *  Dependencies: none
// *
// *  Reads in data of various types from standard input.
// *
// ******************************************************************************/
//
///**
// *  The <tt>StdIn</tt> class provides static methods for reading strings
// *  and numbers from standard input.
// *  These functions fall into one of four categories:
// *  <p>
// *  <ul>
// *  <li>those for reading individual tokens from standard input, one at a time,
// *      and converting each to a number, string, or boolean
// *  <li>those for reading characters from standard input, one at a time
// *  <li>those for reading lines from standard input, one at a time
// *  <li>those for reading a sequence of values of the same type from standard input,
// *      and returning the values in an array
// *  </ul>
// *  <p>
// *  Generally, it is best not to mix functions from the different
// *  categories in the same program.
// *  <p>
// *  <b>Reading tokens from standard input one at a time,
// *  and converting to numbers and strings.</b>
// *  You can use the following methods to read numbers, strings, and booleans
// *  from standard input:
// *  <ul>
// *  <li> {@link #readInt()}
// *  <li> {@link #readDouble()}
// *  <li> {@link #readString()}
// *  <li> {@link #readBoolean()}
// *  <li> {@link #readShort()}
// *  <li> {@link #readLong()}
// *  <li> {@link #readFloat()}
// *  <li> {@link #readByte()}
// *  </ul>
// *  <p>
// *  Each method skips over any input that is whitespace. Then, it reads
// *  the next token and attempts to convert it into a value of the specified
// *  type. If it succeeds, it returns that value; otherwise, it
// *  throws a {@link InputMismatchException}.
// *  <p>
// *  <em>Whitespace</em> includes spaces, tabs, and newlines; the full definition
// *  is inherited from {@link Character#isWhitespace(char)}.
// *  A <em>token</em> is a maximal sequence of non-whitespace characters.
// *  The precise rules for describing which tokens can be converted to
// *  integers and floating-point numbers are inherited from
// *  <a href = "http://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html#number-syntax">Scanner</a>,
// *  using the locale {@link Locale#US}; the rules
// *  for floating-point numbers are slightly different
// *  from those in {@link Double#valueOf(String)},
// *  but unlikely to be of concern to most programmers.
// *  <p>
// *  <b>Reading characters from standard input, one at a time.</b>
// *  You can use the following two methods to read characters from standard input:
// *  <ul>
// *  <li> {@link #hasNextChar()}
// *  <li> {@link #readChar()}
// *  </ul>
// *  <p>
// *  The first method returns true if standard input has more input (including whitespace).
// *  The second method reads and returns the next character of input on standard
// *  input (possibly a whitespace character).
// *  <p>
// *  As an example, the following code fragment reads characters from standard input,
// *  one character at a time, and prints it to standard output.
// *  <pre>
// *  while (!StdIn.hasNextChar()) {
// *      char c = StdIn.readChar();
// *      StdOut.print(c);
// *  }
// *  </pre>
// *  <p>
// *  <b>Reading lines from standard input, one at a time.</b>
// *  You can use the following two methods to read lines from standard input:
// *  <ul>
// *  <li> {@link #hasNextLine()}
// *  <li> {@link #readLine()}
// *  </ul>
// *  <p>
// *  The first method returns true if standard input has more input (including whitespace).
// *  The second method reads and returns the remaining portion of
// *  the next line of input on standard input (possibly whitespace),
// *  discarding the trailing line separator.
// *  <p>
// *  A <em>line separator</em> is defined to be one of the following strings:
// *  {@code \n} (Linux), {@code \r} (old Macintosh),
// *  {@code \r\n} (Windows),
// *  <code>&#92;u2028</code>, <code>&#92;u2029</code>, or <code>&#92;u0085</code>.
// *  <p>
// *  As an example, the following code fragment reads text from standard input,
// *  one line at a time, and prints it to standard output.
// *  <pre>
// *  while (!StdIn.hasNextLine()) {
// *      String line = StdIn.readLine();
// *      StdOut.println(line);
// *  }
// *  </pre>
// *  <p>
// *  <b>Reading a sequence of values of the same type from standard input.</b>
// *  You can use the following methods to read a sequence numbers, strings,
// *  or booleans (all of the same type) from standard input:
// *  <ul>
// *  <li> {@link #readAllDoubles()}
// *  <li> {@link #readAllInts()}
// *  <li> {@link #readAllStrings()}
// *  <li> {@link #readAllLines()}
// *  <li> {@link #readAll()}
// *  </ul>
// *  <p>
// *  The first three methods read of all of remaining token on standard input
// *  and dconverts the tokens to values of
// *  the specified type, as in the corresponding
// *  {@code readDouble}, {@code readInt}, and {@code readString()} methods.
// *  The {@code readAllLines()} method reads all remaining lines on standard
// *  input and returns them as an array of strings.
// *  The {@code readAll()} method reads all remaining input on standard
// *  input and returns it as a string.
// *  <p>
// *  As an example, the following code fragment reads all of the remaining
// *  tokens from standard input and returns them as an array of strings.
// *  <pre>
// *  String[] words = StdIn.readAllString();
// *  </pre>
// *  <p>
// *  <b>Differences with Scanner.</b>
// *  {@code StdIn} and {@link Scanner} are both designed to parse
// *  tokens and convert them to primitive types and strings.
// *  Some of the main differences are summarized below:
// *  <ul>
// *  <li> {@code StdIn} is a set of static methods and reads
// *       reads input from only standard input. It is suitable for use before
// *       a programmer knows about objects.
// *       See {@link In} for an object-oriented version that handles
// *       input from files, URLs,
// *       and sockets.
// *  <li> {@code StdIn} uses whitespace as the delimiter between tokens.
// *  <li> {@code StdIn} coerces the character-set encoding to UTF-8,
// *       which is a standard character encoding for Unicode.
// *  <li> {@code StdIn} coerces the locale to {@link Locale#US},
// *       for consistency with {@link StdOut}, {@link Double#parseDouble(String)},
// *       and floating-point literals.
// *  <li> {@code StdIn} has convenient methods for reading a single
// *       character; reading in sequences of integers, doubles, or strings;
// *       and reading in all of the remaining input.
// *  </ul>
// *  <p>
// *  Historical note: {@code StdIn} preceded {@code Scanner}; when
// *  {@code Scanner} was introduced, this class was reimplemented to use it.
// *  <p>
// *  <b>Using standard input.</b>
// *  Standard input is fundamental operating system abstraction, on Mac OS X,
// *  Windows, and Linux.
// *  The methods in {@code StdIn} are <em>blocking</em>, which means that they
// *  will wait until you enter input on standard input.
// *  If your program has a loop that repeats until standard input is empty,
// *  you must signal that the input is finished.
// *  To do so, depending on your operating system and IDE,
// *  use either {@code <Ctrl-d>} or {@code <Ctrl-z>}, on its own line.
// *  If you are redirecting standard input from a file, you will not need
// *  to do anything to signal that the input is finished.
// *  <p>
// *  <b>Known bugs.</b>
// *  Java's UTF-8 encoding does not recognize the optional
// *  <a href = "http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4508058">byte-order mask</a>.
// *  If the input begins with the optional byte-order mask, <tt>StdIn</tt>
// *  will have an extra character <code>&#92;uFEFF</code> at the beginning.
// *  <p>
// *  <b>Reference.</b>
// *  For additional documentation,
// *  see <a href="http://introcs.cs.princeton.edu/15inout">Section 1.5</a> of
// *  <em>Introduction to Programming in Java: An Interdisciplinary Approach</em>
// *  by Robert Sedgewick and Kevin Wayne.
// *
// *  @author David Pritchard
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
//final class StdIn {
//
//    /*** begin: section (1 of 2) of code duplicated from In to StdIn. */
//
//    // assume Unicode UTF-8 encoding
//    private static final String CHARSET_NAME = "UTF-8";
//
//    // assume language = English, country = US for consistency with System.out.
//    private static final Locale LOCALE = Locale.US;
//
//    // the default token separator; we maintain the invariant that this value
//    // is held by the scanner's delimiter between calls
//    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
//
//    // makes whitespace significant
//    private static final Pattern EMPTY_PATTERN = Pattern.compile("");
//
//    // used to read the entire input
//    private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");
//
//    /*** end: section (1 of 2) of code duplicated from In to StdIn. */
//
//    private static Scanner scanner;
//
//    // it doesn't make sense to instantiate this class
//    private StdIn() { }
//
//    //// begin: section (2 of 2) of code duplicated from In to StdIn,
//    //// with all methods changed from "public" to "public static"
//
//    /**
//     * Returns true if standard input is empty (except possibly for whitespace).
//     * Use this method to know whether the next call to {@link #readString()},
//     * {@link #readDouble()}, etc will succeed.
//     *
//     * @return <tt>true</tt> if standard input is empty (except possibly
//     *         for whitespace); <tt>false</tt> otherwise
//     */
//    public static boolean isEmpty() {
//        return !scanner.hasNext();
//    }
//
//    /**
//     * Returns true if standard input has a next line.
//     * Use this method to know whether the
//     * next call to {@link #readLine()} will succeed.
//     * This method is functionally equivalent to {@link #hasNextChar()}.
//     *
//     * @return <tt>true</tt> if standard input is empty;
//     *         <tt>false</tt> otherwise
//     */
//    public static boolean hasNextLine() {
//        return scanner.hasNextLine();
//    }
//
//    /**
//     * Returns true if standard input has more inputy (including whitespace).
//     * Use this method to know whether the next call to {@link #readChar()} will succeed.
//     * This method is functionally equivalent to {@link #hasNextLine()}.
//     *
//     * @return <tt>true</tt> if standard input has more input (including whitespace);
//     *         <tt>false</tt> otherwise
//     */
//    public static boolean hasNextChar() {
//        scanner.useDelimiter(EMPTY_PATTERN);
//        boolean result = scanner.hasNext();
//        scanner.useDelimiter(WHITESPACE_PATTERN);
//        return result;
//    }
//
//
//    /**
//     * Reads and returns the next line, excluding the line separator if present.
//     * @return the next line, excluding the line separator if present
//     */
//    public static String readLine() {
//        String line;
//        try {
//            line = scanner.nextLine();
//        }
//        catch (NoSuchElementException e) {
//            line = null;
//        }
//        return line;
//    }
//
//    /**
//     * Reads and returns the next character.
//     * @return the next character
//     */
//    public static char readChar() {
//        scanner.useDelimiter(EMPTY_PATTERN);
//        String ch = scanner.next();
//        assert ch.length() == 1 : "Internal (Std)In.readChar() error!"
//                                  + " Please contact the authors.";
//        scanner.useDelimiter(WHITESPACE_PATTERN);
//        return ch.charAt(0);
//    }
//
//
//    /**
//     * Reads and returns the remainder of the input, as a string.
//     * @return the remainder of the input, as a string
//     */
//    public static String readAll() {
//        if (!scanner.hasNextLine())
//            return "";
//
//        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
//        // not that important to reset delimeter, since now scanner is empty
//        scanner.useDelimiter(WHITESPACE_PATTERN); // but let's do it anyway
//        return result;
//    }
//
//
//    /**
//     * Reads the next token  and returns the <tt>String</tt>.
//     * @return the next <tt>String</tt>
//     */
//    public static String readString() {
//        return scanner.next();
//    }
//
//    /**
//     * Reads the next token from standard input, parses it as an integer, and returns the integer.
//     * @return the next integer on standard input
//     * @throws InputMismatchException if the next token cannot be parsed as an <tt>int</tt>
//     */
//    public static int readInt() {
//        return scanner.nextInt();
//    }
//
//    /**
//     * Reads the next token from standard input, parses it as a double, and returns the double.
//     * @return the next double on standard input
//     * @throws InputMismatchException if the next token cannot be parsed as a <tt>double</tt>
//     */
//    public static double readDouble() {
//        return scanner.nextDouble();
//    }
//
//    /**
//     * Reads the next token from standard input, parses it as a float, and returns the float.
//     * @return the next float on standard input
//     * @throws InputMismatchException if the next token cannot be parsed as a <tt>float</tt>
//     */
//    public static float readFloat() {
//        return scanner.nextFloat();
//    }
//
//    /**
//     * Reads the next token from standard input, parses it as a long integer, and returns the long integer.
//     * @return the next long integer on standard input
//     * @throws InputMismatchException if the next token cannot be parsed as a <tt>long</tt>
//     */
//    public static long readLong() {
//        return scanner.nextLong();
//    }
//
//    /**
//     * Reads the next token from standard input, parses it as a short integer, and returns the short integer.
//     * @return the next short integer on standard input
//     * @throws InputMismatchException if the next token cannot be parsed as a <tt>short</tt>
//     */
//    public static short readShort() {
//        return scanner.nextShort();
//    }
//
//    /**
//     * Reads the next token from standard input, parses it as a byte, and returns the byte.
//     * @return the next byte on standard input
//     * @throws InputMismatchException if the next token cannot be parsed as a <tt>byte</tt>
//     */
//    public static byte readByte() {
//        return scanner.nextByte();
//    }
//
//    /**
//     * Reads the next token from standard input, parses it as a boolean,
//     * and returns the boolean.
//     * @return the next boolean on standard input
//     * @throws InputMismatchException if the next token cannot be parsed as a <tt>boolean</tt>:
//     *    <tt>true</tt> or <tt>1</tt> for true, and <tt>false</tt> or <tt>0</tt> for false,
//     *    ignoring case
//     */
//    public static boolean readBoolean() {
//        String s = readString();
//        if (s.equalsIgnoreCase("true"))  return true;
//        if (s.equalsIgnoreCase("false")) return false;
//        if (s.equals("1"))               return true;
//        if (s.equals("0"))               return false;
//        throw new InputMismatchException();
//    }
//
//    /**
//     * Reads all remaining tokens from standard input and returns them as an array of strings.
//     * @return all remaining tokens on standard input, as an array of strings
//     */
//    public static String[] readAllStrings() {
//        // we could use readAll.trim().split(), but that's not consistent
//        // because trim() uses characters 0x00..0x20 as whitespace
//        String[] tokens = WHITESPACE_PATTERN.split(readAll());
//        if (tokens.length == 0 || tokens[0].length() > 0)
//            return tokens;
//
//        // don't include first token if it is leading whitespace
//        String[] decapitokens = new String[tokens.length-1];
//        for (int i = 0; i < tokens.length - 1; i++)
//            decapitokens[i] = tokens[i+1];
//        return decapitokens;
//    }
//
//    /**
//     * Reads all remaining lines from standard input and returns them as an array of strings.
//     * @return all remaining lines on standard input, as an array of strings
//     */
//    public static String[] readAllLines() {
//        ArrayList<String> lines = new ArrayList<String>();
//        while (hasNextLine()) {
//            lines.add(readLine());
//        }
//        return lines.toArray(new String[0]);
//    }
//
//    /**
//     * Reads all remaining tokens from standard input, parses them as integers, and returns
//     * them as an array of integers.
//     * @return all remaining integers on standard input, as an array
//     * @throws InputMismatchException if any token cannot be parsed as an <tt>int</tt>
//     */
//    public static int[] readAllInts() {
//        String[] fields = readAllStrings();
//        int[] vals = new int[fields.length];
//        for (int i = 0; i < fields.length; i++)
//            vals[i] = Integer.parseInt(fields[i]);
//        return vals;
//    }
//
//    /**
//     * Reads all remaining tokens from standard input, parses them as doubles, and returns
//     * them as an array of doubles.
//     * @return all remaining doubles on standard input, as an array
//     * @throws InputMismatchException if any token cannot be parsed as a <tt>double</tt>
//     */
//    public static double[] readAllDoubles() {
//        String[] fields = readAllStrings();
//        double[] vals = new double[fields.length];
//        for (int i = 0; i < fields.length; i++)
//            vals[i] = Double.parseDouble(fields[i]);
//        return vals;
//    }
//
//    //// end: section (2 of 2) of code duplicated from In to StdIn
//
//
//    // do this once when StdIn is initialized
//    static {
//        resync();
//    }
//
//    /**
//     * If StdIn changes, use this to reinitialize the scanner.
//     */
//    private static void resync() {
//        setScanner(new Scanner(new java.io.BufferedInputStream(System.in), CHARSET_NAME));
//    }
//
//    private static void setScanner(Scanner scanner) {
//        StdIn.scanner = scanner;
//        StdIn.scanner.useLocale(LOCALE);
//    }
//
//    /**
//     * Reads all remaining tokens, parses them as integers, and returns
//     * them as an array of integers.
//     * @return all remaining integers, as an array
//     * @throws InputMismatchException if any token cannot be parsed as an <tt>int</tt>
//     * @deprecated Replaced by {@link #readAllInts()}.
//     */
//    public static int[] readInts() {
//        return readAllInts();
//    }
//
//    /**
//     * Reads all remaining tokens, parses them as doubles, and returns
//     * them as an array of doubles.
//     * @return all remaining doubles, as an array
//     * @throws InputMismatchException if any token cannot be parsed as a <tt>double</tt>
//     * @deprecated Replaced by {@link #readAllDoubles()}.
//     */
//    public static double[] readDoubles() {
//        return readAllDoubles();
//    }
//
//    /**
//     * Reads all remaining tokens and returns them as an array of strings.
//     * @return all remaining tokens, as an array of strings
//     * @deprecated Replaced by {@link #readAllStrings()}.
//     */
//    public static String[] readStrings() {
//        return readAllStrings();
//    }
//}
//
//
///**
// *  This class provides methods for printing strings and numbers to standard output.
// *  <p>
// *  <b>Getting started.</b>
// *  To use this class, you must have <tt>StdOut.class</tt> in your
// *  Java classpath. If you used our autoinstaller, you should be all set.
// *  Otherwise, download
// *  <a href = "http://introcs.cs.princeton.edu/java/stdlib/StdOut.java">StdOut.java</a>
// *  and put a copy in your working directory.
// *  <p>
// *  Here is an example program that uses <code>StdOut</code>:
// *  <pre>
// *   public class TestStdOut {
// *       public static void main(String[] args) {
// *           int a = 17;
// *           int b = 23;
// *           int sum = a + b;
// *           StdOut.println("Hello, World");
// *           StdOut.printf("%d + %d = %d\n", a, b, sum);
// *       }
// *   }
// *  </pre>
// *  <p>
// *  <b>Differences with System.out.</b>
// *  The behavior of <code>StdOut</code> is similar to that of {@link System#out},
// *  but there are a few subtle differences:
// *  <ul>
// *  <li> <code>StdOut</code> coerces the character-set encoding to UTF-8,
// *       which is a standard character encoding for Unicode.
// *  <li> <code>StdOut</code> coerces the locale to {@link Locale#US},
// *       for consistency with {@link StdIn}, {@link Double#parseDouble(String)},
// *       and floating-point literals.
// *  <li> <code>StdOut</code> <em>flushes</em> standard output after each call to
// *       <code>print()</code> so that text will appear immediately in the terminal.
// *  </ul>
// *  <p>
// *  <b>Reference.</b>
// *  For additional documentation,
// *  see <a href="http://introcs.cs.princeton.edu/15inout">Section 1.5</a> of
// *  <em>Introduction to Programming in Java: An Interdisciplinary Approach</em>
// *  by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
//final class StdOut {
//
//    // force Unicode UTF-8 encoding; otherwise it's system dependent
//    private static final String CHARSET_NAME = "UTF-8";
//
//    // assume language = English, country = US for consistency with StdIn
//    private static final Locale LOCALE = Locale.US;
//
//    // send output here
//    private static PrintWriter out;
//
//    // this is called before invoking any methods
//    static {
//        try {
//            out = new PrintWriter(new OutputStreamWriter(System.out, CHARSET_NAME), true);
//        }
//        catch (UnsupportedEncodingException e) {
//            System.out.println(e);
//        }
//    }
//
//    // don't instantiate
//    private StdOut() { }
//
//    /**
//     * Closes standard output.
//     */
//    public static void close() {
//        out.close();
//    }
//
//    /**
//     * Terminates the current line by printing the line-separator string.
//     */
//    public static void println() {
//        out.println();
//    }
//
//    /**
//     * Prints an object to this output stream and then terminates the line.
//     *
//     * @param x the object to print
//     */
//    public static void println(Object x) {
//        out.println(x);
//    }
//
//    /**
//     * Prints a boolean to standard output and then terminates the line.
//     *
//     * @param x the boolean to print
//     */
//    public static void println(boolean x) {
//        out.println(x);
//    }
//
//    /**
//     * Prints a character to standard output and then terminates the line.
//     *
//     * @param x the character to print
//     */
//    public static void println(char x) {
//        out.println(x);
//    }
//
//    /**
//     * Prints a double to standard output and then terminates the line.
//     *
//     * @param x the double to print
//     */
//    public static void println(double x) {
//        out.println(x);
//    }
//
//    /**
//     * Prints an integer to standard output and then terminates the line.
//     *
//     * @param x the integer to print
//     */
//    public static void println(float x) {
//        out.println(x);
//    }
//
//    /**
//     * Prints an integer to standard output and then terminates the line.
//     *
//     * @param x the integer to print
//     */
//    public static void println(int x) {
//        out.println(x);
//    }
//
//    /**
//     * Prints a long to standard output and then terminates the line.
//     *
//     * @param x the long to print
//     */
//    public static void println(long x) {
//        out.println(x);
//    }
//
//    /**
//     * Prints a short integer to standard output and then terminates the line.
//     *
//     * @param x the short to print
//     */
//    public static void println(short x) {
//        out.println(x);
//    }
//
//    /**
//     * Prints a byte to standard output and then terminates the line.
//     * <p>
//     *
//     * @param x the byte to print
//     */
//    public static void println(byte x) {
//        out.println(x);
//    }
//
//    /**
//     * Flushes standard output.
//     */
//    public static void print() {
//        out.flush();
//    }
//
//    /**
//     * Prints an object to standard output and flushes standard output.
//     *
//     * @param x the object to print
//     */
//    public static void print(Object x) {
//        out.print(x);
//        out.flush();
//    }
//
//    /**
//     * Prints a boolean to standard output and flushes standard output.
//     *
//     * @param x the boolean to print
//     */
//    public static void print(boolean x) {
//        out.print(x);
//        out.flush();
//    }
//
//    /**
//     * Prints a character to standard output and flushes standard output.
//     *
//     * @param x the character to print
//     */
//    public static void print(char x) {
//        out.print(x);
//        out.flush();
//    }
//
//    /**
//     * Prints a double to standard output and flushes standard output.
//     *
//     * @param x the double to print
//     */
//    public static void print(double x) {
//        out.print(x);
//        out.flush();
//    }
//
//    /**
//     * Prints a float to standard output and flushes standard output.
//     *
//     * @param x the float to print
//     */
//    public static void print(float x) {
//        out.print(x);
//        out.flush();
//    }
//
//    /**
//     * Prints an integer to standard output and flushes standard output.
//     *
//     * @param x the integer to print
//     */
//    public static void print(int x) {
//        out.print(x);
//        out.flush();
//    }
//
//    /**
//     * Prints a long integer to standard output and flushes standard output.
//     *
//     * @param x the long integer to print
//     */
//    public static void print(long x) {
//        out.print(x);
//        out.flush();
//    }
//
//    /**
//     * Prints a short integer to standard output and flushes standard output.
//     *
//     * @param x the short integer to print
//     */
//    public static void print(short x) {
//        out.print(x);
//        out.flush();
//    }
//
//    /**
//     * Prints a byte to standard output and flushes standard output.
//     *
//     * @param x the byte to print
//     */
//    public static void print(byte x) {
//        out.print(x);
//        out.flush();
//    }
//
//    /**
//     * Prints a formatted string to standard output, using the specified format
//     * string and arguments, and then flushes standard output.
//     *
//     *
//     * @param format the <a href = "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax">format string</a>
//     * @param args   the arguments accompanying the format string
//     */
//    public static void printf(String format, Object... args) {
//        out.printf(LOCALE, format, args);
//        out.flush();
//    }
//
//    /**
//     * Prints a formatted string to standard output, using the locale and
//     * the specified format string and arguments; then flushes standard output.
//     *
//     * @param locale the locale
//     * @param format the <a href = "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax">format string</a>
//     * @param args   the arguments accompanying the format string
//     */
//    public static void printf(Locale locale, String format, Object... args) {
//        out.printf(locale, format, args);
//        out.flush();
//    }
//}
//
///**
// *  <i>Input</i>. This class provides methods for reading strings
// *  and numbers from standard input, file input, URLs, and sockets.
// *  <p>
// *  The Locale used is: language = English, country = US. This is consistent
// *  with the formatting conventions with Java floating-point literals,
// *  command-line arguments (via {@link Double#parseDouble(String)})
// *  and standard output.
// *  <p>
// *  For additional documentation, see
// *  <a href="http://introcs.cs.princeton.edu/31datatype">Section 3.1</a> of
// *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i>
// *  by Robert Sedgewick and Kevin Wayne.
// *  <p>
// *  Like {@link Scanner}, reading a token also consumes preceding Java
// *  whitespace, reading a full line consumes
// *  the following end-of-line delimeter, while reading a character consumes
// *  nothing extra.
// *  <p>
// *  Whitespace is defined in {@link Character#isWhitespace(char)}. Newlines
// *  consist of \n, \r, \r\n, and Unicode hex code points 0x2028, 0x2029, 0x0085;
// *  see <tt><a href="http://www.docjar.com/html/api/java/util/Scanner.java.html">
// *  Scanner.java</a></tt> (NB: Java 6u23 and earlier uses only \r, \r, \r\n).
// *
// *  @author David Pritchard
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
//final class In {
//
//    ///// begin: section (1 of 2) of code duplicated from In to StdIn.
//
//    // assume Unicode UTF-8 encoding
//    private static final String CHARSET_NAME = "UTF-8";
//
//    // assume language = English, country = US for consistency with System.out.
//    private static final Locale LOCALE = Locale.US;
//
//    // the default token separator; we maintain the invariant that this value
//    // is held by the scanner's delimiter between calls
//    private static final Pattern WHITESPACE_PATTERN
//        = Pattern.compile("\\p{javaWhitespace}+");
//
//    // makes whitespace characters significant
//    private static final Pattern EMPTY_PATTERN
//        = Pattern.compile("");
//
//    // used to read the entire input. source:
//    // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
//    private static final Pattern EVERYTHING_PATTERN
//        = Pattern.compile("\\A");
//
//    //// end: section (1 of 2) of code duplicated from In to StdIn.
//
//    private Scanner scanner;
//
//    /**
//     * Initializes an input stream from standard input.
//     */
//    public In() {
//        scanner = new Scanner(new BufferedInputStream(System.in), CHARSET_NAME);
//        scanner.useLocale(LOCALE);
//    }
//
//    /**
//     * Initializes an input stream from a socket.
//     *
//     * @param  socket the socket
//     * @throws IllegalArgumentException if cannot open {@code socket}
//     * @throws NullPointerException if {@code socket} is {@code null}
//     */
//    public In(Socket socket) {
//        if (socket == null) throw new NullPointerException("argument is null");
//        try {
//            InputStream is = socket.getInputStream();
//            scanner = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
//            scanner.useLocale(LOCALE);
//        }
//        catch (IOException ioe) {
//            throw new IllegalArgumentException("Could not open " + socket);
//        }
//    }
//
////    /**
////     * Initializes an input stream from a URL.
////     *
////     * @param  url the URL
////     * @throws IllegalArgumentException if cannot open {@code url}
////     * @throws NullPointerException if {@code url} is {@code null}
////     */
////    public In(URL url) {
////        if (url == null) throw new NullPointerException("argument is null");
////        try {
////            URLConnection site = url.openConnection();
////            InputStream is     = site.getInputStream();
////            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
////            scanner.useLocale(LOCALE);
////        }
////        catch (IOException ioe) {
////            throw new IllegalArgumentException("Could not open " + url);
////        }
////    }
//
//    /**
//     * Initializes an input stream from a file.
//     *
//     * @param  file the file
//     * @throws IllegalArgumentException if cannot open {@code file}
//     * @throws NullPointerException if {@code file} is {@code null}
//     */
//    public In(File file) {
//        if (file == null) throw new NullPointerException("argument is null");
//        try {
//            scanner = new Scanner(file, CHARSET_NAME);
//            scanner.useLocale(LOCALE);
//        }
//        catch (IOException ioe) {
//            throw new IllegalArgumentException("Could not open " + file);
//        }
//    }
//
//
//    /**
//     * Initializes an input stream from a filename or web page name.
//     *
//     * @param  name the filename or web page name
//     * @throws IllegalArgumentException if cannot open {@code name} as
//     *         a file or URL
//     * @throws NullPointerException if {@code name} is {@code null}
//     */
//    public In(String name) {
//        if (name == null) throw new NullPointerException("argument is null");
//        try {
//            // first try to read file from local file system
//            File file = new File(name);
//            if (file.exists()) {
//                scanner = new Scanner(file, CHARSET_NAME);
//                scanner.useLocale(LOCALE);
//                return;
//            }
//
//            // next try for files included in jar
//            URL url = getClass().getResource(name);
//
//            // or URL from web
//            if (url == null) {
//                url = new URL(name);
//            }
//
//            URLConnection site = url.openConnection();
//
//            // in order to set User-Agent, replace above line with these two
//            // HttpURLConnection site = (HttpURLConnection) url.openConnection();
//            // site.addRequestProperty("User-Agent", "Mozilla/4.76");
//
//            InputStream is     = site.getInputStream();
//            scanner            = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
//            scanner.useLocale(LOCALE);
//        }
//        catch (IOException ioe) {
//            throw new IllegalArgumentException("Could not open " + name);
//        }
//    }
//
//    /**
//     * Initializes an input stream from a given {@link Scanner} source; use with
//     * <tt>new Scanner(String)</tt> to read from a string.
//     * <p>
//     * Note that this does not create a defensive copy, so the
//     * scanner will be mutated as you read on.
//     *
//     * @param  scanner the scanner
//     * @throws NullPointerException if {@code scanner} is {@code null}
//     */
//    public In(Scanner scanner) {
//        if (scanner == null) throw new NullPointerException("argument is null");
//        this.scanner = scanner;
//    }
//
//    /**
//     * Returns true if this input stream exists.
//     *
//     * @return <tt>true</tt> if this input stream exists; <tt>false</tt> otherwise
//     */
//    public boolean exists()  {
//        return scanner != null;
//    }
//
//    ////  begin: section (2 of 2) of code duplicated from In to StdIn,
//    ////  with all methods changed from "public" to "public static".
//
//    /**
//     * Returns true if input stream is empty (except possibly whitespace).
//     * Use this to know whether the next call to {@link #readString()},
//     * {@link #readDouble()}, etc will succeed.
//     *
//     * @return <tt>true</tt> if this input stream is empty (except possibly whitespace);
//     *         <tt>false</tt> otherwise
//     */
//    public boolean isEmpty() {
//        return !scanner.hasNext();
//    }
//
//    /**
//     * Returns true if this input stream has a next line.
//     * Use this method to know whether the
//     * next call to {@link #readLine()} will succeed.
//     * This method is functionally equivalent to {@link #hasNextChar()}.
//     *
//     * @return <tt>true</tt> if this input stream is empty;
//     *         <tt>false</tt> otherwise
//     */
//    public boolean hasNextLine() {
//        return scanner.hasNextLine();
//    }
//
//    /**
//     * Returns true if this input stream has more inputy (including whitespace).
//     * Use this method to know whether the next call to {@link #readChar()} will succeed.
//     * This method is functionally equivalent to {@link #hasNextLine()}.
//     *
//     * @return <tt>true</tt> if this input stream has more input (including whitespace);
//     *         <tt>false</tt> otherwise
//     */
//    public boolean hasNextChar() {
//        scanner.useDelimiter(EMPTY_PATTERN);
//        boolean result = scanner.hasNext();
//        scanner.useDelimiter(WHITESPACE_PATTERN);
//        return result;
//    }
//
//
//    /**
//     * Reads and returns the next line in this input stream.
//     *
//     * @return the next line in this input stream; <tt>null</tt> if no such line
//     */
//    public String readLine() {
//        String line;
//        try {
//            line = scanner.nextLine();
//        }
//        catch (NoSuchElementException e) {
//            line = null;
//        }
//        return line;
//    }
//
//    /**
//     * Reads and returns the next character in this input stream.
//     *
//     * @return the next character in this input stream
//     */
//    public char readChar() {
//        scanner.useDelimiter(EMPTY_PATTERN);
//        String ch = scanner.next();
//        assert ch.length() == 1 : "Internal (Std)In.readChar() error!"
//                                  + " Please contact the authors.";
//        scanner.useDelimiter(WHITESPACE_PATTERN);
//        return ch.charAt(0);
//    }
//
//
//    /**
//     * Reads and returns the remainder of this input stream, as a string.
//     *
//     * @return the remainder of this input stream, as a string
//     */
//    public String readAll() {
//        if (!scanner.hasNextLine())
//            return "";
//
//        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
//        // not that important to reset delimeter, since now scanner is empty
//        scanner.useDelimiter(WHITESPACE_PATTERN); // but let's do it anyway
//        return result;
//    }
//
//
//    /**
//     * Reads the next token from this input stream and returns it as a <tt>String</tt>.
//     *
//     * @return the next <tt>String</tt> in this input stream
//     */
//    public String readString() {
//        return scanner.next();
//    }
//
//    /**
//     * Reads the next token from this input stream, parses it as a <tt>int</tt>,
//     * and returns the <tt>int</tt>.
//     *
//     * @return the next <tt>int</tt> in this input stream
//     */
//    public int readInt() {
//        return scanner.nextInt();
//    }
//
//    /**
//     * Reads the next token from this input stream, parses it as a <tt>double</tt>,
//     * and returns the <tt>double</tt>.
//     *
//     * @return the next <tt>double</tt> in this input stream
//     */
//    public double readDouble() {
//        return scanner.nextDouble();
//    }
//
//    /**
//     * Reads the next token from this input stream, parses it as a <tt>float</tt>,
//     * and returns the <tt>float</tt>.
//     *
//     * @return the next <tt>float</tt> in this input stream
//     */
//    public float readFloat() {
//        return scanner.nextFloat();
//    }
//
//    /**
//     * Reads the next token from this input stream, parses it as a <tt>long</tt>,
//     * and returns the <tt>long</tt>.
//     *
//     * @return the next <tt>long</tt> in this input stream
//     */
//    public long readLong() {
//        return scanner.nextLong();
//    }
//
//    /**
//     * Reads the next token from this input stream, parses it as a <tt>short</tt>,
//     * and returns the <tt>short</tt>.
//     *
//     * @return the next <tt>short</tt> in this input stream
//     */
//    public short readShort() {
//        return scanner.nextShort();
//    }
//
//    /**
//     * Reads the next token from this input stream, parses it as a <tt>byte</tt>,
//     * and returns the <tt>byte</tt>.
//     * <p>
//     *
//     * @return the next <tt>byte</tt> in this input stream
//     */
//    public byte readByte() {
//        return scanner.nextByte();
//    }
//
//    /**
//     * Reads the next token from this input stream, parses it as a <tt>boolean</tt>
//     * (interpreting either <tt>"true"</tt> or <tt>"1"</tt> as <tt>true</tt>,
//     * and either <tt>"false"</tt> or <tt>"0"</tt> as <tt>false</tt>).
//     *
//     * @return the next <tt>boolean</tt> in this input stream
//     */
//    public boolean readBoolean() {
//        String s = readString();
//        if (s.equalsIgnoreCase("true"))  return true;
//        if (s.equalsIgnoreCase("false")) return false;
//        if (s.equals("1"))               return true;
//        if (s.equals("0"))               return false;
//        throw new InputMismatchException();
//    }
//
//    /**
//     * Reads all remaining tokens from this input stream and returns them as
//     * an array of strings.
//     *
//     * @return all remaining tokens in this input stream, as an array of strings
//     */
//    public String[] readAllStrings() {
//        // we could use readAll.trim().split(), but that's not consistent
//        // since trim() uses characters 0x00..0x20 as whitespace
//        String[] tokens = WHITESPACE_PATTERN.split(readAll());
//        if (tokens.length == 0 || tokens[0].length() > 0)
//            return tokens;
//        String[] decapitokens = new String[tokens.length-1];
//        for (int i = 0; i < tokens.length-1; i++)
//            decapitokens[i] = tokens[i+1];
//        return decapitokens;
//    }
//
//    /**
//     * Reads all remaining lines from this input stream and returns them as
//     * an array of strings.
//     *
//     * @return all remaining lines in this input stream, as an array of strings
//     */
//    public String[] readAllLines() {
//        ArrayList<String> lines = new ArrayList<String>();
//        while (hasNextLine()) {
//            lines.add(readLine());
//        }
//        return lines.toArray(new String[0]);
//    }
//
//
//    /**
//     * Reads all remaining tokens from this input stream, parses them as integers,
//     * and returns them as an array of integers.
//     *
//     * @return all remaining lines in this input stream, as an array of integers
//     */
//    public int[] readAllInts() {
//        String[] fields = readAllStrings();
//        int[] vals = new int[fields.length];
//        for (int i = 0; i < fields.length; i++)
//            vals[i] = Integer.parseInt(fields[i]);
//        return vals;
//    }
//
//    /**
//     * Reads all remaining tokens from this input stream, parses them as doubles,
//     * and returns them as an array of doubles.
//     *
//     * @return all remaining lines in this input stream, as an array of doubles
//     */
//    public double[] readAllDoubles() {
//        String[] fields = readAllStrings();
//        double[] vals = new double[fields.length];
//        for (int i = 0; i < fields.length; i++)
//            vals[i] = Double.parseDouble(fields[i]);
//        return vals;
//    }
//
//    ///// end: section (2 of 2) of code duplicated from In to StdIn */
//
//    /**
//     * Closes this input stream.
//     */
//    public void close() {
//        scanner.close();
//    }
//
//    /**
//     * Reads all integers from a file and returns them as
//     * an array of integers.
//     *
//     * @param      filename the name of the file
//     * @return     the integers in the file
//     * @deprecated Replaced by <tt>new In(filename)</tt>.{@link #readAllInts()}.
//     */
//    public static int[] readInts(String filename) {
//        return new In(filename).readAllInts();
//    }
//
//    /**
//     * Reads all doubles from a file and returns them as
//     * an array of doubles.
//     *
//     * @param      filename the name of the file
//     * @return     the doubles in the file
//     * @deprecated Replaced by <tt>new In(filename)</tt>.{@link #readAllDoubles()}.
//     */
//    public static double[] readDoubles(String filename) {
//        return new In(filename).readAllDoubles();
//    }
//
//    /**
//     * Reads all strings from a file and returns them as
//     * an array of strings.
//     *
//     * @param      filename the name of the file
//     * @return     the strings in the file
//     * @deprecated Replaced by <tt>new In(filename)</tt>.{@link #readAllStrings()}.
//     */
//    public static String[] readStrings(String filename) {
//        return new In(filename).readAllStrings();
//    }
//
//    /**
//     * Reads all integers from standard input and returns them
//     * an array of integers.
//     *
//     * @return     the integers on standard input
//     * @deprecated Replaced by {@link StdIn#readAllInts()}.
//     */
//    public static int[] readInts() {
//        return new In().readAllInts();
//    }
//
//    /**
//     * Reads all doubles from standard input and returns them as
//     * an array of doubles.
//     *
//     * @return     the doubles on standard input
//     * @deprecated Replaced by {@link StdIn#readAllDoubles()}.
//     */
//    public static double[] readDoubles() {
//        return new In().readAllDoubles();
//    }
//
//    /**
//     * Reads all strings from standard input and returns them as
//     *  an array of strings.
//     *
//     * @return     the strings on standard input
//     * @deprecated Replaced by {@link StdIn#readAllStrings()}.
//     */
//    public static String[] readStrings() {
//        return new In().readAllStrings();
//    }
//}
//
//
///**
// *  The <tt>CC</tt> class represents a data type for
// *  determining the connected components in an undirected graph.
// *  The <em>id</em> operation determines in which connected component
// *  a given vertex lies; the <em>connected</em> operation
// *  determines whether two vertices are in the same connected component;
// *  the <em>count</em> operation determines the number of connected
// *  components; and the <em>size</em> operation determines the number
// *  of vertices in the connect component containing a given vertex.
//
// *  The <em>component identifier</em> of a connected component is one of the
// *  vertices in the connected component: two vertices have the same component
// *  identifier if and only if they are in the same connected component.
//
// *  <p>
// *  This implementation uses depth-first search.
// *  The constructor takes time proportional to <em>V</em> + <em>E</em>
// *  (in the worst case),
// *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
// *  Afterwards, the <em>id</em>, <em>count</em>, <em>connected</em>,
// *  and <em>size</em> operations take constant time.
// *  <p>
// *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
// *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
// *
// *  @author Robert Sedgewick
// *  @author Kevin Wayne
// */
//class CC {
//    private boolean[] marked;   // marked[v] = has vertex v been marked?
//    private int[] id;           // id[v] = id of connected component containing v
//    private int[] size;         // size[id] = number of vertices in given component
//    private int count;          // number of connected components
//
//    /**
//     * Computes the connected components of the undirected graph <tt>G</tt>.
//     *
//     * @param G the undirected graph
//     */
//    public CC(EdgeWeightedGraph G) {
//        marked = new boolean[G.V()];
//        id = new int[G.V()];
//        size = new int[G.V()];
//        for (int v = 0; v < G.V(); v++) {
//            if (!marked[v]) {
//                dfs(G, v);
//                count++;
//            }
//        }
//    }
//
//    // depth-first search
//    private void dfs(EdgeWeightedGraph G, int v) {
//        marked[v] = true;
//        id[v] = count;
//        size[count]++;
//        for (Edge w : G.adj(v)) {
//            if (!marked[w.other(v)]) {
//                dfs(G, w.other(v));
//            }
//        }
//    }
//
//    /**
//     * Returns the component id of the connected component containing vertex <tt>v</tt>.
//     *
//     * @param  v the vertex
//     * @return the component id of the connected component containing vertex <tt>v</tt>
//     */
//    public int id(int v) {
//        return id[v];
//    }
//
//    /**
//     * Returns the number of vertices in the connected component containing vertex <tt>v</tt>.
//     *
//     * @param  v the vertex
//     * @return the number of vertices in the connected component containing vertex <tt>v</tt>
//     */
//    public int size(int v) {
//        return size[id[v]];
//    }
//
//    /**
//     * Returns the number of connected components in the graph <tt>G</tt>.
//     *
//     * @return the number of connected components in the graph <tt>G</tt>
//     */
//    public int count() {
//        return count;
//    }
//
//    /**
//     * Returns true if vertices <tt>v</tt> and <tt>w</tt> are in the same
//     * connected component.
//     *
//     * @param  v one vertex
//     * @param  w the other vertex
//     * @return <tt>true</tt> if vertices <tt>v</tt> and <tt>w</tt> are in the same
//     *         connected component; <tt>false</tt> otherwise
//     */
//    public boolean connected(int v, int w) {
//        return id(v) == id(w);
//    }
//
//    /**
//     * Returns true if vertices <tt>v</tt> and <tt>w</tt> are in the same
//     * connected component.
//     *
//     * @param  v one vertex
//     * @param  w the other vertex
//     * @return <tt>true</tt> if vertices <tt>v</tt> and <tt>w</tt> are in the same
//     *         connected component; <tt>false</tt> otherwise
//     * @deprecated Replaced by {@link #connected(int, int)}.
//     */
//    public boolean areConnected(int v, int w) {
//        return id(v) == id(w);
//    }
//}