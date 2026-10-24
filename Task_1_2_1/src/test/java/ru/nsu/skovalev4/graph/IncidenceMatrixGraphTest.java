package ru.nsu.skovalev4.graph;

class IncidenceMatrixGraphTest extends AbstractGraphTest {

    @Override
    protected Graph<Integer> createGraph() {
        return new IncidenceMatrixGraph<>();
    }
}
