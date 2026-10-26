package ru.nsu.skovalev4.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides topological sorting for directed graphs.
 */
public final class TopologicalSorter {

    private TopologicalSorter() {
    }

    /**
     * Returns graph vertices in topological order.
     *
     * @param graph graph to sort
     * @param <V> type of graph vertices
     * @return unmodifiable list of vertices in topological order
     * @throws IllegalStateException if the graph contains a cycle
     */
    public static <V> List<V> sort(Graph<V> graph) {
        List<V> vertices = graph.getVertices();
        Map<V, Integer> inDegrees = calculateInDegrees(graph);
        Deque<V> queue = new ArrayDeque<>();
        for (V vertex : vertices) {
            if (inDegrees.get(vertex) == 0) {
                queue.addLast(vertex);
            }
        }
        List<V> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            V vertex = queue.removeFirst();
            result.add(vertex);
            for (V neighbour : graph.getNeighbours(vertex)) {
                int newDegree = inDegrees.get(neighbour) - 1;
                inDegrees.put(neighbour, newDegree);
                if (newDegree == 0) {
                    queue.addLast(neighbour);
                }
            }
        }
        if (vertices.size() != result.size()) {
            throw new IllegalStateException("Graph has a cycle");
        }
        return List.copyOf(result);
    }

    private static <V> Map<V, Integer> calculateInDegrees(Graph<V> graph) {
        Map<V, Integer> inDegrees = new LinkedHashMap<>();
        for (V vertex : graph.getVertices()) {
            inDegrees.put(vertex, 0);
        }
        for (V from : graph.getVertices()) {
            for (V to : graph.getNeighbours(from)) {
                int newInDegrees = inDegrees.get(to) + 1;
                inDegrees.put(to, newInDegrees);
            }
        }
        return inDegrees;
    }
}
