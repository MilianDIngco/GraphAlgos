import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Milian Ingco
 * @version 1.0 ish
 * @since 8/26
 * 
 *        Description:
 * 
 *        Constructors:
 *        - Graph(int graphType, T baseValue, NamingFunction<T> namingFunction)
 * 
 *        Member Methods:
 *        - public void addNode(Point p)
 *        - public void addNode(int x, int y)
 *        - public void addNode(Point p, T val)
 *        - public void addNode(int x, int y, T val)
 *        - public Node<T> removeNode(Node<T> node)
 *        - public Node<T> getNode(Point p)
 *        - public Node<T> getNode(int x, int y)
 *        - public Node<T> getNeighbors(Node<T> node)
 *        - public boolean addEdge(Node<T> nodeOne, Node<T> nodeTwo)
 *        - public boolean removeEdge(Node<T> nodeOne, Node<T> nodeTwo)
 *        - public void setType(int graphType)
 * 
 *        root head tail etc
 * 
 *        Notes:
 *        - Weighted graphs ~can~ use adjacency matrix, however it won't be able
 *        to be used to retrieve the weight of a specific edge.
 *        - AddEdge() unweighted can be heavily improved upon
 * 
 *        Thoughts:
 *        - its not really worth creating a spatial partitioning system
 *        considering the most you'll have to loop over to find some nodes is
 *        trivial
 *        - why aren't there optional parameters D:
 */
public class Graph<T> {

    public enum Type {
        DEFAULT(0),
        DIRECTED(1),
        WEIGHTED(2),
        MULTI(4);

        final int val;

        Type(int val) {
            this.val = val;
        }
    }

    private final int graphType;
    private AdjMat<T> am;
    private AdjList<T> al;
    private NamingFunction<T> naming_f;
    private T baseValue;

    private ArrayList<String> logs;

    public Graph(int graphType, T baseValue, NamingFunction<T> naming_f) {
        // graph type contradiction handling?

        this.graphType = graphType;
        this.baseValue = baseValue;
        this.naming_f = naming_f;
        am = new AdjMat<>();
        al = new AdjList<>();
        logs = new ArrayList<>() {
            {
                String str = 1 + ": Created Graph with base value: " + baseValue + " and type: " + graphType;
                add(str);
            }
        };

    }

    /**
     * Adds node to the graph at a point and custom value
     * 
     * @param p   Point node is at on the screen
     * @param val Value the node displays
     */
    public void addNode(Point p, T val) {
        Node<T> temp = new Node(val, p);
        if (am.addNode(temp)) {
            logs.add(logs.size() + ": Added node to Adjacency Matrix: " + temp);
        } else {
            logs.add(logs.size() + ": Failed to add node to Adjacency Matrix: " + temp);
        }
        if (al.addNode(temp)) {
            logs.add(logs.size() + ": Added node to Adjacency List: " + temp);
        } else {
            logs.add(logs.size() + ": Failed to add node to Adjacency List: " + temp);
        }
    }

    /**
     * Adds node to the graph at a point and custom value
     * 
     * @param x   X value of the node
     * @param y   Y value of the node
     * @param val Value the node displays
     */
    public void addNode(int x, int y, T val) {
        addNode(new Point(x, y), val);
    }

    /**
     * Adds node to the graph at a point with a calculated value
     * 
     * @param p Point node is at on the screen
     */
    public void addNode(Point p) {
        this.baseValue = naming_f.apply(this.baseValue);
        addNode(p, this.baseValue);
    }

    /**
     * Adds node to the graph at a point with a calculated value
     * 
     * @param x X value of the node
     * @param y Y value of the node
     */
    public void addNode(int x, int y) {
        this.baseValue = naming_f.apply(this.baseValue);
        addNode(new Point(x, y), this.baseValue);
    }

    /**
     * Removes node from graph
     * 
     * @param node Node to be removed from the graph
     * @return Returns the node removed from the graph, returns null if failed to
     *         remove from either adjacency matrix or list, or if different nodes
     *         were removed
     */
    public Node<T> removeNode(Node<T> node) {
        Node<T> amRemove = am.removeNode(node);
        Node<T> alRemove = al.removeNode(node);
        if (amRemove == null) {
            logs.add(logs.size() + ": !!FAILED!! to remove node: " + node + " from adjacency matrix");
            return null;
        } else {
            logs.add(logs.size() + ":  REMOVED node: " + node + " from adjacency matrix");
        }
        if (alRemove == null) {
            logs.add(logs.size() + ": !!FAILED!! to remove node: " + node + " from adjacency list");
            return null;
        } else {
            logs.add(logs.size() + ":  REMOVED node: " + node + " from adjacency list");
        }

        if (alRemove.equals(amRemove)) {
            logs.add(logs.size() + ": REMOVED node: " + node + " from both successfully");
            return alRemove;
        } else {
            logs.add(logs.size()
                    + ": !!FAILED!! Different nodes were removed. AdjacencyMatrix Node: " + amRemove
                    + "| AdjacencyList Node: " + alRemove + " (or... they didnt refer to the same object idk)");
            return null;
        }
    }

