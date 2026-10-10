package ru.nsu.skovalev4.expression;

import java.util.Map;

/**
 * Represents a constant number.
 */
public class Number extends Expression {

    private final int value;

    /**
     * Creates a number with the specified value.
     *
     * @param value value of the number
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Returns the number as a string.
     *
     * @return string representation of the number
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * Creates the derivative of the number.
     *
     * @param variable variable used for differentiation
     * @return zero because the derivative of a number is zero
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * Returns the value of the number.
     *
     * @param variables variable values
     * @return value of the number
     */
    @Override
    protected int eval(Map<String, Integer> variables) {
        return value;
    }
}
