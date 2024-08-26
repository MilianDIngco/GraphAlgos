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

    private HashMap<Node<T>, ArrayList<Node<T>>> adjList;

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
        if (!adjList.containsKey(nodeOne) || !adjList.containsKey(nodeTwo))
            return false;

        adjList.get(nodeOne).add(nodeTwo);
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
        for (var list : adjList.values()) {
            list.remove(node);
        }
        adjList.remove(node);
        return node;
    }

    /**
     * Removes an edge between two nodes in the specified direction
     * from nodeOne -> nodeTwo
     * but not from nodeTwo -> nodeOne
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @return Returns true if the edge was successfully removed, returns false if
     *         edge does not exist
     */
    public boolean removeEdge(Node<T> nodeOne, Node<T> nodeTwo) {
        if (!adjList.containsKey(nodeOne) || !adjList.containsKey(nodeTwo))
            return false;
        return adjList.get(nodeOne).remove(nodeTwo);
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
        return (adjList.containsKey(nodeOne) && adjList.containsKey(nodeTwo) && adjList.get(nodeOne).contains(nodeTwo));
    }

    /**
     * @param node Gets all nodes this node has an edge pointing to
     * @return Returns a list of Node<T> that are connected to the given node or
     *         null if the node does not exist
     */
    public List<Node<T>> getNeighbors(Node<T> node) {
        if (!adjList.containsKey(node))
            return null;
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
            for (var edge : pair.getValue()) {
                res += edge + " ";
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