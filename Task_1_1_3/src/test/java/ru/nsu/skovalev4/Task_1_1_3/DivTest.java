package ru.nsu.skovalev4.Task_1_1_3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DivTest {
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
    void printsDivision() {
        Div div = new Div(new Number(3), new Variable("x"));

        div.print();

        assertEquals("(3/x)", output.toString());
    }

    @Test
    void printsRecurrentDivision() {
        Div div = new Div(
            new Div(new Number(3), new Variable("x")),
            new Number(7)
        );

        div.print();

        assertEquals("((3/x)/7)", output.toString());
    }

    @Test
    void evaluatesDivision() {
        Div div = new Div(new Number(10), new Variable("x"));

        int divisionResult = div.eval("x = 2");

        assertEquals(5, divisionResult);
    }

    @Test
    void differentiatesDivision() {
        Div div = new Div(
            new Mul(new Variable("x"), new Variable("x")),
            new Variable("x")
        );

        Expression derivativeResult = div.derivative("x");

        assertEquals(1, derivativeResult.eval("x = 5"));
    }

    @Test
    void throwsWhenDividingByZero() {
        Div div = new Div(new Number(10), new Number(0));

        assertThrows(ArithmeticException.class, () -> div.eval(""));
    }

}