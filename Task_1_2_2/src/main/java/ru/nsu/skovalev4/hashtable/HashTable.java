package ru.nsu.skovalev4.hashtable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Stores key-value pairs in a hash table with separate chaining for collisions.
 * The table supports null keys and values, dynamic resizing, and iteration.
 *
 * @param <K> type of keys
 * @param <V> type of values
 */
public class HashTable<K, V>
    implements Iterable<KeyValuePair<K, V>> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private Node<K, V>[] buckets;
    private int size;
    private int modificationCount = 0;

    private static class Node<K, V> {

        private final K key;
        private V value;
        private Node<K, V> next;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Creates an empty hash table with the default capacity.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.buckets = new Node[DEFAULT_CAPACITY];
    }

    /**
     * Returns the number of key-value pairs stored in the table.
     *
     * @return number of stored pairs
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether the table contains no key-value pairs.
     *
     * @return true if the table is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private int bucketIndex(Object key) {

        if (key == null) {
            return 0;
        }

        int keyHash = key.hashCode();
        keyHash = keyHash ^ (keyHash >>> 16);
        int index = keyHash & (buckets.length - 1);

        return index;
    }

    /**
     * Adds a new key-value pair to the table.
     *
     * @param key key to add
     * @param value value associated with the key
     * @throws IllegalArgumentException if the key already exists
     */
    public void put(K key, V value) {

        if (findNode(key) != null) {
            throw new IllegalArgumentException("Key already exists");
        }

        if (size + 1 > buckets.length * LOAD_FACTOR) {
            resize();
        }

        int index = bucketIndex(key);
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
        modificationCount++;
    }

    /**
     * Checks whether the table contains the specified key.
     *
     * @param key key to find
     * @return true if the key exists, false otherwise
     */
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    /**
     * Returns the value associated with the specified key.
     *
     * @param key key whose value is required
     * @return value associated with the key
     * @throws NoSuchElementException if the key does not exist
     */
    public V get(K key) {
        Node<K, V> node = findNode(key);
        if (node == null) {
            throw new NoSuchElementException("Key doesn't exist");
        }
        return node.value;
    }

    /**
     * Replaces the value associated with an existing key.
     *
     * @param key key whose value should be replaced
     * @param value new value associated with the key
     * @throws NoSuchElementException if the key does not exist
     */
    public void update(K key, V value) {
        Node<K, V> node = findNode(key);
        if (node == null) {
            throw new NoSuchElementException("Key doesn't exist");
        }

        node.value = value;
    }

    /**
     * Removes the key-value pair associated with the specified key.
     *
     * @param key key to remove
     * @return value previously associated with the key
     * @throws NoSuchElementException if the key does not exist
     */
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
                modificationCount++;
                return current.value;
            }

            previous = current;
            current = current.next;
        }

        throw new NoSuchElementException("Key doesn't exist");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof HashTable<?, ?> otherTable)) {
            return false;
        }

        if (size != otherTable.size) {
            return false;
        }

        for (KeyValuePair<K, V> pair : this) {
            Node<?, ?> otherNode = otherTable.findNode(pair.getKey());

            if (otherNode == null
                || !Objects.equals(pair.getValue(), otherNode.value)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (KeyValuePair<K, V> pair : this) {
            hash += pair.hashCode();
        }

        return hash;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        boolean first = true;

        for (KeyValuePair<K, V> pair : this) {

            if (!first) {
                builder.append(", ");
            }

            builder.append(pair);
            first = false;
        }
        builder.append("}");
        return builder.toString();
    }

    private Node<K, V> findNode(Object key) {

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

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        buckets = new Node[oldBuckets.length * 2];

        for (Node<K, V> oldBucket : oldBuckets) {
            Node<K, V> current = oldBucket;

            while (current != null) {
                Node<K, V> next = current.next;

                int newIndex = bucketIndex(current.key);
                current.next = buckets[newIndex];
                buckets[newIndex] = current;

                current = next;
            }
        }
    }

    private class HashTableIterator
        implements Iterator<KeyValuePair<K, V>> {

        private int nextBucketIndex;
        private Node<K, V> nextNode;
        private final int iteratorModificationCount;

        private HashTableIterator() {
            iteratorModificationCount = modificationCount;
            moveToNextBucket();
        }

        @Override
        public boolean hasNext() {
            checkConcurrentModification();
            return nextNode != null;
        }

        @Override
        public KeyValuePair<K, V> next() {
            checkConcurrentModification();
            if (nextNode == null) {
                throw new NoSuchElementException("No more elements");
            }

            Node<K, V> nodeToReturn = nextNode;
            nextNode = nextNode.next;
            if (nextNode == null) {
                moveToNextBucket();
            }

            return new KeyValuePair<>(nodeToReturn.key, nodeToReturn.value);
        }

        private void checkConcurrentModification() {
            if (iteratorModificationCount != modificationCount) {
                throw new ConcurrentModificationException();
            }
        }

        private void moveToNextBucket() {
            while (nextNode == null && nextBucketIndex < buckets.length) {
                nextNode = buckets[nextBucketIndex];
                nextBucketIndex++;
            }
        }
    }

    /**
     * Returns an iterator over the key-value pairs in the table.
     *
     * @return iterator over the stored pairs
     */
    @Override
    public Iterator<KeyValuePair<K, V>> iterator() {
        return new HashTableIterator();
    }
}
