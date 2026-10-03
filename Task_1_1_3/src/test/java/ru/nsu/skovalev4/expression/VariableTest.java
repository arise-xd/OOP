package ru.nsu.skovalev4.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VariableTest {
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
    void printsName() {
        Variable variable = new Variable("x");

        variable.print();

        assertEquals("x", output.toString());
    }

    @Test
    void derivativeBySameVariable() {
        Variable variable = new Variable("x");

        Expression derivative = variable.derivative("x");

        assertEquals(1, derivative.eval(""));
    }

    @Test
    void derivativeByDifferentVariableIsZero() {
        Variable variable = new Variable("x");

        Expression derivative = variable.derivative("y");

        assertEquals(0, derivative.eval(""));
    }

    @Test
    void evaluatesAssignedValue() {
        Variable variable = new Variable("x");

        int valueResult = variable.eval("x = 777; y = 2");

        assertEquals(777, valueResult);
    }

    @Test
    void throwsExceptionWhenVariableIsMissing() {
        Variable variable = new Variable("x");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> variable.eval("z = 777; y = 2")
        );

        assertEquals("No value specified for variable: x", exception.getMessage());
    }

    @Test
    void evaluatesVariableWithMultiletterName() {
        Variable variable = new Variable("temperature");

        int result = variable.eval("x = 10; temperature = 25");

        assertEquals(25, result);
    }
}