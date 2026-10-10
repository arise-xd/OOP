package ru.nsu.skovalev4.expression;

import java.util.Map;

/**
 * Represents a variable in an expression.
 */
public class Variable extends Expression {

    private final String name;

    /**
     * Creates a variable with the specified name.
     *
     * @param name name of the variable
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Returns the variable name as a string.
     *
     * @return string representation of the variable
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Creates the derivative of the variable.
     *
     * @param variable variable used for differentiation
     * @return one for the same variable, otherwise zero
     */
    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Finds and returns the value assigned to the variable.
     *
     * @param variables variable values
     * @return value assigned to this variable
     * @throws IllegalArgumentException if the variable has no assigned value
     */
    @Override
    protected int eval(Map<String, Integer> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }

        throw new IllegalArgumentException(
            "No value specified for variable: " + name
        );
    }
}
