package tutorpro.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import tutorpro.commons.util.ToStringBuilder;
import tutorpro.model.schedule.Event;

/**
 * Contains integration tests (interaction with the Model) for {@code EventCommand}.
 */
public class EventCommandTest {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Event sampleEvent1 = new Event("Sample Event 1",
            LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER), 1, "Sample Notes 1",
            new HashSet<>(), new HashSet<>());

    private Event sampleEvent2 = new Event("Sample Event 2",
            LocalDateTime.parse("2024-06-06 12:00", DATE_TIME_FORMATTER), 2, "Sample Notes 2",
            new HashSet<>(), new HashSet<>());

    @Test
    public void equals() {
        EventCommand eventFirstCommand = new EventCommand(sampleEvent1);
        EventCommand eventSecondCommand = new EventCommand(sampleEvent2);

        // same object -> returns true
        assertTrue(eventFirstCommand.equals(eventFirstCommand));

        // different types -> returns false
        assertFalse(eventFirstCommand.equals(1));

        // null -> returns false
        assertFalse(eventFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(eventFirstCommand.equals(eventSecondCommand));
    }

    @Test
    public void toString_validEventCommand_returnsStringRepresentation() {
        EventCommand eventCommand = new EventCommand(sampleEvent1);
        String expectedString = new ToStringBuilder(eventCommand)
                .add("toAdd", sampleEvent1)
                .toString();
        assertEquals(expectedString, eventCommand.toString());
    }
}

