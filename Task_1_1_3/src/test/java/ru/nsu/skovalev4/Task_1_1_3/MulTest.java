package ru.nsu.skovalev4.Task_1_1_3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MulTest {
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
    void printsMultiplication() {
        Mul mul = new Mul(new Number(3), new Variable("x"));

        mul.print();

        assertEquals("(3*x)", output.toString());
    }

    @Test
    void printsRecurrentMultiplication() {
        Mul mul = new Mul(
            new Mul(new Number(3), new Variable("x")),
            new Number(7)
        );

        mul.print();

        assertEquals("((3*x)*7)", output.toString());
    }

    @Test
    void evaluatesMultiplication() {
        Mul mul = new Mul(new Number(3), new Variable("x"));

        int multiplicationResult = mul.eval("x = 10");

        assertEquals(30, multiplicationResult);
    }

    @Test
    void differentiatesMultiplication() {
        Mul mul = new Mul(new Variable("x"), new Variable("x"));

        Expression derivativeResult = mul.derivative("x");

        assertEquals(10, derivativeResult.eval("x = 5"));
    }

}