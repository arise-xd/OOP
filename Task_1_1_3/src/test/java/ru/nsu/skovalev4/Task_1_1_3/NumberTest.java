package ru.nsu.skovalev4.Task_1_1_3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumberTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void printNumber() {
        Number number = new Number(5);

        number.print();

        assertEquals("5", output.toString());
    }

    @Test
    void evaluatesToItsValue() {
        Number number = new Number(5);

        int result = number.eval("");

        assertEquals(5, result);
    }

    @Test
    void derivativeIsZero() {
        Number number = new Number(5);

        Expression derivative = number.derivative("x");

        assertEquals(0, derivative.eval(""));
    }
}