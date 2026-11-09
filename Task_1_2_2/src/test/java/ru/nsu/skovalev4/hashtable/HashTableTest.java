package ru.nsu.skovalev4.hashtable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HashTableTest {

    @Test
    void supportsBasicOperationsAndNulls() {
        HashTable<String, Number> table = new HashTable<>();

        assertAll(
            () -> assertTrue(table.isEmpty()),
            () -> assertEquals(0, table.size()),
            () -> assertFalse(table.containsKey("missing")),
            () -> assertThrows(NoSuchElementException.class, () -> table.get("missing"))
        );

        table.put("one", 1);
        table.put(null, 2);
        table.put("nullable", null);
        table.update("one", 1.5);

        assertAll(
            () -> assertFalse(table.isEmpty()),
            () -> assertEquals(3, table.size()),
            () -> assertEquals(1.5, table.get("one")),
            () -> assertEquals(2, table.get(null)),
            () -> assertNull(table.get("nullable")),
            () -> assertTrue(table.containsKey(null)),
            () -> assertThrows(IllegalArgumentException.class, () -> table.put("one", 10)),
            () -> assertThrows(NoSuchElementException.class,
                () -> table.update("missing", 10)),
            () -> assertThrows(NoSuchElementException.class,
                () -> table.remove("missing"))
        );

        assertEquals(2, table.remove(null));
        assertNull(table.remove("nullable"));
        assertAll(
            () -> assertEquals(1, table.size()),
            () -> assertFalse(table.containsKey(null)),
            () -> assertFalse(table.containsKey("nullable"))
        );
    }

    @Test
    void resolvesCollisionsAndRemovesEveryChainPosition() {
        HashTable<CollisionKey, String> table = new HashTable<>();
        CollisionKey first = new CollisionKey(1);
        CollisionKey second = new CollisionKey(2);
        CollisionKey third = new CollisionKey(3);
        CollisionKey fourth = new CollisionKey(4);

        table.put(first, "one");
        table.put(second, "two");
        table.put(third, "three");
        table.put(fourth, "four");

        assertAll(
            () -> assertEquals("one", table.get(first)),
            () -> assertEquals("two", table.remove(second)),
            () -> assertEquals("one", table.remove(first)),
            () -> assertEquals("four", table.remove(fourth)),
            () -> assertEquals("three", table.remove(third)),
            () -> assertTrue(table.isEmpty())
        );
    }

    @Test
    void resizesAndPreservesEveryElement() {
        HashTable<Integer, Integer> table = new HashTable<>();
        table.put(null, -1);

        for (int value = 0; value < 64; value++) {
            table.put(value, value * value);
        }

        assertEquals(65, table.size());
        assertEquals(-1, table.get(null));
        for (int value = 0; value < 64; value++) {
            assertEquals(value * value, table.get(value));
        }
    }

    @Test
    void iteratorTraversesElements() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(1, "one");
        table.put(17, "seventeen");
        table.put(null, "null-key");

        Set<KeyValuePair<Integer, String>> actualPairs = new HashSet<>();
        for (KeyValuePair<Integer, String> pair : table) {
            actualPairs.add(pair);
        }
        final Set<KeyValuePair<Integer, String>> expectedPairs = Set.of(
            new KeyValuePair<>(1, "one"),
            new KeyValuePair<>(17, "seventeen"),
            new KeyValuePair<>(null, "null-key")
        );

        Iterator<KeyValuePair<Integer, String>> updateIterator = table.iterator();
        table.update(1, "updated");
        assertDoesNotThrow(updateIterator::hasNext);

        Iterator<KeyValuePair<Integer, String>> putIterator = table.iterator();
        table.put(2, "two");
        assertThrows(ConcurrentModificationException.class, putIterator::hasNext);

        Iterator<KeyValuePair<Integer, String>> removeIterator = table.iterator();
        table.remove(2);
        assertThrows(ConcurrentModificationException.class, removeIterator::next);

        Iterator<KeyValuePair<Integer, String>> exhaustedIterator = table.iterator();
        while (exhaustedIterator.hasNext()) {
            exhaustedIterator.next();
        }

        Iterator<KeyValuePair<Integer, String>> emptyIterator =
            new HashTable<Integer, String>().iterator();

        assertAll(
            () -> assertEquals(expectedPairs, actualPairs),
            () -> assertFalse(exhaustedIterator.hasNext()),
            () -> assertThrows(NoSuchElementException.class, exhaustedIterator::next),
            () -> assertFalse(emptyIterator.hasNext()),
            () -> assertThrows(NoSuchElementException.class, emptyIterator::next)
        );
    }

    @Test
    void supportsEqualityHashCodeAndStringFormatting() {
        HashTable<Integer, String> first = new HashTable<>();
        first.put(1, "one");
        first.put(2, "two");
        first.put(null, "null-key");

        HashTable<Integer, String> equal = new HashTable<>();
        equal.put(null, "null-key");
        equal.put(2, "two");
        equal.put(1, "one");

        HashTable<Integer, String> differentValue = new HashTable<>();
        differentValue.put(1, "one");
        differentValue.put(2, "different");
        differentValue.put(null, "null-key");

        HashTable<Integer, String> differentSize = new HashTable<>();
        differentSize.put(1, "one");

        String text = first.toString();

        assertAll(
            () -> assertEquals(first, first),
            () -> assertEquals(first, equal),
            () -> assertEquals(equal, first),
            () -> assertEquals(first.hashCode(), equal.hashCode()),
            () -> assertNotEquals(first, differentValue),
            () -> assertNotEquals(first, differentSize),
            () -> assertNotEquals(first, null),
            () -> assertNotEquals(first, "not a table"),
            () -> assertTrue(text.startsWith("{")),
            () -> assertTrue(text.endsWith("}")),
            () -> assertTrue(text.contains("1=one")),
            () -> assertTrue(text.contains("2=two")),
            () -> assertTrue(text.contains("null=null-key")),
            () -> assertTrue(text.contains(", ")),
            () -> assertEquals("{}", new HashTable<>().toString())
        );
    }

    private static final class CollisionKey {

        private final int identifier;

        private CollisionKey(int identifier) {
            this.identifier = identifier;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof CollisionKey key && identifier == key.identifier;
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }
}
