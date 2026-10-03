package ru.nsu.skovalev4.Task_1_1_3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubTest {
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
    void printsSubtraction() {
        Sub sub = new Sub(new Number(3), new Variable("x"));

        sub.print();

        assertEquals("(3-x)", output.toString());
    }

    @Test
    void printsRecurrentSubtraction() {
        Sub sub = new Sub(
            new Sub(new Number(3), new Variable("x")),
            new Number(7)
        );

        sub.print();

        assertEquals("((3-x)-7)", output.toString());
    }

    @Test
    void evaluatesSubtraction() {
        Sub sub = new Sub(new Number(3), new Variable("x"));

        int subtractionResult = sub.eval("x = 10");

        assertEquals(-7, subtractionResult);
    }

    @Test
    void differentiatesSubtraction() {
        Sub sub = new Sub(new Variable("x"), new Variable("x"));

        Expression derivativeResult = sub.derivative("x");

        assertEquals(0, derivativeResult.eval(""));
    }

}