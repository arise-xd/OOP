package ru.nsu.skovalev4.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExpressionParserTest {
    @Test
    void parsesNumber() {
        String input = "67";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(67, expression.eval(""));
    }

    @Test
    void parsesNegativeNumber() {
        String input = "-67";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(-67, expression.eval(""));
    }

    @Test
    void parsesVariable() {
        String input = "var";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(25, expression.eval("var = 25"));
    }

    @Test
    void parsesNestedExpression() {
        String input = "(3+(2*x))";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(13, expression.eval("x = 5"));
    }

    @Test
    void parsesLeftNegativityNumberInExpression() {
        String input = "(-6+2)";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(-4, expression.eval(""));
    }

    @Test
    void twoMinusesEqualsToPlus() {
        String input = "(10--2)";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(12, expression.eval(""));
    }

    @Test
    void parsesExpressionWithWhitespace() {
        String input = "(   3    +  (  2 * x    )       )";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(13, expression.eval("x = 5"));
    }

    @Test
    void parsesAddition() {
        String input = "(8+2)";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(10, expression.eval(""));
    }

    @Test
    void parsesSubtraction() {
        String input = "(8-2)";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(6, expression.eval(""));
    }

    @Test
    void parsesDivision() {
        String input = "(8/2)";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(4, expression.eval(""));
    }

    @Test
    void parsesMultiplication() {
        String input = "(8*2)";
        ExpressionParser parser = new ExpressionParser(input);
        Expression expression = parser.parse();
        assertEquals(16, expression.eval(""));
    }

    @Test
    void rejectsEmptyExpression() {
        String input = "";
        ExpressionParser parser = new ExpressionParser(input);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse()
        );

        assertEquals("Expected expression at position 0", exception.getMessage());
    }

    @Test
    void rejectsMissingClosingParenthesis() {
        String input = "(5+2";
        ExpressionParser parser = new ExpressionParser(input);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse()
        );

        assertEquals("Expected ')' at position 4", exception.getMessage());
    }

    @Test
    void rejectsUnknownOperator() {
        String input = "(5$2)";
        ExpressionParser parser = new ExpressionParser(input);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse()
        );

        assertEquals("Unknown operator provided: $", exception.getMessage());
    }

    @Test
    void rejectsMissingOperator() {
        String input = "(5";
        ExpressionParser parser = new ExpressionParser(input);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse()
        );

        assertEquals("Expected operator at 2", exception.getMessage());
    }

    @Test
    void rejectsTrailingCharacters() {
        String input = "(5+2)sss";
        ExpressionParser parser = new ExpressionParser(input);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse()
        );

        assertEquals("End of the expression expected at 5", exception.getMessage());
    }

    @Test
    void rejectsStandaloneMinus() {
        String input = "-";
        ExpressionParser parser = new ExpressionParser(input);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse()
        );

        assertEquals("Expected expression at position 0", exception.getMessage());
    }

    @Test
    void rejectsMinusSeparatedFromNumber() {
        String input = "- 5";
        ExpressionParser parser = new ExpressionParser(input);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> parser.parse()
        );

        assertEquals("Expected expression at position 0", exception.getMessage());
    }
}
