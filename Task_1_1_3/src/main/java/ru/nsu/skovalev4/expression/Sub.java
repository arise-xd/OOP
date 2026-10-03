package ru.nsu.skovalev4.expression;

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
     * Prints the difference to the console.
     */
    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("-");
        right.print();
        System.out.print(")");
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
     * @param assignments variable values
     * @return value of the difference
     */
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) - right.eval(assignments);
    }
}
