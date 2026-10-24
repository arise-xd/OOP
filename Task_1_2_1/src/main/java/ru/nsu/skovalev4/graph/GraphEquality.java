package ru.nsu.skovalev4.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Provides common equality and hash code operations for graphs.
 */
final class GraphEquality {

    private GraphEquality() {
    }

    /**
     * Creates an order-independent map of vertices and their neighbours.
     *
     * @param graph graph to convert
     * @param <V> type of graph vertices
     * @return map of vertices and their neighbours
     */
    private static <V> Map<V, Set<V>> graphMap(Graph<V> graph) {
        Map<V, Set<V>> graphHashMap = new HashMap<>();
        for (V vertex : graph.getVertices()) {
            Set<V> neighbours = new HashSet<>();
            neighbours.addAll(graph.getNeighbours(vertex));
            graphHashMap.put(vertex, neighbours);
        }
        return graphHashMap;
    }

    /**
     * Compares a graph with another object by vertices and edges.
     *
     * @param graph graph to compare
     * @param other object to compare with the graph
     * @return true if the objects represent equal graphs, false otherwise
     */
    static boolean areEqual(Graph<?> graph, Object other) {
        if (graph == other) {
            return true;
        } else if (!(other instanceof Graph<?>)) {
            return false;
        } else {
            Graph<?> otherGraph = (Graph<?>) other;
            return graphMap(graph).equals(graphMap(otherGraph));
        }
    }

    /**
     * Calculates a hash code from graph vertices and edges.
     *
     * @param graph graph whose hash code is required
     * @return hash code of the graph
     */
    static int calculateHashCode(Graph<?> graph) {
        return graphMap(graph).hashCode();
    }
}
