package tutorpro.model.schedule;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ReminderComparatorTest {
    private ReminderComparator comparator = new ReminderComparator();

    @Test
    public void compare() {
        Reminder reminder1 = new Reminder("Test1", LocalDateTime.of(2022, 1, 1, 12, 0), "Notes1", null, null);
        Reminder reminder2 = new Reminder("Test2", LocalDateTime.of(2022, 1, 1, 13, 0), "Notes2", null, null);
        Reminder reminder3 = new Reminder("Test3", LocalDateTime.of(2022, 1, 1, 12, 0), "Notes3", null, null);

        // Reminder1 is before Reminder2 -> returns -1
        Assertions.assertEquals(-1, comparator.compare(reminder1, reminder2));

        // Reminder1 is equal to Reminder3 -> returns 0
        Assertions.assertEquals(0, comparator.compare(reminder1, reminder3));

        // Reminder2 is after Reminder1 -> returns 1
        Assertions.assertEquals(1, comparator.compare(reminder2, reminder1));
    }
}
