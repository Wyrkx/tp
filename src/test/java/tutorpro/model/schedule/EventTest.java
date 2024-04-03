package tutorpro.model.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EventTest {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Event sampleEvent1 = new Event("Sample Event 1",
                    LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER), 1, "Sample Notes 1",
                            new HashSet<>(), new HashSet<>());

    private Event sampleEvent2 = new Event("Sample Event 2",
            LocalDateTime.parse("2024-06-06 12:00", DATE_TIME_FORMATTER), 2, "Sample Notes 2",
            new HashSet<>(), new HashSet<>());
    @Test
    public void getDuration() {
        // Correct duration returned --> returns true, else returns false
        double expected1 = 1;
        double expected2 = 2;
        Assertions.assertEquals(expected1, sampleEvent1.getDuration());
        Assertions.assertEquals(expected2, sampleEvent2.getDuration());
    }

    @Test
    public void equals() {
        // Same object -> returns true
        Assertions.assertTrue(sampleEvent1.equals(sampleEvent1));

        // Different object -> returns false
        Assertions.assertFalse(sampleEvent1.equals(sampleEvent2));

        // Null object -> returns false
        Assertions.assertFalse(sampleEvent1.equals(null));

        // Different class -> returns false
        String notAnEvent = "I am not an Event object";
        Assertions.assertFalse(sampleEvent1.equals(notAnEvent));

        // Different duration -> returns false
        Event differentDurationEvent = new Event(sampleEvent1.getName(), sampleEvent1.getTime(), sampleEvent1.getDuration() + 1, sampleEvent1.getNotes(), sampleEvent1.getPeople(), sampleEvent1.getTags());
        Assertions.assertFalse(sampleEvent1.equals(differentDurationEvent));

        // Same duration -> returns true
        Event sameDurationEvent = new Event(sampleEvent1.getName(), sampleEvent1.getTime(), sampleEvent1.getDuration(), sampleEvent1.getNotes(), sampleEvent1.getPeople(), sampleEvent1.getTags());
        Assertions.assertTrue(sampleEvent1.equals(sameDurationEvent));

        
    }

    @Test
    public void getEndTime() {
        // Gets correct end time -> returns true
        LocalDateTime expected1 = LocalDateTime.parse("2024-05-05 13:00", DATE_TIME_FORMATTER);
        LocalDateTime expected2 = LocalDateTime.parse("2024-06-06 14:00", DATE_TIME_FORMATTER);
        Assertions.assertEquals(expected1, sampleEvent1.getEndTime());
        Assertions.assertEquals(expected2, sampleEvent2.getEndTime());
    }

    @Test
    public void hashCode_checkCorrectHash() {
        // Same object -> returns same hash code
        Assertions.assertEquals(sampleEvent1.hashCode(), sampleEvent1.hashCode());

        // Different object with same values -> returns same hash code
        Event sameValuesEvent = new Event(sampleEvent1.getName(), sampleEvent1.getTime(), sampleEvent1.getDuration(), sampleEvent1.getNotes(), sampleEvent1.getPeople(), sampleEvent1.getTags());
        Assertions.assertEquals(sampleEvent1.hashCode(), sameValuesEvent.hashCode());

        // Different object with different values -> returns different hash code
        Event differentValuesEvent = new Event(sampleEvent1.getName(), sampleEvent1.getTime(), sampleEvent1.getDuration() + 1, sampleEvent1.getNotes(), sampleEvent1.getPeople(), sampleEvent1.getTags());
        Assertions.assertNotEquals(sampleEvent1.hashCode(), differentValuesEvent.hashCode());
    }
}
