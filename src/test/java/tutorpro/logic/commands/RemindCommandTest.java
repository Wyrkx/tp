package tutorpro.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import tutorpro.model.schedule.Reminder;

/**
 * Contains integration tests (interaction with the Model) for {@code RemindCommand}.
 */
public class RemindCommandTest {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Reminder sampleReminder1 = new Reminder("sample1",
            LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER), "sample1",
            new HashSet<>(), new HashSet<>());

    Reminder sampleReminder2 = new Reminder("sample2",
            LocalDateTime.parse("2024-06-06 12:00", DATE_TIME_FORMATTER), "sample2",
            new HashSet<>(), new HashSet<>());

    @Test
    public void equals() {
        RemindCommand remindFirstCommand = new RemindCommand(sampleReminder1);
        RemindCommand remindSecondCommand = new RemindCommand(sampleReminder2);

        // same object -> returns true
        assertTrue(remindFirstCommand.equals(remindFirstCommand));

        // different types -> returns false
        assertFalse(remindFirstCommand.equals(1));

        // null -> returns false
        assertFalse(remindFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(remindFirstCommand.equals(remindSecondCommand));
    }


}