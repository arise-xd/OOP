package ru.nsu.skovalev4.expression;

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
     * Prints the quotient to the console.
     */
    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("/");
        right.print();
        System.out.print(")");
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
     * @param assignments variable values
     * @return value of the quotient
     * @throws ArithmeticException if the denominator is zero
     */
    @Override
    public int eval(String assignments) {
        return left.eval(assignments) / right.eval(assignments);
    }
}
