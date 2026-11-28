package ru.nsu.skovalev4.student;

/**
 * Represents numeric grades and pass or fail results.
 */
public enum Grade {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    UNSATISFACTORY(2),
    PASSED(null),
    FAILED(null);

    private final Integer value;

    Grade(Integer value) {
        this.value = value;
    }

    /**
     * Returns the numeric value of the grade.
     *
     * @return numeric value
     */
    public Integer getValue() {
        return value;
    }
}
