package model;


import java.util.ArrayList;

/**
 * Represents a rooted Tree.
 * It points to a root and all its nodes.
 * Vertices and additional data are stored in Node objects.
 */
public class TreeClass implements Tree {

    Node root;
    ArrayList<Node> nodes = new ArrayList<>();

    public TreeClass(Node root)
    { Create(root); }

    public TreeClass(String rootName, String rootValue)
    { Create(rootName, rootValue); }

    public TreeClass(ArrayList<String> leafNames)
    { Create("Root", "", leafNames); }

    public TreeClass(String rootName, String rootValue, ArrayList<String> leafNames)
    { Create(rootName, rootValue, leafNames); }

    private void Create(Node root)
    {
        this.root = root;
        this.nodes = new ArrayList<>();
        this.nodes.add(root);
    }

    private void Create(String rootName, String rootValue)
    { this.root = new NodeClass(rootName, rootValue, this); }

    private void Create(String rootName, String rootValue, ArrayList<String> leafNames)
    {
        this.root = new NodeClass(rootName, rootValue, this);
        leafNames.forEach(name -> this.root.addChild(name , ""));
    }

    @Override
    public Node getRoot() { return root; }

    @Override
    public ArrayList<Node> getNodes() { return nodes; }


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
    @Override
    public Node MergeNodes(Node n1, Node n2, String value1, String value2)
    { return n1.getParent().mergeChildren(n1, n2, value1, value2); }

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
    @Override
    public void MergeNodes(ArrayList<Node> queue, int node1, int node2, String value1, String value2)
    { queue.get(node1).getParent().mergeChildren(queue,node1, node2, value1, value2); }

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
    @Override
    public void MergeNodes(ArrayList<Node> queue, int node1, int node2, String value1, String value2, String newName)
    { queue.get(node1).getParent().mergeChildren(queue,node1, node2, value1, value2, newName); }


    public void MergeNodes(Node n1, Node n2, String vertexValue1, String vertexValue2, String newName){
        if (n1.getParent() != n2.getParent())
            throw new TreeException(n1.getName()+" and "+ n2.getName()+" cannot be merged, as they don't have the same parent node.");

        n1.getParent().mergeChildren(n1, n2, vertexValue1, vertexValue2, newName);
    }

    /// @return Newick-representation of this tree without the root.
    @Override
    public String getNewick()
    {
        StringBuilder newick = new StringBuilder();
        newick.append(root.getNewick());
        newick.deleteCharAt(newick.length()-1);
        newick.append(';');
        return newick.toString();
    }

    /// @return Newick-representation of this tree.
    @Override
    public String getNewickWithoutRoot()
    {
        StringBuilder newick = new StringBuilder();
        newick.append(root.getChildren().getFirst().getNewick());
        newick.deleteCharAt(newick.length()-1);
        newick.append(';');
        return newick.toString();
    }


    /// Should only be called by NodeClass.
    protected void addNode(Node newNode) {
        if (newNode.getTree() != this)
            throw new TreeException("This method should only be called by NodeClass.setTree().");

        nodes.add(newNode);
    }

    /// Should only be called by NodeClass.
    protected void removeNode(Node node) {
        if (node.getTree() != this)
            throw new TreeException("This method should only be called by NodeClass.removeTree().");
        nodes.remove(node);
    }
}
