package ru.nsu.skovalev4.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GradeBookTest {

    @Test
    void checkAverageAndScholarship() {
        GradeBook book = new GradeBook(StudyForm.BUDGET, 2);
        Discipline oop = new Discipline("OOP", 2);
        Discipline operatingSystems = new Discipline("Operating systems", 2);
        Discipline english = new Discipline("English", 2);
        book.addDiscipline(oop);
        book.addDiscipline(operatingSystems);
        book.addDiscipline(english);

        english.setResult(1,
            new AssessmentResult(AssessmentType.PASS_FAIL_ASSESSMENT, Grade.PASSED));
        assertNull(book.getAverageGrade());
        assertFalse(book.canGetIncreasedScholarship());

        oop.setResult(1, exam(Grade.EXCELLENT));
        assertTrue(book.canGetIncreasedScholarship());

        operatingSystems.setResult(1, exam(Grade.GOOD));
        assertEquals(4.5, book.getAverageGrade());
        assertFalse(book.canGetIncreasedScholarship());

        book.completeSemester();
        assertFalse(book.canGetIncreasedScholarship());
        oop.setResult(2, exam(Grade.EXCELLENT));
        assertTrue(book.canGetIncreasedScholarship());
        assertEquals(14.0 / 3.0, book.getAverageGrade());

        book.completeSemester();
        assertFalse(book.canGetIncreasedScholarship());
        assertThrows(IllegalStateException.class, book::completeSemester);
    }

    @Test
    void usesTheLastTwoCompletedSessionsForBudgetTransfer() {
        GradeBook book = new GradeBook(StudyForm.PAID, 4);
        Discipline oop = new Discipline("OOP", 4);
        book.addDiscipline(oop);
        oop.setResult(1, exam(Grade.SATISFACTORY));
        oop.setResult(2, new AssessmentResult(AssessmentType.GRADED_CREDIT, Grade.SATISFACTORY));
        oop.setResult(3, exam(Grade.EXCELLENT));
        oop.setResult(4, exam(Grade.SATISFACTORY));

        book.completeSemester();
        assertFalse(book.canTransferToBudget());
        book.completeSemester();
        assertFalse(book.canTransferToBudget());
        book.completeSemester();
        assertTrue(book.canTransferToBudget());

        oop.setResult(2, exam(Grade.SATISFACTORY));
        assertFalse(book.canTransferToBudget());
        oop.setResult(2, null);
        assertTrue(book.canTransferToBudget());

        book.completeSemester();
        assertFalse(book.canTransferToBudget());
    }

    @Test
    void evaluatesHonorsDiploma() {
        GradeBook book = new GradeBook(StudyForm.PAID, 3);
        Discipline oop = new Discipline("OOP", 3);
        Discipline operatingSystems = new Discipline("Operating systems", 3);
        Discipline algorithms = new Discipline("Algorithms", 3);
        Discipline probability = new Discipline("Probability", 3);
        Discipline english = new Discipline("English", 3);
        book.addDiscipline(oop);
        book.addDiscipline(operatingSystems);
        book.addDiscipline(algorithms);
        book.addDiscipline(probability);
        book.addDiscipline(english);

        english.setResult(1,
            new AssessmentResult(AssessmentType.PASS_FAIL_ASSESSMENT, Grade.FAILED));
        assertFalse(book.canGetHonorsDiploma());

        oop.setResult(1, exam(Grade.SATISFACTORY));
        oop.setResult(2, exam(Grade.EXCELLENT));
        oop.setResult(3, new AssessmentResult(AssessmentType.PASS_FAIL_ASSESSMENT, Grade.PASSED));
        operatingSystems.setResult(1, exam(Grade.EXCELLENT));
        algorithms.setResult(1, exam(Grade.GOOD));
        probability.setResult(1, exam(Grade.GOOD));
        assertFalse(book.canGetHonorsDiploma());

        algorithms.setResult(2, exam(Grade.EXCELLENT));
        assertTrue(book.canGetHonorsDiploma());

        probability.setResult(2,
            new AssessmentResult(AssessmentType.GRADED_CREDIT, Grade.SATISFACTORY));
        assertFalse(book.canGetHonorsDiploma());
        probability.setResult(2, exam(Grade.GOOD));

        book.setThesisGrade(Grade.GOOD);
        assertFalse(book.canGetHonorsDiploma());
        book.setThesisGrade(Grade.EXCELLENT);
        assertTrue(book.canGetHonorsDiploma());
    }

    @Test
    void keepsAnAlreadyBudgetStudentOnBudget() {
        GradeBook book = new GradeBook(StudyForm.BUDGET, 2);
        assertThrows(IllegalArgumentException.class,
            () -> book.addDiscipline(new Discipline("OOP", 1)));

        Discipline oop = new Discipline("OOP", 2);
        oop.setResult(1, exam(Grade.EXCELLENT));
        oop.setResult(2, exam(Grade.EXCELLENT));
        book.addDiscipline(oop);
        book.completeSemester();
        book.completeSemester();

        assertEquals(StudyForm.BUDGET, book.getStudyForm());
        assertFalse(book.canTransferToBudget());
    }

    private static AssessmentResult exam(Grade grade) {
        return new AssessmentResult(AssessmentType.EXAM, grade);
    }
}
