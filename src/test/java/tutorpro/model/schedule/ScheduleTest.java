package tutorpro.model.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScheduleTest {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Event sampleEvent1 = new Event("Sample Event 1",
            LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER), 1, "Sample Notes 1",
            new HashSet<>(), new HashSet<>());

    private Event sampleEvent2 = new Event("Sample Event 2",
            LocalDateTime.parse("2024-06-06 12:00", DATE_TIME_FORMATTER), 2, "Sample Notes 2",
            new HashSet<>(), new HashSet<>());
    private Reminder sampleReminder1 = new Reminder("sample1",
            LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER), "sample1",
            new HashSet<>(), new HashSet<>());

    private Reminder sampleReminder2 = new Reminder("sample2",
            LocalDateTime.parse("2024-06-06 12:00", DATE_TIME_FORMATTER), "sample2",
            new HashSet<>(), new HashSet<>());

    @Test
    public void add() {
        Schedule testSchedule = new Schedule();
        testSchedule.add(sampleEvent1);
        testSchedule.add(sampleReminder1);

        // return correct number of events added -> return true
        Assertions.assertEquals(2, testSchedule.getEvents().size());

        // added correct events -> return true
        Assertions.assertEquals(sampleEvent1, testSchedule.getEvents().get(0));
        Assertions.assertEquals(sampleReminder1, testSchedule.getEvents().get(1));
    }

    @Test
    public void iterator() {
        Schedule testSchedule = new Schedule();
        testSchedule.add(sampleReminder1);
        testSchedule.add(sampleReminder2);

        Iterator<Reminder> iterator = testSchedule.iterator();

        // Check first reminder
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals(sampleReminder1, iterator.next());

        // Check second reminder
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals(sampleReminder2, iterator.next());

        // No more reminders
        Assertions.assertFalse(iterator.hasNext());
   }
}
