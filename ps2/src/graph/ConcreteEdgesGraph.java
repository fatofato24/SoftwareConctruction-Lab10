package graph;

import java.util.*;

/**
 * Concrete implementation of a weighted, directed graph with String vertices.
 *
 * Abstraction function:
 *   AF(vertices, edges) = the graph with vertices in 'vertices' and edges in 'edges',
 *                         where each edge connects two vertices with a specific weight.
 *
 * Representation invariant:
 *   - vertices != null
 *   - edges != null
 *   - No edge in edges has source or target not in vertices
 *   - No edge has negative weight
 *
 * Safety from rep exposure:
 *   - All methods returning internal data (vertices, sources, targets) return
 *     copies or unmodifiable views to prevent modification of the rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    /** checkRep ensures the rep invariant holds */
    private void checkRep() {
        assert vertices != null : "Vertices set is null";
        assert edges != null : "Edges list is null";
        for (Edge e : edges) {
            assert vertices.contains(e.getSource()) : "Edge source not in vertices";
            assert vertices.contains(e.getTarget()) : "Edge target not in vertices";
            assert e.getWeight() >= 0 : "Edge weight negative";
        }
    }

    @Override
    public boolean add(String vertex) {
        Objects.requireNonNull(vertex, "Vertex cannot be null");
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }

    @Override
    public int set(String source, String target, int weight) {
        Objects.requireNonNull(source, "Source cannot be null");
        Objects.requireNonNull(target, "Target cannot be null");
        if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");

        int oldWeight = 0;
        Edge existingEdge = null;
        for (Edge e : edges) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                existingEdge = e;
                oldWeight = e.getWeight();
                break;
            }
        }

        if (existingEdge != null) edges.remove(existingEdge);
        if (weight > 0) edges.add(new Edge(source, target, weight));

        vertices.add(source);
        vertices.add(target);

        checkRep();
        return oldWeight;
    }

    @Override
    public boolean remove(String vertex) {
        Objects.requireNonNull(vertex, "Vertex cannot be null");
        boolean removed = vertices.remove(vertex);
        edges.removeIf(e -> e.getSource().equals(vertex) || e.getTarget().equals(vertex));
        checkRep();
        return removed;
    }

    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(new HashSet<>(vertices));
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Objects.requireNonNull(target, "Target cannot be null");
        Map<String, Integer> result = new HashMap<>();
        for (Edge e : edges) {
            if (e.getTarget().equals(target)) {
                result.put(e.getSource(), e.getWeight());
            }
        }
        return result;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Objects.requireNonNull(source, "Source cannot be null");
        Map<String, Integer> result = new HashMap<>();
        for (Edge e : edges) {
            if (e.getSource().equals(source)) {
                result.put(e.getTarget(), e.getWeight());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Edges: ").append(edges);
        return sb.toString();
    }

    /** Edge class represents an immutable directed edge */
    public static class Edge {

        private final String source;
        private final String target;
        private final int weight;

        /**
         * Constructor
         * @throws IllegalArgumentException if source/target is null or weight < 0
         */
        public Edge(String source, String target, int weight) {
            if (source == null || target == null || weight < 0) {
                throw new IllegalArgumentException("Invalid edge parameters");
            }
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public String getSource() { return source; }
        public String getTarget() { return target; }
        public int getWeight() { return weight; }

        @Override
        public String toString() {
            return source + " -> " + target + " (" + weight + ")";
        }
    }
}
