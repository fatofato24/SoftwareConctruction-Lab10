/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    
    @Test
    public void testAddVertex() {
        Graph<String> g = emptyInstance();
        
        // add new vertex
        assertTrue("adding new vertex should return true", g.add("A"));
        
        // add duplicate
        assertFalse("adding duplicate vertex should return false", g.add("A"));
        
        // verify vertex exists
        assertTrue("graph should contain A", g.vertices().contains("A"));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> g = emptyInstance();
        g.add("A");
        g.add("B");

        assertTrue("removing existing vertex A should return true", g.remove("A"));
        assertFalse("removing non-existing vertex A should return false", g.remove("A"));
        assertFalse("A should no longer exist", g.vertices().contains("A"));
    }

    @Test
    public void testSetAddNewEdge() {
        Graph<String> g = emptyInstance();

        g.add("A");
        g.add("B");

        int prev = g.set("A", "B", 5);
        assertEquals("new edge should return previous weight 0", 0, prev);

        assertEquals("target B should have weight 5",
                Integer.valueOf(5), g.targets("A").get("B"));
    }

    @Test
    public void testSetUpdateEdge() {
        Graph<String> g = emptyInstance();
        g.add("A");
        g.add("B");

        g.set("A", "B", 5);
        int prev = g.set("A", "B", 10);

        assertEquals("previous weight should be 5", 5, prev);
        assertEquals("edge should be updated to 10",
                Integer.valueOf(10), g.targets("A").get("B"));
    }

    @Test
    public void testSetRemoveEdge() {
        Graph<String> g = emptyInstance();
        g.add("A");
        g.add("B");

        g.set("A", "B", 5);
        int prev = g.set("A", "B", 0);

        assertEquals("removing edge should return previous weight 5", 5, prev);
        assertFalse("edge A->B should be removed", g.targets("A").containsKey("B"));
    }

    @Test
    public void testVerticesAfterOperations() {
        Graph<String> g = emptyInstance();

        g.add("A");
        g.add("B");
        g.add("C");
        g.remove("B");

        assertEquals("graph should contain only A and C",
                Set.of("A", "C"), g.vertices());
    }

    @Test
    public void testSources() {
        Graph<String> g = emptyInstance();

        g.add("A");
        g.add("B");
        g.add("C");

        g.set("A", "C", 3);
        g.set("B", "C", 7);

        assertEquals("C should have two sources", 2, g.sources("C").size());
        assertEquals(Integer.valueOf(3), g.sources("C").get("A"));
        assertEquals(Integer.valueOf(7), g.sources("C").get("B"));
    }

    @Test
    public void testTargets() {
        Graph<String> g = emptyInstance();

        g.add("A");
        g.add("B");
        g.add("C");

        g.set("A", "B", 4);
        g.set("A", "C", 6);

        assertEquals("A should have two targets", 2, g.targets("A").size());
        assertEquals(Integer.valueOf(4), g.targets("A").get("B"));
        assertEquals(Integer.valueOf(6), g.targets("A").get("C"));
    }

    
}
