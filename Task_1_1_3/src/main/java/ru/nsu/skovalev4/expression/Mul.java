package ru.nsu.skovalev4.expression;

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
     * Prints the product to the console.
     */
    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("*");
        right.print();
        System.out.print(")");
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
     * @param assignments variable values
     * @return value of the product
     */
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) * right.eval(assignments);
    }
}
