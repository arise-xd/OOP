package ru.nsu.skovalev4.substring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class SubstringTest {
    @TempDir
    Path tempDir;

    private Path writeFile(String text) throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, text, StandardCharsets.UTF_8);
        return file;
    }

    private static Stream<Arguments> searchCases() {
        return Stream.of(
                Arguments.of("абракадабра", "бра", List.of(1L, 8L)),
                Arguments.of("aaaa", "aa", List.of(0L, 1L, 2L)),
                Arguments.of("abc---abc", "abc", List.of(0L, 6L)),
                Arguments.of("AAABA", "AAABA", List.of(0L)),
                Arguments.of("ABABABACA", "ABACA", List.of(4L)),
                Arguments.of("abcdef", "xyz", List.<Long>of()),
                Arguments.of("", "abc", List.<Long>of()),
                Arguments.of("ab", "abc", List.<Long>of()),
                Arguments.of("aba", "a", List.of(0L, 2L))
        );
    }

    @ParameterizedTest(name = "[{index}] text={0}, pattern={1}, expected={2}")
    @MethodSource("searchCases")
    void findsOccurrences(String text, String pattern, List<Long> expected) throws IOException {
        Path file = writeFile(text);
        Substring finder = new Substring(pattern);

        var result = finder.find(file);

        assertEquals(expected, result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void rejectsInvalidPattern(String pattern) {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Substring(pattern)
        );
    }

    @Test
    void throwsForMissingFile() {
        Path file = tempDir.resolve("missing.txt");
        Substring finder = new Substring("abc");
        assertThrows(
            IOException.class,
            () -> finder.find(file)
        );
    }

    @Test
    void findsPatternInGeneratedLargeFile() throws IOException {
        Path file = tempDir.resolve("large.txt");
        String block = "x".repeat(8192);
        int blockCount = 8192;

        try (var writer =
                 Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {

            for (int i = 0; i < blockCount; i++) {
                writer.write(block);
            }

            writer.write("lol");

            for (int i = 0; i < blockCount; i++) {
                writer.write(block);
            }
        }
        long expectedPosition = (long) block.length() * blockCount;
        Substring finder = new Substring("lol");
        var result = finder.find(file);

        assertEquals(List.of(expectedPosition), result);
    }
}
