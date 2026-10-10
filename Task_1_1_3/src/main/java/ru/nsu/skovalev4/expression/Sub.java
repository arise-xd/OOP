package ru.nsu.skovalev4.expression;

import java.util.Map;

/**
 * Represents the difference of two expressions.
 */
public class Sub extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Creates a difference of two expressions.
     *
     * @param left left expression
     * @param right right expression
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the difference as a string.
     *
     * @return string representation of the difference
     */
    @Override
    public String toString() {
        String result = '('
            + left.toString()
            + '-'
            + right.toString()
            + ')';
        return result;
    }

    /**
     * Creates the derivative of the difference.
     *
     * @param variable variable used for differentiation
     * @return derivative of the difference
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Evaluates the difference.
     *
     * @param variables variable values
     * @return value of the difference
     */
    @Override
    protected int eval(Map<String, Integer> variables) {
        return left.eval(variables) - right.eval(variables);
    }
}
