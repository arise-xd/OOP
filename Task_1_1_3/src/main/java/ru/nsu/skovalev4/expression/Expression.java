package ru.nsu.skovalev4.expression;

/**
 * Represents a mathematical expression.
 */
public abstract class Expression {

    /**
     * Creates an expression.
     */
    protected Expression() {
    }

    /**
     * Prints the expression to the console.
     */
    public abstract void print();

    /**
     * Creates the derivative of the expression.
     *
     * @param variable variable used for differentiation
     * @return derivative of the expression
     */
    public abstract Expression derivative(String variable);

    /**
     * Evaluates the expression with the specified variable values.
     *
     * @param assignments variable values
     * @return value of the expression
     */
    public abstract int eval(String assignments);
}
