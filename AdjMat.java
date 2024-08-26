import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Adjacency Matrix Class
 * 
 * Member Methods:
 * - addNode(Node<T> a)
 * - addEdge(Node<T> a, Node<T> b)
 * - removeNode(Node<T> a)
 * - removeEdge(Node<T> a, Node<T> b)
 * - getNeighbors()
 * - getAllNodes()
 * - containsNode(Node<T> node)
 * - toString()
 * - edgeExists(Node<T> a, Node<T> b)
 * 
 * @author Milian Ingco
 * @version 1.0
 * @since 8/23/2024
 */
public class AdjMat<T> {

    private ArrayList<ArrayList<Integer>> matrix;
    private HashMap<Node<T>, Integer> lookIndex;
    private ArrayList<Node<T>> lookNode;

    private int capacity = 1;

    AdjMat() {
        matrix = new ArrayList<>(capacity);
        lookIndex = new HashMap<>();
        lookNode = new ArrayList<>();
    }

    /**
     * Adds node to the graph
     * Supposedly should be amortized O(log n) because it fills up and resizes log2
     * n or smth
     * 
     * @param node Node to be added
     * @return Returns false if the node already exists, returns true if successful
     */
    public boolean addNode(Node<T> node) {
        if (lookIndex.containsKey(node))
            return false;

        lookIndex.put(node, lookNode.size());
        lookNode.add(node);

        if (lookNode.size() == capacity) {
            // System.out.println("RESIZED MATRIX");
            resize(matrix);
        }
        matrix.add(new ArrayList<>(Collections.nCopies(lookNode.size(), 0)) {
            {
                ensureCapacity(capacity);
            }
        });

        for (int i = 0; i < matrix.size() - 1; i++) {
            matrix.get(i).add(0);
        }

        // for (ArrayList<Integer> i : matrix) {
        // for (Integer n : i) {
        // System.out.print(n + " ");
        // }
        // System.out.println();
        // }
        // System.out.println("--------------");

        return true;
    }

    /**
     * Removes a given node
     * !! Having to update each node in the hashmap isn't that bad considering
     * deleting nodes will probably be much less common than every other update !!
     * 
     * @param node Given node
     * @return Returns node removed, null if not successful
     */
    public Node<T> removeNode(Node<T> node) {
        if (!lookIndex.containsKey(node))
            return null;
        int index = lookIndex.get(node);

        lookIndex.remove(node);
        Node<T> del = lookNode.remove(index);

        lookIndex.forEach((K, V) -> {
            if (V > index)
                lookIndex.put(K, V - 1);
        });

        // System.out.println(node);
        // System.out.println(matrix.size());
        matrix.remove(index);
        for (int i = 0; i < matrix.size(); i++) {
            // System.out.println(i + " " + matrix.size());
            matrix.get(i).remove(index);
        }
        // System.out.println("--------");

        return del;
    }

    /**
     * Creates a directional edge from nodeOne to nodeTwo
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @return Returns false if either node doesn't exist, true if creating the edge
     *         was successful
     */
    public boolean addEdge(Node<T> nodeOne, Node<T> nodeTwo) {
        if (!lookIndex.containsKey(nodeOne) || !lookIndex.containsKey(nodeTwo))
            return false;

        int indexOne = lookIndex.get(nodeOne);
        int indexTwo = lookIndex.get(nodeTwo);
        int currentEdges = matrix.get(indexOne).get(indexTwo);
        matrix.get(indexOne).set(indexTwo, currentEdges + 1);
        return true;
    }

    /**
     * Removes a directional edge from nodeOne to nodeTwo
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @return Returns false if either node doesn't exist, or if the edge doesn't
     *         exist. True otherwise
     */
    public boolean removeEdge(Node<T> nodeOne, Node<T> nodeTwo) {
        if (!lookIndex.containsKey(nodeOne) || !lookIndex.containsKey(nodeTwo))
            return false;

        int indexOne = lookIndex.get(nodeOne);
        int indexTwo = lookIndex.get(nodeTwo);
        int numEdges = matrix.get(indexOne).get(indexTwo);

        if (numEdges < 1)
            return false;

        matrix.get(indexOne).set(indexTwo, numEdges - 1);
        return true;
    }

    public List<Node<T>> getNeighbors(Node<T> node) {
        return null;
    }

    /**
     * !! NOT A DEEP OR SHALLOW COPY MIGHT WANNA CHANGE THAT !!
     * 
     * @return Returns the list of all nodes in the graph
     */
    public ArrayList<Node<T>> getAllNodes() {
        return lookNode;
    }

    /**
     * Checks if the graph contains a given node
     * 
     * @param node Node to be checked
     * @return Returns true if the node exists, false if otherwise
     */
    public boolean containsNode(Node<T> node) {
        return lookIndex.containsKey(node);
    }

    /**
     * Checks if the graph contains a given directed edge
     * 
     * @param nodeOne The SOURCE node
     * @param nodeTwo The TARGET node
     * @return Returns the number of edges between the two nodes, -1 if either node
     *         does not exist
     */
    public int edgeExists(Node<T> nodeOne, Node<T> nodeTwo) {
        if (!lookIndex.containsKey(nodeOne) || !lookIndex.containsKey(nodeTwo))
            return -1;
        return matrix.get(lookIndex.get(nodeOne)).get(lookIndex.get(nodeTwo));
    }

    public String toString() {
        if (lookNode.size() < 1)
            return "empty";

        String str = "";

        // find largest digit number / value which is size of boxes + 2
        int max = -1;
        for (Node<T> node : lookNode) {
            int len = node.getVal().toString().length();
            max = (len > max) ? len : max;
        }

        for (int i = 0; i < matrix.size(); i++) {
            for (int n = 0; n < matrix.get(i).size(); n++) {
                int len = matrix.get(i).get(n).toString().length();
                max = (len > max) ? len : max;
            }
        }

        max += 2;

        String gap = "";
        for (int i = 0; i < max; i++)
            gap += " ";
        str += gap + "|";

        // PRINT COLUMN LABELS
        for (int i = 0; i < lookNode.size(); i++) {
            String value = lookNode.get(i).getVal().toString();
            str += gap.substring(0, (max - value.length()) / 2);
            str += value;
            str += gap.substring(0, (max - value.length() + 1) / 2);
            str += "|";
        }

        // PRINT DIVIDER
        str += "\n";
        for (int i = 0; i < lookNode.size() + 1; i++) {
            str += String.valueOf("-").repeat(max);
            str += "+";
        }

        for (int i = 0; i < lookNode.size(); i++) {
            // PRINT ROW LABELS
            str += "\n";
            String value = lookNode.get(i).getVal().toString();
            str += gap.substring(0, (max - value.length()) / 2);
            str += value;
            str += gap.substring(0, (max - value.length() + 1) / 2);
            str += "|";

            // PRINT EDGE VALUES
            for (int n = 0; n < lookNode.size(); n++) {
                String edges = matrix.get(i).get(n).toString();
                str += gap.substring(0, (max - edges.length()) / 2);
                str += edges;
                str += gap.substring(0, (max - edges.length() + 1) / 2);
                str += "|";
            }
        }

        return str;
    }

    /**
     * Resizes the array to be twice the initial size
     * 
     * @param matrix 2D ArrayList to be resized
     */
    private void resize(ArrayList<ArrayList<Integer>> matrix) {
        capacity *= 2;
        matrix.ensureCapacity(capacity);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.get(i).ensureCapacity(capacity);
        }
    }

}
