package tutorpro.model.schedule;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ReminderTest {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    Reminder sample1 = new Reminder("sample1",
            LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER), "sample1",
            new HashSet<>(), new HashSet<>());

    Reminder sample2 = new Reminder("sample2",
            LocalDateTime.parse("2024-06-06 12:00", DATE_TIME_FORMATTER), "sample2",
            new HashSet<>(), new HashSet<>());
    @Test
    public void getName() {
        // correct values returned -> return true
        String expected1 = "sample1";
        String expected2 = "sample2";
        Assertions.assertEquals(expected1, sample1.getName());
        Assertions.assertEquals(expected2, sample2.getName());
    }

    @Test
    public void getTime() {
        // correct values returned -> return true
        LocalDateTime expected1 = LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER);
        LocalDateTime expected2 = LocalDateTime.parse("2024-06-06 12:00", DATE_TIME_FORMATTER);
        Assertions.assertEquals(expected1, sample1.getTime());
        Assertions.assertEquals(expected2, sample2.getTime());
    }

    @Test
    public void getNotes() {
        // correct values returned -> return true
        String expected1 = "sample1";
        String expected2 = "sample2";
        Assertions.assertEquals(expected1, sample1.getNotes());
        Assertions.assertEquals(expected2, sample2.getNotes());
    }

    @Test
    public void equals() {
        // Same object -> returns true
        Assertions.assertTrue(sample1.equals(sample1));

        // Different object -> returns false
        Assertions.assertFalse(sample1.equals(sample2));

    }
}
