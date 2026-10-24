package ru.nsu.skovalev4.graph;

class AdjacencyMatrixGraphTest extends AbstractGraphTest {

    @Override
    protected Graph<Integer> createGraph() {
        return new AdjacencyMatrixGraph<>();
    }
}