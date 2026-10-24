package ru.nsu.skovalev4.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GraphFileTest {

    @TempDir
    Path tempDir;

    @Test
    void readGraphFromFile() throws IOException {
        Graph<String> adjacencyGraph = new AdjacencyListGraph<>();
        Path path = Path.of("src/test/resources/graph.txt");
        adjacencyGraph.readFromFile(path, text -> text);
        assertEquals(List.of("A", "B", "C"), adjacencyGraph.getVertices());
        assertEquals(List.of("B"), adjacencyGraph.getNeighbours("A"));
        assertEquals(List.of("C"), adjacencyGraph.getNeighbours("B"));
        assertEquals(List.of(), adjacencyGraph.getNeighbours("C"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "3",
        "A 2",
        "-1 0",
        "0 -1",
        "1 0",
        "1 0\n ",
        "2 0\nA\nA",
        "2 1\nA\nB\nA",
        "2 2\nA\nB\nA B\nA B",
        "1 1\nA\nA B",
        "1 1\nA\nA A"
    })
    void wrongFileFormatTest(String fileText) throws IOException {
        Path tempPath = tempDir.resolve("invalid-graph.txt");
        Files.writeString(tempPath, fileText);
        Graph<String> adjacencyGraph = new AdjacencyListGraph<>();
        assertThrows(IllegalArgumentException.class,
            () -> adjacencyGraph.readFromFile(tempPath, text -> text));
    }

    @Test
    void readGraphFromFileWhenGraphNotEmpty() {
        Graph<String> adjacencyGraph = new AdjacencyListGraph<>();
        adjacencyGraph.addVertex("B");
        Path path = Path.of("src/test/resources/graph.txt");
        assertThrows(IllegalStateException.class,
            () -> adjacencyGraph.readFromFile(path, text -> text));
        assertEquals(List.of("B"), adjacencyGraph.getVertices());
    }

    @Test
    void errorWhenReadingNonexistentFile() {
        Path tempPath = tempDir.resolve("missing-graph.txt");
        Graph<String> adjacencyGraph = new AdjacencyListGraph<>();
        assertThrows(IOException.class,
            () -> adjacencyGraph.readFromFile(tempPath, text -> text));
    }
}
