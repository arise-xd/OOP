package ru.nsu.skovalev4.Task_1_1_3;

public abstract class Expression {

    public abstract void print();

    public abstract Expression derivative(String variable);

    public abstract int eval(String assignments);
}
