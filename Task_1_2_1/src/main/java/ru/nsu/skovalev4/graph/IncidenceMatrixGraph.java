package ru.nsu.skovalev4.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a directed graph stored as an incidence matrix.
 *
 * @param <V> type of graph vertices
 */
public class IncidenceMatrixGraph<V> implements Graph<V> {

    private final Map<V, Integer> vertexIndices;
    private final List<List<Integer>> matrix;
    private int edgeCount;

    /**
     * Creates an empty incidence matrix graph.
     */
    public IncidenceMatrixGraph() {
        vertexIndices = new LinkedHashMap<>();
        matrix = new ArrayList<>();
        edgeCount = 0;
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

            List<Integer> newRow = new ArrayList<>();
            for (int i = 0; i < edgeCount; i++) {
                newRow.add(0);
            }

            matrix.add(newRow);
            vertexIndices.put(vertex, vertexIndices.size());
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
            if (findEdgeColumn(fromIndex, toIndex) != -1) {
                return false;
            }

            for (int row = 0; row < vertexIndices.size(); row++) {
                if (row == fromIndex) {
                    matrix.get(row).add(-1);
                } else if (row == toIndex) {
                    matrix.get(row).add(1);
                } else {
                    matrix.get(row).add(0);
                }
            }
            edgeCount++;
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
            int vertexIndex = vertexIndices.get(vertex);
            List<Integer> incidentColumns = new ArrayList<>();

            for (int column = 0; column < edgeCount; column++) {
                if (matrix.get(vertexIndex).get(column) != 0) {
                    incidentColumns.add(column);
                }
            }

            for (int i = incidentColumns.size() - 1; i >= 0; i--) {
                int column = incidentColumns.get(i);

                for (List<Integer> row : matrix) {
                    row.remove(column);
                }
            }
            edgeCount -= incidentColumns.size();
            matrix.remove(vertexIndex);
            vertexIndices.remove(vertex);
            for (Map.Entry<V, Integer> entry : vertexIndices.entrySet()) {
                if (entry.getValue() > vertexIndex) {
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
            int edgeColumn = findEdgeColumn(fromIndex, toIndex);
            if (edgeColumn == -1) {
                return false;
            }
            for (List<Integer> row : matrix) {
                row.remove(edgeColumn);
            }
            edgeCount--;
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
            List<V> neighbours = new ArrayList<>();
            for (V candidate : vertexIndices.keySet()) {
                int candidateIndex = vertexIndices.get(candidate);
                if (findEdgeColumn(vertexIndex, candidateIndex) != -1) {
                    neighbours.add(candidate);
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

    private int findEdgeColumn(int fromIndex, int toIndex) {
        for (int column = 0; column < edgeCount; column++) {
            if (matrix.get(fromIndex).get(column) == -1
                && matrix.get(toIndex).get(column) == 1) {
                return column;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return GraphFormatter.format(this);
    }
}
