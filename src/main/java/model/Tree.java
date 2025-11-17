package model;

import java.util.ArrayList;

/**
 * Represents a rooted Tree.
 * It points to a root and all its nodes.
 * Vertices and additional data are stored in Node objects.
 */
public interface Tree {

    /// @return Root of the tree.
    Node getRoot();

    /// @return all nodes.
    ArrayList<Node> getNodes();

    /**
     * Inserts a new Node between n1, n2 and their parent and updates values of n1, n2.
     * <p>
     *  . p .............. p<p>
     *  ./. \ .. --> ... |<p>
     * n1 . n2 ....... new<p>
     *  ................... / . \<p>
     *  .................. n1 . n2<p>
     * <p>
     * n1 and n2 Must have the same parent in the same tree.
     * Nodes with value "0" are retracted.
     * @param value1 new value of n1
     * @param value2 new value of n2
     * @exception TreeException if n1 or n2 do not have the same parent in the same tree.
     * @return newNode, which is attached between n1, n2 and their parent.
     */
    Node MergeNodes(Node n1, Node n2, String value1, String value2) throws TreeException;

    //public void removeRoot();
    /**
     * Inserts a new Node between n1, n2 and their parent and updates values of n1, n2.
     * <p>
     *  . p .............. p<p>
     *  ./. \ .. --> ... |<p>
     * n1 . n2 ....... new<p>
     *  ................... / . \<p>
     *  .................. n1 . n2<p>
     * <p>
     * n1 and n2 Must have the same parent in the same tree.
     * Nodes with value "0" are retracted.
     * Nodes at node1 and node2 are removed from queue and newNode is added to queue.
     * @param value1 new value of n1
     * @param value2 new value of n2
     * @exception TreeException if n1 or n2 do not have the same parent in the same tree.
     * @return newNode, which is attached between n1, n2 and their parent.
     */
    void MergeNodes(ArrayList<Node> queue, int node1, int node2, String value1, String value2);
    /**
     * Inserts a new Node between n1, n2 and their parent and updates values of n1, n2.
     * <p>
     *  . p .............. p<p>
     *  ./. \ .. --> ... |<p>
     * n1 . n2 ....... new<p>
     *  ................... / . \<p>
     *  .................. n1 . n2<p>
     * <p>
     * n1 and n2 Must have the same parent in the same tree.
     * Nodes with value "0" are retracted.
     * @param value1 new value of n1
     * @param value2 new value of n2
     * @exception TreeException if n1 or n2 do not have the same parent in the same tree.
     * @return newNode, which is attached between n1, n2 and their parent.
     */
    void MergeNodes(ArrayList<Node> queue, int node1, int node2, String value1, String value2, String newName);

    /// @return Newick-representation of this tree
    String getNewick();

    String getNewickWithoutRoot();
}
