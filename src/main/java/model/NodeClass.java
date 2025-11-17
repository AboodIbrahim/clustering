package model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class NodeClass implements Node{
    String name;
    String value;

    Node parent;

    Tree tree;
    ArrayList<Node> children;
    ArrayList<String> childrenNames;
    //ArrayList<String> vertexValues;

    public NodeClass(String name, Tree tree)
    {
        if (tree == null)
            throw new TreeException("Cannot create Node. Tree must not be null.");
        Create(name, "", null, tree, new ArrayList<>());
    }

    public NodeClass(String name, Node parent)
    {
        if (parent == null)
            throw new TreeException("Cannot create Node. Parent must not be null.");
        Create(name, "", parent, null);
    }

    public NodeClass(String name, Node parent, ArrayList<Node> children)
    { Create(name, "", parent, null, children); }

    public NodeClass(String name, String value, Tree tree)
    {
        if (tree == null)
            throw new TreeException("Cannot create Node. Tree must not be null.");
        Create(name, value, null, tree, new ArrayList<>());
    }

    public NodeClass(String name, String value, Node parent)
    {
        if (parent == null)
            throw new TreeException("Cannot create Node. Parent must not be null.");
        Create(name, value, parent, null);
    }

    public NodeClass(String name, String value, Node parent, ArrayList<Node> children)
    { Create(name, value, parent, null, children); }

    private void Create(String name, String value, Node parent, Tree tree) { Create(name, value, parent, tree, new ArrayList<Node>()); }


    private void Create(String name, String value, Node parent, Tree tree, ArrayList<Node> children)
    {
        ArrayList<String> vertexValues = children.stream().map(Node -> "")
                        .collect(Collectors.toCollection(ArrayList::new));
        Create(name, value, parent, tree, children, vertexValues);
    }

    private void Create(String name, String value, Node parent, Tree tree, ArrayList<Node> children, ArrayList<String> vertexValues)
    {
        if (children.size() != vertexValues.size())
            throw new TreeException("When creating a node, the number of vertexValues must correspond to the number of children.");

        this.name = name;
        this.value = value;
        this.children = children;

        childrenNames = children.stream().map(Node::getName)
                .collect(Collectors.toCollection(ArrayList::new));

        //this.vertexValues = vertexValues;
        //setParent(parent);
        if (parent != null) {
            setTree(parent.getTree());

            parent.addChild(this);
        }
        else
            setTree(tree);
    }

    private void setTree(Tree tree)
    {
        this.tree = tree;
        ((TreeClass)tree).addNode(this);
    }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public String getValue() { return value; }

    @Override
    public void setValue(String value) { this.value = value; }

    @Override
    public Node getParent() { return parent; }

    @Override
    public ArrayList<Node> getChildren() { return children; }

    @Override
    public Tree getTree() { return tree; }

    @Override
    public void addChild(Node node)
    { addChild(node, ""); }

    @Override
    public void addChild(String name) { addChild(name, "");}

    @Override
    public void addChild(String name, String value)
    { addChild(new NodeClass(name, value, this.getTree())); }

    @Override
    public void addChild(Node node, String vertexValue)
    {
        if (isChild(node))
            throw new TreeException(node.getName() + " cannot be added to " + name + ", as it is already a child.");
        if (node.getTree() != this.tree)
            throw new TreeException("Cannot add " + node.getName() + " to " + name + " as a child, as they are not in the same tree.");

        children.add(node);
        childrenNames.add(node.getName());
        ((NodeClass)node).setParent(this);
    }

    @Override
    public void addChildren(ArrayList<Node> nodes) {
        for (Node node : nodes)
            addChild(node);
    }

    @Override
    public void addChildrenfromNames(ArrayList<String> nodeNames) {
        for (String nodeName : nodeNames)
            addChild(nodeName);
    }

    protected void setParent(Node node) {
        // Called by outside method.
        if (parent == node)
            return;
        if (parent == null && node.isChild(this))
            this.parent = node;
        else
            throw new TreeException(name + " already has a parent, so it cannot be assigned to an other parent.");
    }

    @Override
    public void removeChild(Node child)
    {
        if (!children.contains(child))
            throw new TreeException(child.getName() + " cannot be removed from " + name + ", as it is not a child.");
        children.remove(child);
        ((NodeClass)child).removeParent();
        childrenNames.remove(child.getName());
        ((TreeClass)tree).removeNode(child);
    }

    private void removeTree()
    {
        ((TreeClass)tree).removeNode(this);
        tree = null;
    }

    @Override
    public void removeChild(String childName) {
        Node child = getChild(childName);
        if (child == null)
            throw new TreeException(childName + " cannot be removed, as it is not a child of " + name + ".");
        removeChild(child);
    }



    @Override
    public void removeChildren(ArrayList<Node> children) {
        for (Node child : children)
            removeChild(child);
    }

    @Override
    public void removeChildrenbyNames(ArrayList<String> childrenNames) {
        for (String childName : childrenNames)
            removeChild(childName);
    }

    protected void removeParent() { parent = null; }

    @Override
    public Node getChild(String childName) {
        for (Node child : children)
            if (child.getName().equals(childName))
                return child;
        return null;
    }

    public void mergeChildren(ArrayList<Node> queue, int node1, int node2, String value1, String value2)
    { mergeChildren(queue, node1, node2, value1, value2, queue.get(node1).getName() + queue.get(node1).getName()); }

    public void mergeChildren(ArrayList<Node> queue, int node1, int node2, String value1, String value2, String newName)
    {
        Node n1 = queue.get(node1),
                n2 = queue.get(node2);
        Node newNode = mergeChildren(n1, n2, value1, value2, newName);
        queue.remove(n1);
        queue.remove(n2);
        queue.addFirst(newNode);
    }

    @Override
    public Node mergeChildren(Node child1, Node child2, String value1, String value2)
    { return mergeChildren(child1, child2, value1, value2, child1.getName() + child2.getName()); }

    @Override
    public Node mergeChildren(Node child1, Node child2, String value1, String value2, String newName)
    {
        if (child1.getParent() != this || child2.getParent() != this)
            throw new TreeException(child1.getName()+" and "+ child2.getName()+" cannot be merged, as they don't have the same parent node.");

        if (value2 == "0")
            if (value1 == "0") {
                child1.setName(child1.getName() + child2.getName());
                this.removeChild(child2);
                return child1;
            }
            else
                return mergeChildren(child2, child1, value2, value1);
        else if (value1 == "0")
        {
            removeChild(child2);
            child1.addChild(child2);
            return child1;
        }
        else
        {
            Node newNode = new NodeClass(newName,"", this);

            removeChild(child1);
            newNode.addChild(child1);
            child1.setValue(value1);
            removeChild(child2);
            newNode.addChild(child2);
            child2.setValue(value2);

            return newNode;
        }
    }

    @Override
    public boolean isChild(Node node) { return isChild(node.getName()); }

    @Override
    public boolean isChild(String nodeName) { return childrenNames.contains(nodeName); }


    @Override
    public String getNewick()
    {
        if (children.isEmpty())
            return name + ":" + value;
        else {
            StringBuilder str = new StringBuilder();
            str.append('(');
            for (Node child : children) {
                str.append(child.getNewick());
                str.append(',');
            }
            if (str.charAt(str.length()-1) == ',')
                str.deleteCharAt(str.length()-1);
            str.append("):");
            str.append(this.value);
            return str.toString();
        }
    }
}
