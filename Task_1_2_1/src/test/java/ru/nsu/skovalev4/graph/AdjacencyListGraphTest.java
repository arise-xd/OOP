package ru.nsu.skovalev4.graph;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {

    @Test
    void addNewVertex() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        assertTrue(adjacencyGraph.addVertex(10));
        assertEquals(List.of(10), adjacencyGraph.getVertices());

    }

    @Test
    void addExistingVertex() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        adjacencyGraph.addVertex(10);
        assertFalse(adjacencyGraph.addVertex(10));

    }

    @Test
    void addNullVertex() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        assertThrows(
            IllegalArgumentException.class,
            () -> adjacencyGraph.addVertex(null)
        );
    }

    @Test
    void verticesReturnsInAddingOrder() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        assertTrue(adjacencyGraph.addVertex(10));
        assertTrue(adjacencyGraph.addVertex(20));
        assertTrue(adjacencyGraph.addVertex(67));
        assertEquals(List.of(10, 20, 67), adjacencyGraph.getVertices());
    }

    @Test
    void listOfVerticesIsImmutable() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        adjacencyGraph.addVertex(10);
        adjacencyGraph.addVertex(20);
        adjacencyGraph.addVertex(67);

        assertThrows(
            UnsupportedOperationException.class,
            () -> adjacencyGraph.getVertices().clear()
        );

        assertEquals(List.of(10, 20, 67), adjacencyGraph.getVertices());
    }

    @Test
    void addNewEdge() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        adjacencyGraph.addVertex(10);
        adjacencyGraph.addVertex(20);
        adjacencyGraph.addVertex(30);
        assertTrue(adjacencyGraph.addEdge(10, 30));
        assertTrue(adjacencyGraph.addEdge(10, 20));
        assertEquals(List.of(20, 30), adjacencyGraph.getNeighbours(10));
        assertEquals(List.of(), adjacencyGraph.getNeighbours(20));
        assertThrows(
            UnsupportedOperationException.class,
            () -> adjacencyGraph.getNeighbours(10).clear()
        );
    }

    @Test
    void addExistingEdge() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        adjacencyGraph.addVertex(10);
        adjacencyGraph.addVertex(20);
        assertTrue(adjacencyGraph.addEdge(10, 20));
        assertFalse(adjacencyGraph.addEdge(10,20));
    }

    @Test
    void addEdgeRejectsInvalidVertices() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        adjacencyGraph.addVertex(10);
        adjacencyGraph.addVertex(20);

        assertAll(
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> adjacencyGraph.addEdge(30, 10)
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> adjacencyGraph.addEdge(10, 30)
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> adjacencyGraph.addEdge(10, 10)
            )
        );
    }

    @Test
    void removeExistingAndMissingEdge() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        adjacencyGraph.addVertex(10);
        adjacencyGraph.addVertex(20);

        assertTrue(adjacencyGraph.addEdge(10, 20));
        assertTrue(adjacencyGraph.removeEdge(10, 20));
        assertEquals(List.of(), adjacencyGraph.getNeighbours(10));
        assertFalse(adjacencyGraph.removeEdge(10, 20));
    }

    @Test
    void removeEdgeRejectsInvalidVertices() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        adjacencyGraph.addVertex(10);
        adjacencyGraph.addVertex(20);
        adjacencyGraph.addEdge(10, 20);
        assertAll(
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> adjacencyGraph.removeEdge(30, 10)
            ),
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> adjacencyGraph.removeEdge(10, 30)
            )
        );
    }

    @Test
    void removeExistingVertexAndIncidentEdges() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        adjacencyGraph.addVertex(10);
        adjacencyGraph.addVertex(20);
        adjacencyGraph.addVertex(30);
        adjacencyGraph.addEdge(10, 20);
        adjacencyGraph.addEdge(20, 30);
        adjacencyGraph.addEdge(30, 20);
        assertTrue(adjacencyGraph.removeVertex(20));
        assertEquals(List.of(10, 30), adjacencyGraph.getVertices());
        assertEquals(List.of(), adjacencyGraph.getNeighbours(10));
        assertEquals(List.of(), adjacencyGraph.getNeighbours(30));
        assertThrows(
            IllegalArgumentException.class,
            () -> adjacencyGraph.getNeighbours(20));
    }

    @Test
    void removeMissingVertexReturnsFalse() {
        Graph<Integer> adjacencyGraph = new AdjacencyListGraph<>();

        adjacencyGraph.addVertex(10);
        adjacencyGraph.addVertex(20);
        adjacencyGraph.addVertex(30);

        assertFalse(adjacencyGraph.removeVertex(67));
        assertEquals(List.of(10, 20, 30), adjacencyGraph.getVertices());
    }
}