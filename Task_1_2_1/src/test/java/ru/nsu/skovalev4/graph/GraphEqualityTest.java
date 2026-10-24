package ru.nsu.skovalev4.graph;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class GraphEqualityTest {

    @Test
    void equalGraphsIgnoreRepresentationAndInsertionOrder() {
        Graph<Integer> adjacencyList = new AdjacencyListGraph<>();
        adjacencyList.addVertex(10);
        adjacencyList.addVertex(20);
        adjacencyList.addVertex(30);
        adjacencyList.addEdge(10, 20);
        adjacencyList.addEdge(20, 30);

        Graph<Integer> adjacencyMatrix = new AdjacencyMatrixGraph<>();
        adjacencyMatrix.addVertex(30);
        adjacencyMatrix.addVertex(20);
        adjacencyMatrix.addVertex(10);
        adjacencyMatrix.addEdge(10, 20);
        adjacencyMatrix.addEdge(20, 30);

        Graph<Integer> incidenceMatrix = new IncidenceMatrixGraph<>();
        incidenceMatrix.addVertex(20);
        incidenceMatrix.addVertex(10);
        incidenceMatrix.addVertex(30);
        incidenceMatrix.addEdge(10, 20);
        incidenceMatrix.addEdge(20, 30);

        assertAll(
            () -> assertEquals(adjacencyList, adjacencyList),
            () -> assertEquals(adjacencyList, adjacencyMatrix),
            () -> assertEquals(adjacencyMatrix, adjacencyList),
            () -> assertEquals(adjacencyMatrix, incidenceMatrix),
            () -> assertEquals(incidenceMatrix, adjacencyList),
            () -> assertEquals(adjacencyList.hashCode(), adjacencyMatrix.hashCode()),
            () -> assertEquals(adjacencyMatrix.hashCode(), incidenceMatrix.hashCode())
        );
    }

    @Test
    void differentGraphsAndOtherObjectsAreNotEqual() {
        Graph<Integer> firstGraph = new AdjacencyListGraph<>();
        firstGraph.addVertex(10);
        firstGraph.addVertex(20);
        firstGraph.addEdge(10, 20);

        Graph<Integer> secondGraph = new AdjacencyMatrixGraph<>();
        secondGraph.addVertex(20);
        secondGraph.addVertex(10);
        secondGraph.addEdge(20, 10);

        assertAll(
            () -> assertNotEquals(firstGraph, secondGraph),
            () -> assertNotEquals(secondGraph, firstGraph),
            () -> assertNotEquals(firstGraph, null),
            () -> assertNotEquals(firstGraph, "graph")
        );
    }
}
