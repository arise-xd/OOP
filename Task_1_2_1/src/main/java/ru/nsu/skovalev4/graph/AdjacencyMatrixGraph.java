package ru.nsu.skovalev4.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a directed graph stored as an adjacency matrix.
 *
 * @param <V> type of graph vertices
 */
public class AdjacencyMatrixGraph<V> implements Graph<V> {

    private final Map<V, Integer> vertexIndices;
    private final List<List<Boolean>> matrix;

    /**
     * Creates an empty adjacency matrix graph.
     */
    public AdjacencyMatrixGraph() {
        vertexIndices = new LinkedHashMap<>();
        matrix = new ArrayList<>();
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

        if (vertexIndices.containsKey(vertex)) {
            return false;
        } else {
            int oldSize = vertexIndices.size();
            for (List<Boolean> row : matrix) {
                row.add(false);
            }
            List<Boolean> newRow = new ArrayList<>();
            for (int i = 0; i < oldSize + 1; i++) {
                newRow.add(false);
            }
            matrix.add(newRow);
            vertexIndices.put(vertex, oldSize);
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
        if (!vertexIndices.containsKey(from)) {
            throw new IllegalArgumentException("Start vertex doesn't exist");
        } else if (!vertexIndices.containsKey(to)) {
            throw new IllegalArgumentException("Finish vertex doesn't exist");
        } else if (from.equals(to)) {
            throw new IllegalArgumentException("Start vertex equals to finish vertex");
        } else {
            int fromIndex = vertexIndices.get(from);
            int toIndex = vertexIndices.get(to);
            if (matrix.get(fromIndex).get(toIndex)) {
                return false;
            }

            matrix.get(fromIndex).set(toIndex, true);
            return true;
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
        if (!vertexIndices.containsKey(vertex)) {
            return false;
        } else {
            int removedIndex = vertexIndices.get(vertex);
            matrix.remove(removedIndex);
            for (List<Boolean> row : matrix) {
                row.remove(removedIndex);
            }
            vertexIndices.remove(vertex);
            for (Map.Entry<V, Integer> entry : vertexIndices.entrySet()) {
                if (entry.getValue() > removedIndex) {
                    entry.setValue(entry.getValue() - 1);
                }
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
        if (!vertexIndices.containsKey(from)) {
            throw new IllegalArgumentException("Start vertex doesn't exist");
        } else if (!vertexIndices.containsKey(to)) {
            throw new IllegalArgumentException("Finish vertex doesn't exist");
        } else {
            int fromIndex = vertexIndices.get(from);
            int toIndex = vertexIndices.get(to);
            if (!matrix.get(fromIndex).get(toIndex)) {
                return false;
            }

            matrix.get(fromIndex).set(toIndex, false);
            return true;
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
        if (!vertexIndices.containsKey(vertex)) {
            throw new IllegalArgumentException("Vertex doesn't exist");
        } else {
            int vertexIndex = vertexIndices.get(vertex);
            List<Boolean> row = matrix.get(vertexIndex);
            List<V> neighbours = new ArrayList<>();
            for (V ver : vertexIndices.keySet()) {
                if (row.get(vertexIndices.get(ver))) {
                    neighbours.add(ver);
                }
            }
            return List.copyOf(neighbours);
        }
    }

    /**
     * Returns all vertices in vertex insertion order.
     *
     * @return unmodifiable list of vertices
     */
    @Override
    public List<V> getVertices() {
        return List.copyOf(vertexIndices.keySet());
    }

    @Override
    public boolean equals(Object other) {
        return GraphEquality.areEqual(this, other);
    }

    @Override
    public int hashCode() {
        return GraphEquality.calculateHashCode(this);
    }

    @Override
    public String toString() {
        return GraphFormatter.format(this);
    }
}
