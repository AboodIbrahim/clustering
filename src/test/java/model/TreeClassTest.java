package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class TreeClassTest {

    Node root, l1, l2, l3, n1, n2, n3;
    Tree t = new TreeClass("Root", "R"),
            t2;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getRoot() {
        ArrayList<String> leafNames = new ArrayList<>(asList("A", "B", "C"));
        String rootName = "Hans";
        t = new TreeClass(rootName, "", leafNames);
        assertEquals(t.getRoot().getName(), rootName);
    }

    @Test
    void getNodes() {
        ArrayList<String> leafNames = new ArrayList<>(asList("A", "B", "C"));
        String rootName = "Root";
        t = new TreeClass(rootName, "", leafNames);
        assertEquals(rootName, t.getNodes().get(0).getName());
        for (int i = 0; i < leafNames.size(); i++)
            assertEquals(leafNames.get(i), t.getNodes().get(i+1).getName());

        assertEquals(4, t.getNodes().size());
    }


    @Test
    void getNodesWithAddNode() {

        ArrayList<String> leafNames = new ArrayList<>(asList("A", "B", "C"));
        String rootName = "Root";
        t = new TreeClass(rootName, "", leafNames);
        t.getRoot().getChild("B").addChild("B1","");

        assertEquals(rootName, t.getNodes().get(0).getName());
        for (int i = 0; i < leafNames.size(); i++)
            assertEquals(leafNames.get(i), t.getNodes().get(i+1).getName());
        assertEquals("B1", t.getNodes().get(4).getName());

        assertEquals(5, t.getNodes().size());
    }

    @Test
    void getNodesWithRemoveNode() {
        ArrayList<String> leafNames = new ArrayList<>(asList("A", "B", "C"));
        String rootName = "Root";
        t = new TreeClass(rootName, "", leafNames);
        t.getRoot().removeChild("B");

        assertEquals(rootName, t.getNodes().get(0).getName());
        assertEquals(leafNames.get(0), t.getNodes().get(1).getName());
        assertEquals(leafNames.get(2), t.getNodes().get(2).getName());

        assertEquals(3, t.getNodes().size());
    }

    @Test
    void TreeException() {
        t = new TreeClass("R1", "");
        t2 = new TreeClass("R2", "");

        assertThrows(TreeException.class, ()->{t.getRoot().addChild(t2.getRoot());}, "Node cannot be added to tree.");
    }

    @Test
    void getNewick() {

        ArrayList<String> leafNames = new ArrayList<>(asList("AB", "C"));
        String rootName = "Hans";
        t = new TreeClass(rootName, "", leafNames);
        t.getRoot().getChild("AB").setValue("7");
        t.getRoot().getChild("C").setValue("1");
        t.getRoot().getChild("AB").addChild("A", "3");
        t.getRoot().getChild("AB").addChild("B", "4");
        String newick = "((A:3,B:4):7,C:1);";
        assertEquals(t.getNewick(), newick);
    }

    @Test
    void mergeNodes() {
        ArrayList<String> leafNames = new ArrayList<>(asList("A", "B", "C"));
        String rootName = "Hans";
        t = new TreeClass(rootName, "", leafNames);
        Node r = t.getRoot();
        r.getChild("C").setValue("1");

        t.MergeNodes(r.getChild("A"), r.getChild("B"), "3", "4");
        System.out.println(r.getChildren().get(1).getName());

        t.MergeNodes(r.getChild("AB"), r.getChild("C"), "3", "4");

        String newick = "((A:3,B:4):3,C:4);";

        assertEquals(newick, t.getNewickWithoutRoot());
    }


}