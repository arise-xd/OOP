package ru.nsu.skovalev4.Task_1_1_3;

public class Number extends Expression {

    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int eval(String assignments) {
        return value;
    }
}
