package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class NodeClassTest {

    Tree t, t2;
    Node r, n1, n2, n3, l1, l2, l3;

    @BeforeEach
    void setUp() {
        t = new TreeClass("root", "");
        r = t.getRoot();
    }

    @AfterEach
    void tearDown()
    {
        t = null;
        r = null;
    }

    @Test
    void constructorAndGetter()
    {
        String name = "nA",
                value = "A";

        Node node = new NodeClass(name, value, t);

        assertEquals(t, node.getTree());
        assertEquals(name, node.getName());
        assertEquals(value, node.getValue());
        assertNull(node.getChild("Horst"));
        assertNull(node.getParent());
        assertEquals(0, node.getChildren().size());
    }

    private Node[] setUp1()
    {
        String name = "nR",
                value = "5";
        Node parent = new NodeClass(name, value, t);

        String nameA = "nA", valA = "A",
                nameB = "nB", valB = "B";
        Node cA = new NodeClass(nameA, valA, t),
                cB = new NodeClass(nameB, valB, t);

        parent.addChild(cA);
        parent.addChild(cB);

        return new Node[] {parent, cA, cB};
    }

    @Test
    void addChild() {
        Node[] nodes = setUp1();
        Node parent = nodes[0], cA = nodes[1], cB = nodes[2];
        assertEquals(2, parent.getChildren().size());
    }

    @Test
    void addChildren() {
        ArrayList<Node> children = new ArrayList<>();
        children.add(new NodeClass("A", t));
        children.add(new NodeClass("B", t));
        children.add(new NodeClass("C", t));
        r.addChildren(children);

        assertTrue(r.isChild("A"));
        assertTrue(r.isChild("B"));
        assertTrue(r.isChild("C"));
        assertFalse(r.isChild("D"));

        assertEquals(3, r.getChildren().size());
    }

    @Test
    void removeChild() {
        String name = "n1";
        Node child = new NodeClass(name, "A", t),
                parent = t.getRoot();

        parent.addChild(child);
        parent.removeChild(name);

        assertNull(parent.getChild(name));
        assertNull(child.getParent());
    }

    @Test
    void getChild() {
        n1 = new NodeClass("n1",  t);
        r.addChild(n1);

        assertEquals(n1, r.getChild("n1"));
        assertNull(r.getChild("b"));
    }

    @Test
    void mergeChildren() {

        ArrayList<String> leafNames = new ArrayList<>(asList("A", "B", "C"));

        r.addChildrenfromNames(leafNames);
        r.getChild("C").setValue("1");

        t.MergeNodes(r.getChild("A"), r.getChild("B"), "3", "4");

        String newick = "(C:1,(A:3,B:4):);";

        assertEquals(newick, t.getNewick());
    }

    @Test
    void isChild() {
        n1 = new NodeClass("n1", r);
        n1.addChild("A", "2");
        r.addChild("B", "");
        assertTrue(n1.isChild("A"));
        assertFalse(n1.isChild("B"));
    }


    @Test
    void getNewick() {
        ArrayList<String> leafNames = new ArrayList<>(asList("AB", "C"));

        r.addChildrenfromNames(leafNames);
        t.getRoot().getChild("AB").setValue("7");
        t.getRoot().getChild("C").setValue("1");
        t.getRoot().getChild("AB").addChild("A", "3");
        t.getRoot().getChild("AB").addChild("B", "4");
        String newick = "((A:3,B:4):7,C:1):";
        assertEquals(r.getNewick(), newick);
    }
}