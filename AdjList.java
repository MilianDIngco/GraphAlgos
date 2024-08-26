import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/*
 * TO DO:
 *  - tradeoff between memory and speed for removeNode()
 *  - add copy constructor
 */

/**
 * Adjacency List class
 * 
 * Member Methods:
 * - addNode(Node<T> node)
 * - addEdge(Node<T> nodeOne, Node<T> nodeTwo)
 * - removeNode(Node<T> node)
 * - removeEdge(Node<T> nodeOne, Node<T> nodeTwo)
 * - edgeExists()
 * - getNeighbors(Node<T> node)
 * - getAllNodes()
 * - getSize()
 * - toString()
 * 
 * @author Milian Ingco
 * @version 1.0
 * @since 8/23/2024
 */
public class AdjList<T> {

    private HashMap<Node<T>, ArrayList<Pair<Node<T>, Double>>> adjList;

    public AdjList() {
        adjList = new HashMap<>();
    }

    /**
     * Adds node to the graph
     * 
     * @param node Node to be added
     * @return Returns true if successfully added, false if node already exists
     */
    public boolean addNode(Node<T> node) {
        if (adjList.containsKey(node))
            return false;
        adjList.put(node, new ArrayList<>());
        return true;
    }

    /**
     * Adds a directional edge between two nodes in the specified direction
     * from nodeOne -> nodeTwo
     * but not from nodeTwo -> nodeOne
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @return Returns true if adding the node was successful, returns false if
     *         either node does not already exist in the graph
     */
    public boolean addEdge(Node<T> nodeOne, Node<T> nodeTwo) {
        return addEdge(nodeOne, nodeTwo, 1);
    }

    /**
     * Adds a directional edge between two nodes in the specified direction with a
     * weight
     * from nodeOne -> nodeTwo
     * but not from nodeTwo -> nodeOne
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @param weight  Weight of the edge
     * @return Returns true if adding the node was successful, returns false if
     *         either node does not already exist in the graph
     */
    public boolean addEdge(Node<T> nodeOne, Node<T> nodeTwo, Double weight) {
        if (!adjList.containsKey(nodeOne) || !adjList.containsKey(nodeTwo))
            return false;

        adjList.get(nodeOne).add(new Pair<Node<T>, Double>(nodeTwo, weight));

        return true;
    }

    /**
     * Removes a node from the graph
     * 
     * @param node Node to be removed
     * @return Returns the removed node, null if node doesn't exist
     */
    public Node<T> removeNode(Node<T> node) {
        if (!adjList.containsKey(node))
            return null;
        // No list of edges because could be a directed graph; must check each list
        // could probably keep track of edges as memory is less of a concern though
        adjList.remove(node);
        for (var list : adjList.values()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).one == node)
                    list.remove(i);
            }
        }

        return node;
    }

    /**
     * Removes an edge given a source node and a target node
     * Removes the first edge found between the two
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @return Returns true if the edge was successfully removed, false if either
     *         node doesn't exist or if the edge itself doesn't exist
     */
    public boolean removeEdge(Node<T> nodeOne, Node<T> nodeTwo) {
        if (!adjList.containsKey(nodeOne) || !adjList.containsKey(nodeTwo))
            return false;
        ArrayList<Pair<Node<T>, Double>> edgeList = adjList.get(nodeOne);
        for (int i = 0; i < edgeList.size(); i++) {
            if (edgeList.get(i).one == nodeTwo) {
                edgeList.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an edge given a node and the edge
     * 
     * @param nodeOne The SOURCE node
     * @param edge    The edge from the SOURCE node
     * @return Returns true if the edge was successfully removed, returns false if
     *         edge does not exist
     */
    public boolean removeEdge(Node<T> nodeOne, Pair<Node<T>, Double> edge) {
        if (!adjList.containsKey(nodeOne) || !adjList.containsKey(edge.one))
            return false;
        return adjList.get(nodeOne).remove(edge);
    }

    /**
     * Detects if a given directed edge exists between two nodes
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @return Returns true if an edge exists between the two nodes, false if either
     *         node doesn't exist or the edge doesn't exist
     */
    public boolean edgeExists(Node<T> nodeOne, Node<T> nodeTwo) {
        ArrayList<Pair<Node<T>, Double>> edgeList = adjList.get(nodeOne);
        for (Pair<Node<T>, Double> edge : edgeList) {
            if (edge.one == nodeTwo)
                return true;
        }
        return false;
    }

    /**
     * Detects if a given edge exists given a node and an edge
     * 
     * @param node The SOURCE node
     * @param edge The edge
     * @return Returns true if the edge exists, false if it doesn't or if either
     *         node doesn't exist
     */
    public boolean edgeExists(Node<T> node, Pair<Node<T>, Double> edge) {
        if (!adjList.containsKey(node) || !adjList.containsKey(edge.one))
            return false;

        return adjList.get(node).contains(edge);
    }

    /**
     * @param node Gets all nodes this node has an edge pointing to
     * @return Returns a list of Pair<Node<T>> representing edges and their
     *         weights that are connected to the given node or
     *         null if the node does not exist
     */
    public List<Pair<Node<T>, Double>> getNeighbors(Node<T> node) {
        if (!adjList.containsKey(node))
            return null;
        ArrayList<Node<T>> neighbors = new ArrayList<>(adjList.get(node).size());
        for (Pair<Node<T>, Double> edge : adjList.get(node)) {
            neighbors.add(edge.one);
        }
        return adjList.get(node);
    }

    /**
     * @return Returns a list of all nodes in the graph
     */
    public List<Node<T>> getAllNodes() {
        return new ArrayList<Node<T>>(adjList.keySet());
    }

    /**
     * @return Returns the number of nodes in the graph
     */
    public int getSize() {
        return adjList.size();
    }

    public String toString() {
        String res = "";
        for (var pair : adjList.entrySet()) {
            res += pair.getKey().getVal() + ": ";
            for (Pair<Node<T>, Double> edge : pair.getValue()) {
                res += "(" + edge.one.getVal() + ", " + edge.two + ") ";
            }
            res += "\n";
        }

        return res;
    }

}

/*
 * add edge
 * add node
 * remove node
 * remove edge
 * get connected nodes
 * get all nodes
 * get amount of connected nodes
 * 
 */