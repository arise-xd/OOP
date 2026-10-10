package ru.nsu.skovalev4.expression;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a mathematical expression.
 */
public abstract class Expression {

    /**
     * Creates an expression.
     */
    protected Expression() {
    }

    /**
     * Prints the expression to the console.
     */
    public final void print() {
        System.out.print(toString());
    }

    /**
     * Returns the expression as a string.
     *
     * @return string representation of the expression
     */
    @Override
    public abstract String toString();

    /**
     * Creates the derivative of the expression.
     *
     * @param variable variable used for differentiation
     * @return derivative of the expression
     */
    public abstract Expression derivative(String variable);

    /**
     * Evaluates the expression with the specified variable values.
     *
     * @param assignments variable assignments
     * @return value of the expression
     */
    public final int eval(String assignments) {
        Map<String, Integer> variables = new HashMap<>();

        if (!assignments.isBlank()) {
            String[] assignmentParts = assignments.split(";");

            for (String assignmentPart : assignmentParts) {
                String[] nameAndValue = assignmentPart.split("=");

                String name = nameAndValue[0].trim();
                int value = Integer.parseInt(nameAndValue[1].trim());

                variables.put(name, value);
            }
        }

        return eval(variables);
    }

    /**
     * Evaluates the expression using parsed variable values.
     *
     * @param variables variable values
     * @return value of the expression
     */
    protected abstract int eval(Map<String, Integer> variables);
}
