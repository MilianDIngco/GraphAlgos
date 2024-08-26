import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Milian Ingco
 * @version 1.0 ish
 * @since 8/26
 * 
 *        Description:
 * 
 *        Constructors:
 *        -
 * 
 *        Member Methods:
 *        - public boolean addNode<T>(Point p)
 *        - public boolean addNode<T>(int x, int y)
 *        - public boolean addNode<T>(Point p, T val)
 *        - public boolean addNode<T>(int x, int y, T val)
 *        - public Node<T> removeNode(Node<T> node)
 *        - public Node<T> getNode(Point p)
 *        - public Node<T> getNode(int x, int y)
 *        - public List<Node<T>> getNode(T value)
 *        - public Node<T> getNeighbors(Node<T> node)
 *        - public boolean addEdge(Node<T> nodeOne, Node<T> nodeTwo)
 *        - public boolean removeEdge(Node<T> nodeOne, Node<T> nodeTwo)
 *        - public void setType(int graphType)
 *        - public void setNamingFunction(NamingFunction<T> nf, T baseValue)
 * 
 * 
 *        root head tail etc
 * 
 * 
 *        Thoughts:
 *        - its not really worth creating a spatial partitioning system
 *        considering the most you'll have to loop over to find some nodes is
 *        trivial
 *        - why aren't there optional parameters D:
 */
public class Graph<T> {

    public enum Type {
        DIRECTED(1),
        WEIGHTED(2),
        MULTI(4);

        final int val;

        Type(int val) {
            this.val = val;
        }
    }

    private int graphType = 0;
    private AdjMat<T> am;
    private AdjList<T> al;
    private NamingFunction<T> nf;
    private T baseValue;

    public Graph(int graphType) {
        this.graphType = graphType;
        am = new AdjMat<>();
        al = new AdjList<>();
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
