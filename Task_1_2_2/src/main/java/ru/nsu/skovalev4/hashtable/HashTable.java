package ru.nsu.skovalev4.hashtable;

import java.util.NoSuchElementException;
import java.util.Objects;

public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private Node<K, V>[] buckets;
    private int size;

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }

    @SuppressWarnings("unchecked")
    public HashTable() {
        this.buckets = new Node[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int bucketIndex(K key) {

        if (key == null) {
            return 0;
        }

        int keyHash = key.hashCode();
        keyHash = keyHash ^ (keyHash >>> 16);
        int index = keyHash & (buckets.length - 1);

        return index;
    }

    public void put(K key, V value) {
        int index = bucketIndex(key);

        if (findNode(key) != null) {
            throw new IllegalArgumentException("Key already exists");
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
    }

    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    public V get(K key) {
        Node<K, V> node = findNode(key);
        if (node == null) {
            throw new NoSuchElementException("Key doesn't exist");
        }
        return node.value;
    }

    public void update(K key, V value) {
        Node<K, V> node = findNode(key);
        if (node == null) {
            throw new NoSuchElementException("Key doesn't exist");
        }

        node.value = value;
    }

    public V remove(K key) {
        int index = bucketIndex(key);
        Node<K, V> previous = null;
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return current.value;
            }

            previous = current;
            current = current.next;
        }

        throw new NoSuchElementException("Key doesn't exist");
    }

    private Node<K, V> findNode(K key) {

        int index = bucketIndex(key);
        Node<K, V> current = buckets[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current;
            }
            current = current.next;
        }

        return null;

    }
}
