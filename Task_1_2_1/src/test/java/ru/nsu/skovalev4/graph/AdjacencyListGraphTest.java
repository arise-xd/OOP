package ru.nsu.skovalev4.graph;

class AdjacencyListGraphTest extends AbstractGraphTest {

    @Override
    protected Graph<Integer> createGraph() {
        return new AdjacencyListGraph<>();
    }
}