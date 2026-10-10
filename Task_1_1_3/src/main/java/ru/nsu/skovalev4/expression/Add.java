package ru.nsu.skovalev4.expression;

import java.util.Map;

/**
 * Represents the sum of two expressions.
 */
public class Add extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Creates a sum of two expressions.
     *
     * @param left left expression
     * @param right right expression
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the sum as a string.
     *
     * @return string representation of the sum
     */
    @Override
    public String toString() {
        String result = '('
            + left.toString()
            + '+'
            + right.toString()
            + ')';
        return result;
    }

    /**
     * Creates the derivative of the sum.
     *
     * @param variable variable used for differentiation
     * @return derivative of the sum
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Evaluates the sum.
     *
     * @param variables variable values
     * @return value of the sum
     */
    @Override
    protected int eval(Map<String, Integer> variables) {
        return left.eval(variables) + right.eval(variables);
    }
}
