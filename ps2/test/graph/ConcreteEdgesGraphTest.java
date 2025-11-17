package graph;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 *
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph,
 * and also includes additional tests specific to this implementation.
 *
 * Tests for the Graph ADT spec belong in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /**
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /* ============================================================
       TESTS FOR ConcreteEdgesGraph.toString()
       ============================================================ */

    // Testing strategy:
    //  - empty graph
    //  - graph with vertices but no edges
    //  - graph with vertices and edges
    //  - check format: "source -> target (weight)"

    @Test
    public void testToStringEmpty() {
        assertEquals("Expected empty graph description",
                "Vertices: []\nEdges: []",
                emptyInstance().toString().trim());
    }

    @Test
    public void testToStringVerticesNoEdges() {
        Graph<String> g = emptyInstance();
        g.add("A");
        g.add("B");

        String s = g.toString();
        assertTrue("Should list vertices", s.contains("A"));
        assertTrue("Should list vertices", s.contains("B"));
        assertTrue("Should have no edges", s.contains("Edges: []"));
    }

    @Test
    public void testToStringWithEdges() {
        Graph<String> g = emptyInstance();
        g.add("A");
        g.add("B");
        g.set("A", "B", 5);

        String s = g.toString();
        assertTrue("Should contain edge", s.contains("A -> B (5)"));
    }

    /* ============================================================
       TESTS FOR Edge CLASS
       ============================================================ */

    // Testing strategy:
    //  - constructor rejects negative weight
    //  - getSource(), getTarget(), getWeight() return correct values
    //  - toString() format: "source -> target (weight)"

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeRejectsNegativeWeight() {
        new ConcreteEdgesGraph.Edge("A", "B", -1);
    }

    @Test
    public void testEdgeBasicGetters() {
        ConcreteEdgesGraph.Edge e = new ConcreteEdgesGraph.Edge("X", "Y", 10);

        assertEquals("X", e.getSource());
        assertEquals("Y", e.getTarget());
        assertEquals(10, e.getWeight());
    }

    @Test
    public void testEdgeToString() {
        ConcreteEdgesGraph.Edge e = new ConcreteEdgesGraph.Edge("U", "V", 3);
        assertEquals("U -> V (3)", e.toString());
    }
}
