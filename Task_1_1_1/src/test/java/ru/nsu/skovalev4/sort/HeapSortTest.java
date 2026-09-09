package ru.nsu.skovalev4.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HeapSortTest {

    @Test
    void sortReverseArray() {
        int[] array = {5, 4, 3, 2, 1};

        HeapSort heapSort = new HeapSort();
        heapSort.sort(array);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void sortEmptyArray() {
        int[] array = {};

        HeapSort heapSort = new HeapSort();
        heapSort.sort(array);

        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void sortSortedArray() {
        int[] array = {1, 2, 3, 4, 5};

        HeapSort heapSort = new HeapSort();
        heapSort.sort(array);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void sortEqualNumbersArray() {
        int[] array = {1, 1, 1, 1, 1};

        HeapSort heapSort = new HeapSort();
        heapSort.sort(array);

        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, array);
    }

    @Test
    void sortNegativeArray() {
        int[] array = {-124, -1, -7, -421, 0};

        HeapSort heapSort = new HeapSort();
        heapSort.sort(array);

        assertArrayEquals(new int[]{-421, -124, -7, -1, 0}, array);
    }

    @Test
    void heapify() {
    }
}