    /**
     * Gets the closest node to a given point
     * 
     * @param p Point you want to search from
     * @return Returns the closest node to the given point, or null if the graph is
     *         empty
     */
    public Node<T> getNode(Point p) {
        double minDistance = Double.MAX_VALUE;
        Node<T> closestNode = null;
        ArrayList<Node<T>> nodes = am.getAllNodes();
        if (nodes.size() < 1) {
            logs.add(logs.size() + ": Graph has no nodes and cannot return any nodes");
            return null;
        }
        for (Node<T> node : nodes) {
            double distance = node.getPos().distanceSq(p);
            if (distance < minDistance) {
                minDistance = distance;
                closestNode = node;
            }
        }
        logs.add(logs.size() + ": Returned closest node to point: (" + p.getX() + ", " + p.getY() + "). Node returned: "
                + closestNode);
        return closestNode;
    }

    /**
     * Gets the closest node to a given point
     * 
     * @param x The x value of where you want to search from
     * @param y The y value of where you want to search from
     * @return Returns the closest node to the given point, or null if the graph is
     *         empty
     */
    public Node<T> getNode(int x, int y) {
        return getNode(new Point(x, y));
    }

    /**
     * Gets all nodes connected to a given node
     * 
     * @param node The source node you want to find with connected nodes
     * @return Returns List<Node<T>> of nodes with a connection to the given node,
     *         or null if the given node doesn't exist or has no edges
     */
    public List<Node<T>> getNeighbors(Node<T> node) {
        logs.add(logs.size() + ": Returned list of neighbor nodes of node: " + node);
        return al.getNeighbors(node);
    }

    /**
     * Adds an unweighted edge between two nodes. Automatically handles other graph
     * type cases
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @return Returns true if the edge was successfully added, false if either node
     *         doesn't exist, creation contradicts graph type, or failed to be added
     */
    public boolean addEdge(Node<T> nodeOne, Node<T> nodeTwo) {
        // Check if wrong addEdge() function was called
        if ((graphType & 2) > 0) {
            logs.add(logs.size() + ": !!FAILED!! to add edge because wrong addEdge() function was called on Node1: "
                    + nodeOne + " and Node2: " + nodeTwo);
            return false;
        }

        // if graphType isn't nonmulti, skips this check
        // Cannot create multiple edges from one to two
        // Don't need to check two to one because if it's undirected & two to one
        // exists, so does one to two
        if ((graphType & 4) == 0 && am.edgeExists(nodeOne, nodeTwo) > 0) {
            logs.add(logs.size()
                    + ": !!FAILED!! to add edge because an edge already exists and graph type is nonmulti. Node1: "
                    + nodeOne + ", Node2: " + nodeTwo);
            return false;
        }

        // Add edge in one direction regardless of type, no worries about weighted
        if (!am.addEdge(nodeOne, nodeTwo)) {
            logs.add(logs.size() + ": !!FAILED!! to add edge to adjacency matrix with node1: " + nodeOne + " node2: "
                    + nodeTwo);
            return false;
        }

        if (!al.addEdge(nodeOne, nodeTwo)) {
            logs.add(logs.size() + ": !!FAILED!! to add edge to adjacency list with node1: " + nodeOne + " node2: "
                    + nodeTwo);
            return false;
        }

        // if undirected, add the other direction
        if ((graphType & 1) == 0) {
            if (!am.addEdge(nodeTwo, nodeOne)) {
                logs.add(
                        logs.size() + ": !!FAILED!! to add edge to adjacency matrix with node1: " + nodeTwo + " node2: "
                                + nodeOne + " UNDIRECTED EDGE ADD");
                return false;
            }
            if (!al.addEdge(nodeTwo, nodeOne)) {
                logs.add(
                        logs.size() + ": !!FAILED!! to add edge to adjacency list with node1: " + nodeTwo + " node2: "
                                + nodeOne + " UNDIRECTED EDGE ADD");
                return false;
            }
        }

        logs.add(logs.size() + ": Added edge with nodes node1: " + nodeOne + " node2: " + nodeTwo);

        return true;
    }

