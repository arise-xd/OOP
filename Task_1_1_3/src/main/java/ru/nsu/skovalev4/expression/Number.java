package ru.nsu.skovalev4.expression;

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
     * Prints the number to the console.
     */
    @Override
    public void print() {
        System.out.print(value);
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
     * @param assignments variable values
     * @return value of the number
     */
    @Override
    public int eval(String assignments) {
        return value;
    }
}
