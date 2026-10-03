package ru.nsu.skovalev4.Task_1_1_3;

public class Mul extends Expression {

    private final Expression left;
    private final Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("*");
        right.print();
        System.out.print(")");
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(
            new Mul(left.derivative(variable), right),
            new Mul(left, right.derivative(variable))
        );
    }

    @Override
    public int eval(String assignments) {
        return left.eval(assignments) * right.eval(assignments);
    }
}
