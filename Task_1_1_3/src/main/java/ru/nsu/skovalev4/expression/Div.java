package ru.nsu.skovalev4.expression;

import java.util.Map;

/**
 * Represents the quotient of two expressions.
 */
public class Div extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Creates a quotient of two expressions.
     *
     * @param left numerator
     * @param right denominator
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the quotient as a string.
     *
     * @return string representation of the quotient
     */
    @Override
    public String toString() {
        String result = '('
            + left.toString()
            + '/'
            + right.toString()
            + ')';
        return result;
    }

    /**
     * Creates the derivative of the quotient.
     *
     * @param variable variable used for differentiation
     * @return derivative of the quotient
     */
    @Override
    public Expression derivative(String variable) {
        return new Div(
            new Sub(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
            ),
            new Mul(right, right)
        );
    }

    /**
     * Evaluates the quotient.
     *
     * @param variables variable values
     * @return value of the quotient
     * @throws ArithmeticException if the denominator is zero
     */
    @Override
    protected int eval(Map<String, Integer> variables) {
        return left.eval(variables) / right.eval(variables);
    }
}
