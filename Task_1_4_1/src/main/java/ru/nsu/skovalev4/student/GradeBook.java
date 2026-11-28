package ru.nsu.skovalev4.student;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores a student's grades and checks academic eligibility.
 */
public class GradeBook {
    private final List<Discipline> disciplines;
    private final StudyForm form;
    private int lastCompletedSemester;
    private final int semesterCount;
    private Grade thesisGrade;

    /**
     * Creates an empty grade book at the start of the program.
     *
     * @param form student's form of study
     * @param semesterCount total number of semesters in the program
     */
    public GradeBook(StudyForm form, int semesterCount) {
        this.disciplines = new ArrayList<>();
        this.form = form;
        this.lastCompletedSemester = 0;
        this.semesterCount = semesterCount;
        this.thesisGrade = null;
    }

    /**
     * Adds a discipline to the grade book.
     *
     * @param discipline discipline to add
     * @throws IllegalArgumentException if the semester counts differ
     */
    public void addDiscipline(Discipline discipline) {
        if (discipline.getSemesterCount() != semesterCount) {
            throw new IllegalArgumentException(
                "Discipline and grade book semester counts must match");
        }

        disciplines.add(discipline);
    }

    /**
     * Calculates the average of all recorded numeric grades.
     *
     * @return average grade
     */
    public Double getAverageGrade() {
        int gradeSum = 0;
        int gradeCount = 0;
        for (Discipline discipline : disciplines) {
            int semesterCount = discipline.getSemesterCount();
            for (int semester = 1; semester <= semesterCount; semester++) {
                AssessmentResult result = discipline.getResult(semester);
                if (result != null && result.grade().getValue() != null) {
                    gradeSum += result.grade().getValue();
                    gradeCount++;
                }
            }
        }

        if (gradeCount == 0) {
            return null;
        }
        return (double) gradeSum / gradeCount;
    }

    /**
     * Checks whether a paid student can transfer to a budget.
     *
     * @return true if the student has completed at least two semesters and has no
     *     satisfactory exam grades in the last two completed semesters
     */
    public boolean canTransferToBudget() {
        if (form == StudyForm.BUDGET || lastCompletedSemester <= 1) {
            return false;
        }

        for (Discipline discipline : disciplines) {
            for (
                int semester = lastCompletedSemester - 1;
                semester <= lastCompletedSemester;
                semester++
            ) {
                AssessmentResult result = discipline.getResult(semester);
                if (result != null
                    && result.type() == AssessmentType.EXAM
                    && result.grade() == Grade.SATISFACTORY
                ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks the honors diploma criteria using the latest numeric grade per discipline.
     *
     * @return true if at least 75% of those grades are excellent, none are satisfactory,
     *     and the thesis grade is either excellent or still pending
     */
    public boolean canGetHonorsDiploma() {
        int excellentCount = 0;
        int gradeCount = 0;
        for (Discipline discipline : disciplines) {
            AssessmentResult result = discipline.getLatestResult();
            if (result != null) {
                gradeCount++;
                if (result.grade() == Grade.EXCELLENT) {
                    excellentCount++;
                }
                if (result.grade() == Grade.SATISFACTORY) {
                    return false;
                }
            }

        }

        if (thesisGrade != null && thesisGrade != Grade.EXCELLENT) {
            return false;
        }
        if (gradeCount == 0) {
            return false;
        }
        return ((double) excellentCount / gradeCount) >= 0.75;
    }

    /**
     * Checks the scholarship criterion using grades from the first unfinished semester.
     *
     * @return true if that semester has at least one numeric grade and all its numeric
     *     grades are excellent
     */
    public boolean canGetIncreasedScholarship() {
        if (lastCompletedSemester >= semesterCount) {
            return false;
        }

        int currentSemester = lastCompletedSemester + 1;
        boolean hasExcellentGrade = false;
        for (Discipline discipline : disciplines) {
            AssessmentResult result = discipline.getResult(currentSemester);
            if (result == null || result.grade().getValue() == null) {
                continue;
            }
            if (result.grade() != Grade.EXCELLENT) {
                return false;
            }
            hasExcellentGrade = true;
        }
        return hasExcellentGrade;
    }

    /**
     * Returns the student's form of study.
     *
     * @return student's form of study
     */
    public StudyForm getStudyForm() {
        return form;
    }

    /**
     * Marks the next semester as completed.
     *
     * @throws IllegalStateException if all semesters are already completed
     */
    public void completeSemester() {
        if (lastCompletedSemester >= semesterCount) {
            throw new IllegalStateException("Not enough semesters");
        }
        lastCompletedSemester++;
    }

    /**
     * Sets the grade for the thesis defense.
     *
     * @param grade thesis grade
     */
    public void setThesisGrade(Grade grade) {
        thesisGrade = grade;
    }
}
