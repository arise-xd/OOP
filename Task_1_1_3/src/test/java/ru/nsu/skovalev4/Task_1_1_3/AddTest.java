package ru.nsu.skovalev4.Task_1_1_3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddTest {
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
    void printsAddition() {
        Add add = new Add(new Number(3), new Variable("x"));

        add.print();

        assertEquals("(3+x)", output.toString());
    }

    @Test
    void printsRecurrentAddition() {
        Add add = new Add(
            new Add(new Number(3), new Variable("x")),
            new Number(7)
        );

        add.print();

        assertEquals("((3+x)+7)", output.toString());
    }

    @Test
    void evaluatesAddition() {
        Add add = new Add(new Number(3), new Variable("x"));

        int additionResult = add.eval("x = 10");

        assertEquals(13, additionResult);
    }

    @Test
    void differentiatesAddition() {
        Add add = new Add(new Variable("x"), new Variable("x"));

        Expression derivativeResult = add.derivative("x");

        assertEquals(2, derivativeResult.eval(""));
    }

}