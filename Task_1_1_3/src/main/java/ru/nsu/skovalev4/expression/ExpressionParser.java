package ru.nsu.skovalev4.expression;

/**
 * Represents the parser of expression.
 */
public class ExpressionParser {
    private final String input;
    private int position;

    /**
     * Creates a parser for string.
     *
     * @param input input string
     */
    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

    /**
     * Parse input expression.
     *
     * @return expression
     * @throws IllegalArgumentException if expression is invalid
     */
    public Expression parse() {
        Expression expression = parseExpression();
        skipWhitespace();
        if (position != input.length()) {
            throw new IllegalArgumentException(
                "End of the expression expected at " + position
            );
        } else {
            return expression;
        }
    }

    private Expression parseExpression() {
        skipWhitespace();

        Expression expression;

        if (position < input.length()
            && (Character.isDigit(input.charAt(position))
            || (input.charAt(position) == '-' && position + 1 < input.length()
            && Character.isDigit(input.charAt(position + 1))))) {
            expression = parseNumber();
        } else if (position < input.length()
            && Character.isLetter(input.charAt(position))) {
            expression = parseVariable();
        } else if (position < input.length()
            && input.charAt(position) == '(') {
            expression = parseOperation();
        } else {
            throw new IllegalArgumentException(
                "Expected expression at position " + position
            );
        }

        return expression;
    }

    private Expression parseNumber() {
        int startPosition = position;
        if (input.charAt(position) == '-') {
            position++;
        }
        while (position < input.length()
            && Character.isDigit(input.charAt(position))) {
            position++;
        }

        String numberText = input.substring(startPosition, position);
        int value = Integer.parseInt(numberText);

        return new Number(value);
    }

    private Expression parseVariable() {
        int startPosition = position;

        while (position < input.length()
            && Character.isLetterOrDigit(input.charAt(position))) {
            position++;
        }

        String variableName = input.substring(startPosition, position);

        return new Variable(variableName);
    }

    private Expression createOperation(char operator, Expression left, Expression right) {
        if (operator == '+') {
            return new Add(left, right);
        } else if (operator == '-') {
            return new Sub(left, right);
        } else if (operator == '/') {
            return new Div(left, right);
        } else if (operator == '*') {
            return new Mul(left, right);
        } else {
            throw new IllegalArgumentException(
                "Unknown operator provided: " + operator
            );
        }
    }

    private Expression parseOperation() {
        position++;
        skipWhitespace();
        Expression left = parseExpression();
        skipWhitespace();
        if (position >= input.length()) {
            throw new IllegalArgumentException(
                "Expected operator at " + position
            );
        }
        char operator = input.charAt(position);
        position++;
        skipWhitespace();
        Expression right = parseExpression();
        skipWhitespace();

        if (position >= input.length() || input.charAt(position) != ')') {
            throw new IllegalArgumentException(
                "Expected ')' at position " + position
            );
        } else {
            position++;
            return createOperation(operator, left, right);
        }
    }

    private void skipWhitespace() {
        while (position < input.length()
            && Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    }

}
