package ru.nsu.skovalev4.sort;

import java.util.Arrays;

/**
 * Application entry point for testing heap sort.
 */
public class Main {
    /**
     * Runs the heap sort example.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int[] array = {5, 4, 3, 2, 1};

        HeapSort heapSort = new HeapSort();
        heapSort.sort(array);

        System.out.println(Arrays.toString(array));
    }
}