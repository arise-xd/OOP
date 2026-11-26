package ru.nsu.skovalev4.substring;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Finds all occurrences of a pattern in file.
 */
public class Substring {
    private final int[] lps;
    private final String pattern;

    /**
     * Creates a finder for the given pattern.
     *
     * @param pattern substring to search for
     * @throws IllegalArgumentException if the pattern is null or empty
     */
    public Substring(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern must not be empty");
        }

        this.pattern = pattern;
        lps = new int[pattern.length()];
        createLps();
    }

    /**
     * Builds the array of longest proper prefix lengths that also match suffixes.
     */
    private void createLps() {
        int len = 0;
        int i = 1;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }

    /**
     * Finds all occurrences of the pattern.
     *
     * @param path path to the file
     * @return starting positions of all occurrences in ascending order
     * @throws IOException if the file cannot be opened or read
     */
    public ArrayList<Long> find(Path path) throws IOException {
        ArrayList<Long> result = new ArrayList<>();

        try (BufferedReader reader =
                 Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            int current;
            long position = 0;
            int j = 0;

            while ((current = reader.read()) != -1) {
                char symbol = (char) current;
                while (j > 0 && symbol != pattern.charAt(j)) {
                    j = lps[j - 1];
                }

                if (symbol == pattern.charAt(j)) {
                    j++;
                }

                if (j == pattern.length()) {
                    result.add(position - pattern.length() + 1);
                    j = lps[j - 1];
                }

                position++;
            }
        }

        return result;
    }
}
