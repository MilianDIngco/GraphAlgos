
/**
 * Main
 */

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Collections;
import java.util.random.*;

public class Main {

    public static void main(String[] args) {
        // Set up window
        // JFrame window = new JFrame("Grapher");
        // window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // window.setBounds(new Rectangle(800, 500));

        // window.setVisible(true);

    }
}

/*
 * ONE TEST I WROTE TO CHECK THE ADD AND REMOVE NODES AND EDGES WORKED PROPERLY
 * int nodesSize = 25;
 * int nonodeSize = 10;
 * int nEdges = 50;
 * ArrayList<Node<Integer>> nodes = new ArrayList<>(nodesSize);
 * ArrayList<Node<Integer>> nodesCopy = new ArrayList<>(nodesSize);
 * ArrayList<Node<Integer>> nodesNOT = new ArrayList<>(nonodeSize);
 * ArrayList<int[]> edges = new ArrayList<>(nEdges);
 * 
 * int totalFail = 0;
 * for (int i = 0; i < 100; i++) {
 * nodes.clear();
 * nodesCopy.clear();
 * edges.clear();
 * int success = 0;
 * int fail = 0;
 * // NODES that exist
 * for (int n = 0; n < nodesSize; n++) {
 * nodes.add(new Node<Integer>(n));
 * nodesCopy.add(nodes.get(n));
 * }
 * 
 * // NODES that don't exist
 * for (int n = 0; n < nonodeSize; n++) {
 * nodesNOT.add(new Node<Integer>((int) (Math.random() * 100)));
 * }
 * 
 * // EDGES was gonna let self loops not exist but realized they do
 * for (int n = 0; n < nEdges; n++) {
 * int one = (int) (Math.random() * nodesSize);
 * int two = (int) (Math.random() * nodesSize);
 * 
 * edges.add(new int[] { one, two });
 * }
 * 
 * AdjList<Integer> am = new AdjList<>();
 * 
 * // ADD NODES
 * for (Node<Integer> n : nodes) {
 * am.addNode(n);
 * }
 * 
 * // ADD EDGES
 * for (int[] n : edges) {
 * am.addEdge(nodes.get(n[0]), nodes.get(n[1]));
 * }
 * 
 * // System.out.println(am);
 * 
 * // REMOVE EDGES and NODES
 * while (edges.size() > 0 && nodes.size() > 0) {
 * if (Math.random() > .5) {
 * // REMOVE EDGES
 * // index of edge list
 * int index = (int) (Math.random() * edges.size());
 * int[] edge = edges.remove(index);
 * // should return false if the edge doesn't exist, or if either node doesn't
 * // exist
 * boolean ans = true;
 * if (!nodes.contains(nodesCopy.get(edge[0])) ||
 * !nodes.contains(nodesCopy.get(edge[1]))) {
 * ans = false;
 * System.out.println("[EDGE NODE WAS REMOVED]");
 * }
 * 
 * if (ans == am.removeEdge(nodesCopy.get(edge[0]), nodesCopy.get(edge[1]))) {
 * success++;
 * } else {
 * fail++;
 * }
 * 
 * } else {
 * // REMOVE NODES
 * int index = (int) (Math.random() * nodes.size());
 * // System.out.println("Node: " + index + " Nodes Size: " + nodes.size());
 * Node<Integer> remove = nodes.remove(index);
 * am.removeNode(remove);
 * }
 * 
 * System.out.println("RemoveEdge: Success [" + success + "] Fail [" + fail +
 * "]");
 * }
 * 
 * System.out.println(am);
 * 
 * if (fail > 0) {
 * totalFail++;
 * }
 * }
 * System.out.println("Total Fails: " + totalFail);
 * 
 * 
 */