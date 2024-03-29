package tutorpro.model.person.student;

import static java.util.Objects.requireNonNull;

import tutorpro.commons.util.AppUtil;

/**
 * Represents a subject that a student is being taught in TutorPro.
 * Guarantees: immutable; is valid as declared in {@link #isValidSubject(String)}
 */
public class Subject {

    public static final String MESSAGE_CONSTRAINTS = "Subjects can take any values, and it should not be blank";
    // Possible grades: A, B2, C+, D-
    public static final String VALIDATION_REGEX = "\\w+(?: \\w+)*-\\S+"; //"[^\\s].*";


    private String value;
    private String grade;

    /**
     * Constructs a {@code Subject}
     *
     * @param subjectInput A valid subject.
     */
    public Subject(String subjectInput) {
        requireNonNull(subjectInput);
        AppUtil.checkArgument(isValidSubject(subjectInput), MESSAGE_CONSTRAINTS);
        String[] outputs = subjectInput.split("-");
        this.value = outputs[0];
        this.grade = outputs[1];
    }

    public String getValue() {
        return value;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Subject)) {
            return false;
        }
        Subject otherLevel = (Subject) other;
        return value.equals(otherLevel.value)
                && grade.equals(otherLevel.grade);
    }

    @Override
    public String toString() {
        return value + "-" + grade.toUpperCase();
    }

    /**
     * Returns true if the given String is a valid Subject.
     */
    public static boolean isValidSubject(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return value.hashCode() + grade.hashCode();
    }
}
