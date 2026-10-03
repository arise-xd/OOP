package ru.nsu.skovalev4.expression;

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
     * Prints the variable name to the console.
     */
    @Override
    public void print() {
        System.out.print(name);
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
     * @param assignments variable values
     * @return value assigned to this variable
     * @throws IllegalArgumentException if the variable has no assigned value
     */
    @Override
    public int eval(String assignments) {
        String[] assignmentParts = assignments.split(";");

        for (String assignmentPart : assignmentParts) {
            String[] nameAndValue = assignmentPart.split("=");

            String assignmentName = nameAndValue[0].trim();
            String assignmentValue = nameAndValue[1].trim();

            if (name.equals(assignmentName)) {
                return Integer.parseInt(assignmentValue);
            }
        }

        throw new IllegalArgumentException(
            "No value specified for variable: " + name
        );
    }
}
