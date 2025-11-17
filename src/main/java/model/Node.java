package model;

import java.util.ArrayList;


public interface Node {

    /// @return identifier of this node
    String getName();
    /// @param name identifier of this node
    void setName(String name);
    /// @return stored value of this node or the vertex between this and its parent.
    String getValue();
    /// @param value stored value of this node or the vertex between this and its parent.
    void setValue(String value);
    /// @return node one level higher in the tree, that points to this one.
    Node getParent();

    /// @return collection of all nodes, this node is pointing to.
    ArrayList<Node> getChildren();
    /// @return tree, this node belongs to
    Tree getTree();

    /// @param node is added as a new child of this.
    void addChild(Node node);
    /// Creates a new node and adds it to this as a child.
    void addChild(String name);
    /// Creates a new node and adds it to this as a child.
    void addChild(String name, String value);
    /**
     * @param node is added to this as a new child.
     * @param vertexValue is saved to node.value; representing the value of the vertex between this and node.
     */
    void addChild(Node node, String vertexValue);
    /// Adds all elements in nodes as children to this.
    void addChildren(ArrayList<Node> nodes);
    /// Creates new nodes and adds them to this as children.
    void addChildrenfromNames(ArrayList<String> nodeNames);
    /// Removes child from this node and sets child.parent to null.
    void removeChild(Node child);
    /// Removes the first child with name = childName from this node and sets child.parent to null.
    void removeChild(String childName);
    /// Removes all elements in children from this node and sets their parent attribute to null.
    void removeChildren(ArrayList<Node> children);
    /// Removes the first child with name = childName for from this node and sets child.parent to null.
    void removeChildrenbyNames(ArrayList<String> childrenNames);
    /// Returns the first child, whose  name = childName.
    Node getChild(String childName);

    //public void mergeChildren(Node child1, Node child2, String name);

    /**
     * Inserts a new Node between node1, node2 and this and updates values of n1, n2.
     * <p>
     *  . p .............. p<p>
     *  ./. \ .. --> ... |<p>
     * n1 . n2 ....... new<p>
     *  ................... / . \<p>
     *  .................. n1 . n2<p>
     * <p>
     * node1 and node2 must be children of this.
     * Nodes with value "0" are retracted.<p>
     * Nodes at node1 and node2 are removed from queue and newNode is added to queue.
     * @param value1 new value of child1
     * @param value2 new value of child2
     * @param newName name given to the inserted node
     * @exception TreeException if child1 or child2 are no children of this.
     */
    void mergeChildren(ArrayList<Node> queue, int node1, int node2, String value1, String value2, String newName);

    /**
     * Inserts a new Node between node1, node2 and this and updates values of n1, n2.
     * <p>
     *  . p .............. p<p>
     *  ./. \ .. --> ... |<p>
     * n1 . n2 ....... new<p>
     *  ................... / . \<p>
     *  .................. n1 . n2<p>
     * <p>
     * node1 and node2 must be children of this.
     * Nodes with value "0" are retracted.<p>
     * Nodes at node1 and node2 are removed from queue and newNode is added to queue.
     * @param value1 new value of child1
     * @param value2 new value of child2
     * @exception TreeException if child1 or child2 are no children of this.
     */
    void mergeChildren(ArrayList<Node> queue, int node1, int node2, String value1, String value2);

    /**
     * Inserts a new Node between child1, child2 and this and updates values of n1, n2.
     * The new node's name is the concatenation of child1's and child2's names.
     *
     * <p>
     *  . p .............. p<p>
     *  ./. \ .. --> ... |<p>
     * n1 . n2 ....... new<p>
     *  ................... / . \<p>
     *  .................. n1 . n2<p>
     * <p>
     * child1 and child2 must be children of this.
     * Nodes with value "0" are retracted.
     * @param value1 new value of child1
     * @param value2 new value of child2
     * @exception TreeException if child1 or child2 are no children of this.
     * @return newNode, which is attached between child1, child2 and their parent.
     */
    Node mergeChildren(Node child1, Node child2, String value1, String value2);


    /**
     * Inserts a new Node between child1, child2 and this and updates values of n1, n2.
     * <p>
     *  . p .............. p<p>
     *  ./. \ .. --> ... |<p>
     * n1 . n2 ....... new<p>
     *  ................... / . \<p>
     *  .................. n1 . n2<p>
     * <p>
     * child1 and child2 must be children of this.
     * Nodes with value "0" are retracted.
     * @param value1 new value of child1
     * @param value2 new value of child2
     * @param newName name given to the inserted node
     * @exception TreeException if child1 or child2 are no children of this.
     * @return newNode, which is attached between child1, child2 and their parent.
     */
    Node mergeChildren(Node child1, Node child2, String value1, String value2, String newName);

    /// @return true, iff node is a child of this.
    boolean isChild(Node node);
    /// @return true, iff this has a child with name = nodeName.
    boolean isChild(String nodeName);

    /**
     * Calculates the Newick-representation of the subtree consisting of this as root and all descendants.
     *
     * @return "(*children's Newick string*):*value*"
     */
    String getNewick();
}
