package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class AufgabenTestcases {

    Tree t, t2;
    Node r, n1, n2, n3, l1, l2, l3;

    @BeforeEach
    void setUp() {
        t = new TreeClass("root", "R");
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

        assertEquals(node.getTree(), t);
        assertEquals(node.getName(), name);
        assertEquals(node.getValue(), value);
        assertNull(node.getChild("Horst"));
        assertNull(node.getParent());
        assertEquals(node.getChildren().size(), 0);
    }

}