    /**
     * Adds a weighted edge between two nodes. Automatically handles other graph
     * type cases
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @param weight  The weight of the edge
     * @return Returns true if the edge was successfully added, false if either node
     *         doesn't exist, creation contradicts graph types, or failed to be
     *         added
     */
    public boolean addEdge(Node<T> nodeOne, Node<T> nodeTwo, double weight) {
        // Check if wrong addEdge() function was called
        if ((graphType & 2) == 0) {
            logs.add(logs.size() + ": !!FAILED!! to add edge because wrong addEdge() function was called on Node1: "
                    + nodeOne + " and Node2: " + nodeTwo);
            return false;
        }

        // if graphType isn't nonmulti, skips this check
        // Cannot create multiple edges from one to two
        // Don't need to check two to one because if it's undirected & two to one
        // exists, so does one to two
        if ((graphType & 4) == 0 && am.edgeExists(nodeOne, nodeTwo) > 0) {
            logs.add(logs.size()
                    + ": !!FAILED!! to add edge because an edge already exists and graph type is nonmulti. Node1: "
                    + nodeOne + ", Node2: " + nodeTwo);
            return false;
        }

        // Add edge in one direction regardless of type, no worries about weighted since
        // matrix doesn't hold weighted info
        if (!am.addEdge(nodeOne, nodeTwo)) {
            logs.add(logs.size() + ": !!FAILED!! to add edge to adjacency matrix with node1: " + nodeOne + " node2: "
                    + nodeTwo);
            return false;
        }

        if (!al.addEdge(nodeOne, nodeTwo, weight)) {
            logs.add(logs.size() + ": !!FAILED!! to add edge to adjacency list with node1: " + nodeOne + " node2: "
                    + nodeTwo);
            return false;
        }

        // if undirected, add the other direction
        if ((graphType & 1) == 0) {
            if (!am.addEdge(nodeTwo, nodeOne)) {
                logs.add(
                        logs.size() + ": !!FAILED!! to add edge to adjacency matrix with node1: " + nodeTwo + " node2: "
                                + nodeOne + " UNDIRECTED EDGE ADD");
                return false;
            }
            if (!al.addEdge(nodeTwo, nodeOne, weight)) {
                logs.add(
                        logs.size() + ": !!FAILED!! to add edge to adjacency list with node1: " + nodeTwo + " node2: "
                                + nodeOne + " UNDIRECTED EDGE ADD");
                return false;
            }
        }

        logs.add(logs.size() + ": Added edge with nodes node1: " + nodeOne + " node2: " + nodeTwo + " with weight: "
                + weight);
        return true;
    }

    public boolean removeEdge(Node<T> nodeOne, Node<T> nodeTwo) {
        return false;
    }

}

// public class Graph<T> {

// /**
// * i know its simple (technically not actually) but i just had to for the bit
// * its too silly
// */
// public enum Type {
// DIRECTED(1),
// WEIGHTED(2),
// MULTI(4);

// private final int val;

// Type(int val) {
// this.val = val;
// }

// public int val() {
// return val;
// }
// }

// ArrayList<Node<T>> nodes;
// ArrayList<ArrayList<Integer>> adjmat;
// HashMap<Node<T>, ArrayList<Node<T>>> adjmap;
// int type;

// Node<T> head = null;
// Node<T> tail = null;

// public Graph(int type) {
// System.out.println(type);
// this.nodes = new ArrayList<>();
// this.adjmap = new HashMap<>();
// this.adjmat = new ArrayList<>();

// }

// public void setHead(Node<T> head) {
// this.head = head;
// }

// public Node<T> getHead() {
// return head;
// }

// public void setTail(Node<T> tail) {
// this.tail = tail;
// }

// public Node<T> getTail() {
// return tail;
// }

// public boolean appendNode(Node<T> node) {

// return nodes.add(node);
// }

// public boolean removeNode(Node<T> node) {
// if (!nodes.contains(node))
// return false;

// int index = 0;
// for (int i = 0; i < nodes.size(); i++) {
// nodes.get(i).removeNext(node);
// nodes.get(i).removePrev(node);
// if (nodes.get(i) == node)
// index = i;
// }

// // adjmat.remove(index);
// // for (int i = 0; i < adjmat.size(); i++) {
// // adjmat.get(i).remove(index);
// // }

// // adjmap.forEach((key, value) -> {
// // value.remove(node);
// // });
// // adjmap.remove(node);

// nodes.get(index).removeNext(node);
// nodes.get(index).removePrev(node);
// nodes.remove(index);

// return true;
// }

// }
