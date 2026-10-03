package ru.nsu.skovalev4.Task_1_1_3;

public class Variable extends Expression {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.print(name);
    }

    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }


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
