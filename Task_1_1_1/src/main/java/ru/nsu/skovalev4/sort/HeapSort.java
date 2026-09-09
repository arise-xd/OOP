package ru.nsu.skovalev4.sort;

/**
 * Provides heap sort implementation.
 */
public class HeapSort {
    /**
     * Sorts an array using the heap sort algorithm.
     *
     * @param array array to sort
     */
    public void sort(int[] array) {
        int arrayLenght = array.length;

        for (int i = arrayLenght / 2 - 1; i >= 0; i--){
            heapify(array, arrayLenght, i);
        }

        for (int i = arrayLenght - 1; i > 0; i--){
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }
        return;
    }

    void heapify(int[] array, int arrayLength, int index) {
        while (true) {
            int largest = index;

            int left = 2 * index + 1;
            int right = 2 * index + 2;

            if (left < arrayLength && array[left] > array[largest]) {
                largest = left;
            }

            if (right < arrayLength && array[right] > array[largest]) {
                largest = right;
            }

            if (largest == index) {
                break;
            }

            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;

            index = largest;
        }
    }
}
