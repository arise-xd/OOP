package ru.nsu.skovalev4.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a directed graph stored as an adjacency list.
 *
 * @param <V> type of graph vertices
 */
public class AdjacencyListGraph<V> implements Graph<V> {

    private final Map<V, Set<V>> adjacency;

    /**
     * Creates an empty adjacency list graph.
     */
    public AdjacencyListGraph() {
        adjacency = new LinkedHashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex vertex to add
     * @return true if the vertex was added, false if it already exists
     * @throws IllegalArgumentException if the vertex is null
     */
    @Override
    public boolean addVertex(V vertex) {

        if (vertex == null) {
            throw new IllegalArgumentException("Vertex can't be null");
        }

        if (adjacency.containsKey(vertex)) {
            return false;
        } else {
            adjacency.put(vertex, new LinkedHashSet<>());
            return true;
        }
    }

    /**
     * Adds a directed edge between two vertices.
     *
     * @param from starting vertex of the edge
     * @param to ending vertex of the edge
     * @return true if the edge was added, false if it already exists
     * @throws IllegalArgumentException if a vertex does not exist or the vertices are equal
     */
    @Override
    public boolean addEdge(V from, V to) {
        if (!adjacency.containsKey(from)) {
            throw new IllegalArgumentException("Start vertex doesn't exist");
        } else if (!adjacency.containsKey(to)) {
            throw new IllegalArgumentException("Finish vertex doesn't exist");
        } else if (from.equals(to)) {
            throw new IllegalArgumentException("Start vertex equals to finish vertex");
        } else {
            return adjacency.get(from).add(to);
        }
    }

    /**
     * Removes a vertex and all its incident edges from the graph.
     *
     * @param vertex vertex to remove
     * @return true if the vertex was removed, false if it does not exist
     */
    @Override
    public boolean removeVertex(V vertex) {
        if (!adjacency.containsKey(vertex)) {
            return false;
        } else {
            adjacency.remove(vertex);
            for (V key : adjacency.keySet()) {
                adjacency.get(key).remove(vertex);
            }
            return true;
        }
    }

    /**
     * Removes a directed edge between two vertices.
     *
     * @param from starting vertex of the edge
     * @param to ending vertex of the edge
     * @return true if the edge was removed, false if it does not exist
     * @throws IllegalArgumentException if a vertex does not exist
     */
    @Override
    public boolean removeEdge(V from, V to) {
        if (!adjacency.containsKey(from)) {
            throw new IllegalArgumentException("Start vertex doesn't exist");
        } else if (!adjacency.containsKey(to)) {
            throw new IllegalArgumentException("Finish vertex doesn't exist");
        } else {
            return adjacency.get(from).remove(to);
        }
    }

    /**
     * Returns the neighbours of a vertex in vertex insertion order.
     *
     * @param vertex vertex whose neighbours are required
     * @return unmodifiable list of neighbouring vertices
     * @throws IllegalArgumentException if the vertex does not exist
     */
    @Override
    public List<V> getNeighbours(V vertex) {
        if (!adjacency.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex doesn't exist");
        } else {
            Set<V> neighbours = adjacency.get(vertex);
            List<V> sortedNeighbours = new ArrayList<>();
            for (V ver : adjacency.keySet()) {
                if (neighbours.contains(ver)) {
                    sortedNeighbours.add(ver);
                }
            }
            return List.copyOf(sortedNeighbours);
        }
    }

    /**
     * Returns all vertices in vertex insertion order.
     *
     * @return unmodifiable list of vertices
     */
    @Override
    public List<V> getVertices() {
        return List.copyOf(adjacency.keySet());
    }

    @Override
    public String toString() {
        return GraphFormatter.format(this);
    }
}
