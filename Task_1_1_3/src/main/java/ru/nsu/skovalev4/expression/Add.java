package ru.nsu.skovalev4.expression;

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
     * Prints the sum to the console.
     */
    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("+");
        right.print();
        System.out.print(")");
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
     * @param assignments variable values
     * @return value of the sum
     */
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) + right.eval(assignments);
    }
}
