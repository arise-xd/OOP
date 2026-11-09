package ru.nsu.skovalev4.hashtable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class KeyValuePairTest {

    @Test
    void storesValuesAndSupportsObjectMethods() {
        KeyValuePair<String, Integer> pair = new KeyValuePair<>("one", 1);
        KeyValuePair<String, Integer> equalPair = new KeyValuePair<>("one", 1);
        KeyValuePair<String, Integer> differentKey = new KeyValuePair<>("two", 1);
        KeyValuePair<String, Integer> differentValue = new KeyValuePair<>("one", 2);
        KeyValuePair<String, Integer> nullPair = new KeyValuePair<>(null, null);

        assertAll(
            () -> assertEquals("one", pair.getKey()),
            () -> assertEquals(1, pair.getValue()),
            () -> assertEquals(pair, pair),
            () -> assertEquals(pair, equalPair),
            () -> assertEquals(pair.hashCode(), equalPair.hashCode()),
            () -> assertNotEquals(pair, differentKey),
            () -> assertNotEquals(pair, differentValue),
            () -> assertNotEquals(pair, null),
            () -> assertNotEquals(pair, "one=1"),
            () -> assertEquals("one=1", pair.toString()),
            () -> assertNull(nullPair.getKey()),
            () -> assertNull(nullPair.getValue()),
            () -> assertEquals("null=null", nullPair.toString())
        );
    }
}
