package tutorpro.model.person.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tutorpro.testutil.Assert;

public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid addresses
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only

        // valid addresses
        assertTrue(Subject.isValidSubject("Math-B"));
        assertTrue(Subject.isValidSubject("b-c")); // one character
    }

    @Test
    public void getGrade_checkCorrectGrade() {
        Subject subject = new Subject("Math-B");
        Assertions.assertEquals("B", subject.getGrade());
    }

    @Test
    public void equals() {
        Subject subject = new Subject("Valid Subject-A");

        // same values -> returns true
        assertTrue(subject.equals(new Subject("Valid Subject-A")));

        // same object -> returns true
        assertTrue(subject.equals(subject));

        // null -> returns false
        assertFalse(subject.equals(null));

        // different types -> returns false
        assertFalse(subject.equals(5.0f));

        // different values -> returns false
        assertFalse(subject.equals(new Subject("Other Valid Subject-A")));
    }

    @Test
    public void testToString() {
        Subject subject = new Subject("Valid Subject-A");

        // same values -> returns true
        assertTrue(subject.toString().equals(new Subject("Valid Subject-A").toString()));

        // same object -> returns true
        assertTrue(subject.toString().equals(subject.toString()));

        // null -> returns false
        assertFalse(subject.toString().equals(null));

        // different types -> returns false
        assertFalse(subject.toString().equals(5.0f));

        // different values -> returns false
        assertFalse(subject.toString().equals(new Subject("Other Valid Subject-A").toString()));
    }
}
