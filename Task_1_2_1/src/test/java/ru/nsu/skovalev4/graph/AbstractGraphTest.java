package ru.nsu.skovalev4.graph;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

abstract class AbstractGraphTest {

    protected abstract Graph<Integer> createGraph();

    @Test
    void addNewVertex() {
        Graph<Integer> graph = createGraph();

        assertTrue(graph.addVertex(10));
        assertEquals(List.of(10), graph.getVertices());

    }

    @Test
    void addExistingVertex() {
        Graph<Integer> graph = createGraph();

        graph.addVertex(10);
        assertFalse(graph.addVertex(10));

    }

    @Test
    void addNullVertex() {
        Graph<Integer> graph = createGraph();

        assertThrows(
            IllegalArgumentException.class,
            () -> graph.addVertex(null)
        );
    }

    @Test
    void verticesReturnsInAddingOrder() {
        Graph<Integer> graph = createGraph();

        assertTrue(graph.addVertex(10));
        assertTrue(graph.addVertex(20));
        assertTrue(graph.addVertex(67));
        assertEquals(List.of(10, 20, 67), graph.getVertices());
    }

    @Test
    void listOfVerticesIsImmutable() {
        Graph<Integer> graph = createGraph();

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(67);

        assertThrows(
            UnsupportedOperationException.class,
            () -> graph.getVertices().clear()
        );

        assertEquals(List.of(10, 20, 67), graph.getVertices());
    }

    @Test
    void addNewEdge() {
        Graph<Integer> graph = createGraph();

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        assertTrue(graph.addEdge(10, 30));
        assertTrue(graph.addEdge(10, 20));
        assertEquals(List.of(20, 30), graph.getNeighbours(10));
        assertEquals(List.of(), graph.getNeighbours(20));
        assertThrows(
            UnsupportedOperationException.class,
            () -> graph.getNeighbours(10).clear()
        );
    }

    @Test
    void addExistingEdge() {
        Graph<Integer> graph = createGraph();

        graph.addVertex(10);
        graph.addVertex(20);
        assertTrue(graph.addEdge(10, 20));
        assertFalse(graph.addEdge(10, 20));
    }

    @Test
    void addEdgeRejectsInvalidVertices() {
        Graph<Integer> graph = createGraph();

        graph.addVertex(10);
        graph.addVertex(20);

        assertAll(
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(30, 10)
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(10, 30)
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> graph.addEdge(10, 10)
            )
        );
    }

    @Test
    void removeExistingAndMissingEdge() {
        Graph<Integer> graph = createGraph();

        graph.addVertex(10);
        graph.addVertex(20);

        assertTrue(graph.addEdge(10, 20));
        assertTrue(graph.removeEdge(10, 20));
        assertEquals(List.of(), graph.getNeighbours(10));
        assertFalse(graph.removeEdge(10, 20));
    }

    @Test
    void removeEdgeRejectsInvalidVertices() {
        Graph<Integer> graph = createGraph();

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addEdge(10, 20);
        assertAll(
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> graph.removeEdge(30, 10)
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> graph.removeEdge(10, 30)
            )
        );
    }

    @Test
    void removeExistingVertexAndIncidentEdges() {
        Graph<Integer> graph = createGraph();

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addEdge(10, 20);
        graph.addEdge(20, 30);
        graph.addEdge(30, 20);
        assertTrue(graph.removeVertex(20));
        assertEquals(List.of(10, 30), graph.getVertices());
        assertEquals(List.of(), graph.getNeighbours(10));
        assertEquals(List.of(), graph.getNeighbours(30));
        assertThrows(
            IllegalArgumentException.class,
            () -> graph.getNeighbours(20));
    }

    @Test
    void removeMissingVertexReturnsFalse() {
        Graph<Integer> graph = createGraph();

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);

        assertFalse(graph.removeVertex(67));
        assertEquals(List.of(10, 20, 30), graph.getVertices());
    }
}