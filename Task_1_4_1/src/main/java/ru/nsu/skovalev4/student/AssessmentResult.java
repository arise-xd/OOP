package ru.nsu.skovalev4.student;

/**
 * Stores the type and grade of a single assessment.
 *
 * @param type assessment type
 * @param grade grade received for the assessment
 */
public record AssessmentResult(AssessmentType type, Grade grade) {
}
