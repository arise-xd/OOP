package ru.nsu.skovalev4.graph;

import java.util.List;

/**
 * Represents a directed graph with unique vertices and edges.
 *
 * @param <V> type of graph vertices
 */
public interface Graph<V> {

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex vertex to add
     * @return true if the vertex was added, false if it already exists
     * @throws IllegalArgumentException if the vertex is null
     */
    boolean addVertex(V vertex);

    /**
     * Adds a directed edge between two vertices.
     *
     * @param from starting vertex of the edge
     * @param to ending vertex of the edge
     * @return true if the edge was added, false if it already exists
     * @throws IllegalArgumentException if a vertex does not exist or the vertices are equal
     */
    boolean addEdge(V from, V to);

    /**
     * Removes a vertex and all its incident edges from the graph.
     *
     * @param vertex vertex to remove
     * @return true if the vertex was removed, false if it does not exist
     */
    boolean removeVertex(V vertex);

    /**
     * Removes a directed edge between two vertices.
     *
     * @param from starting vertex of the edge
     * @param to ending vertex of the edge
     * @return true if the edge was removed, false if it does not exist
     * @throws IllegalArgumentException if a vertex does not exist
     */
    boolean removeEdge(V from, V to);

    /**
     * Returns the neighbours of a vertex in vertex insertion order.
     *
     * @param vertex vertex whose neighbours are required
     * @return unmodifiable list of neighbouring vertices
     * @throws IllegalArgumentException if the vertex does not exist
     */
    List<V> getNeighbours(V vertex);

    /**
     * Returns all vertices in vertex insertion order.
     *
     * @return unmodifiable list of vertices
     */
    List<V> getVertices();
}
