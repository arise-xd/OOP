package ru.nsu.skovalev4.graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

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

    /**
     * Reads vertices and directed edges from a file.
     * The first line contains the vertex and edge counts. It is followed by one vertex
     * per line and then one edge per line in the form of two vertices.
     *
     * @param path path to the graph file
     * @param vertexParser function that converts text into a vertex
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the file has an invalid format
     * @throws IllegalStateException if the graph is not empty
     */
    default void readFromFile(Path path, Function<String, V> vertexParser)
        throws IOException {
        List<String> lines = Files.readAllLines(path);
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Graph file is empty");
        }

        String[] header = lines.get(0).trim().split("\\s+");

        if (header.length != 2) {
            throw new IllegalArgumentException("Header must contain two numbers");
        }

        int vertexCount = Integer.parseInt(header[0]);
        int edgeCount = Integer.parseInt(header[1]);

        if (vertexCount < 0 || edgeCount < 0) {
            throw new IllegalArgumentException("Counts can't be negative");
        }

        if (vertexCount + edgeCount + 1 != lines.size()) {
            throw new IllegalArgumentException("Invalid number of lines");
        }

        if (!getVertices().isEmpty()) {
            throw new IllegalStateException("Graph must be empty");
        }

        for (int vertexIndex = 1; vertexIndex <= vertexCount; vertexIndex++) {
            String vertexText = lines.get(vertexIndex).trim();
            if (vertexText.isEmpty()) {
                throw new IllegalArgumentException("Vertex can't be empty");
            }
            V vertex = vertexParser.apply(vertexText);
            if (!addVertex(vertex)) {
                throw new IllegalArgumentException("Vertex already exists");
            }
        }

        for (int edgeIndex = vertexCount + 1; edgeIndex <= vertexCount + edgeCount; edgeIndex++) {
            String[] edgeText = lines.get(edgeIndex).trim().split("\\s+");
            if (edgeText.length != 2) {
                throw new IllegalArgumentException("Edge must contain two vertices");
            }

            V fromVertex = vertexParser.apply(edgeText[0]);
            V toVertex = vertexParser.apply(edgeText[1]);
            if (!addEdge(fromVertex, toVertex)) {
                throw new IllegalArgumentException("Edge already exists");
            }
        }
    }
}
