package ru.nsu.skovalev4.student;

/**
 * Stores one assessment result per semester for a discipline.
 */
public class Discipline {
    private final String disciplineName;
    private final AssessmentResult[] results;

    /**
     * Creates a discipline with empty results for the whole program.
     *
     * @param name discipline name
     * @param semesterAmount total number of semesters in the program
     */
    public Discipline(String name, int semesterAmount) {
        this.disciplineName = name;
        this.results = new AssessmentResult[semesterAmount];
    }

    /**
     * Sets the result for the given semester.
     *
     * @param semester semester number starting at 1
     * @param result assessment result
     * @throws IndexOutOfBoundsException if the semester number is out of range
     */
    public void setResult(int semester, AssessmentResult result) {
        results[semester - 1] = result;
    }

    /**
     * Returns the result for the given semester.
     *
     * @param semester semester number starting at 1
     * @return assessment result
     * @throws IndexOutOfBoundsException if the semester number is out of range
     */
    public AssessmentResult getResult(int semester) {
        return results[semester - 1];
    }

    /**
     * Returns the total number of semesters in the program.
     *
     * @return number of semesters
     */
    public int getSemesterCount() {
        return results.length;
    }

    /**
     * Finds the numeric assessment result from the latest semester that has one.
     *
     * @return latest numeric assessment result
     */
    public AssessmentResult getLatestResult() {
        for (int semester = results.length; semester > 0; semester--) {
            AssessmentResult result = results[semester - 1];
            if (result != null
                && result.grade() != Grade.PASSED
                && result.grade() != Grade.FAILED) {
                return result;
            }
        }
        return null;
    }

}
