package ru.nsu.skovalev4.hashtable;

import java.util.Objects;

/**
 * Represents an immutable key-value pair returned by a hash table iterator.
 *
 * @param <K> type of the key
 * @param <V> type of the value
 */
public final class KeyValuePair<K, V> {

    private final K key;
    private final V value;

    /**
     * Creates a key-value pair.
     *
     * @param key pair key
     * @param value pair value
     */
    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key.
     *
     * @return pair key
     */
    public K getKey() {
        return this.key;
    }

    /**
     * Returns the value.
     *
     * @return pair value
     */
    public V getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof KeyValuePair<?, ?> pair)) {
            return false;
        }

        return Objects.equals(key, pair.key)
            && Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return String.valueOf(key) + '=' + String.valueOf(value);
    }
}
