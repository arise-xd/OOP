package ru.nsu.skovalev4.graph;

import java.util.List;

final class GraphFormatter {

    private GraphFormatter() {
    }

    static <V> String format(Graph<V> graph) {
        List<V> vertices = graph.getVertices();
        int edgeCount = 0;
        for (V vertex : vertices) {
            edgeCount += graph.getNeighbours(vertex).size();
        }
        StringBuilder builder = new StringBuilder();
        builder.append(vertices.size());
        builder.append(' ');
        builder.append(edgeCount);

        for (V vertex : vertices) {
            builder.append('\n');
            builder.append(vertex);
        }

        for (V from : vertices) {
            List<V> neighbours = graph.getNeighbours(from);
            for (V to : neighbours) {
                builder.append('\n');
                builder.append(from);
                builder.append(' ');
                builder.append(to);
            }
        }
        return builder.toString();
    }
}
