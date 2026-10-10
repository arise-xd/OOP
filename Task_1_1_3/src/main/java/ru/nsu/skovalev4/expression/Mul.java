package ru.nsu.skovalev4.expression;

import java.util.Map;

/**
 * Represents the product of two expressions.
 */
public class Mul extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Creates a product of two expressions.
     *
     * @param left left expression
     * @param right right expression
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the product as a string.
     *
     * @return string representation of the product
     */
    @Override
    public String toString() {
        String result = '('
            + left.toString()
            + '*'
            + right.toString()
            + ')';
        return result;
    }

    /**
     * Creates the derivative of the product.
     *
     * @param variable variable used for differentiation
     * @return derivative of the product
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(
            new Mul(left.derivative(variable), right),
            new Mul(left, right.derivative(variable))
        );
    }

    /**
     * Evaluates the product.
     *
     * @param variables variable values
     * @return value of the product
     */
    @Override
    protected int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }
}
