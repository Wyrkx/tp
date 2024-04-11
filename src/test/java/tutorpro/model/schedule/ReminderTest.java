package tutorpro.model.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tutorpro.model.person.Person;
import tutorpro.model.tag.Tag;
import tutorpro.testutil.PersonBuilder;


public class ReminderTest {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Person person1 = new PersonBuilder().build();
    private Person person2 = new PersonBuilder().build();
    private Set<Person> people = new HashSet<Person>() {{
            add(person1);
            add(person2);
        }};

    private Tag tag1 = new Tag("Tag1");
    private Tag tag2 = new Tag("Tag2");
    private Set<Tag> tags = new HashSet<Tag>() {{
            add(tag1);
            add(tag2);
        }};

    private Reminder reminder = new Reminder("Test", LocalDateTime.now(), "Notes", people, tags);
    private Reminder sample1 = new Reminder("sample1",
            LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER), "sample1",
            new HashSet<>(), new HashSet<>());
    private Reminder sample2 = new Reminder("sample2",
            LocalDateTime.parse("2024-06-06 12:00", DATE_TIME_FORMATTER), "sample2",
            new HashSet<>(), new HashSet<>());

    @Test
    public void getPeople_returnsCorrectPeople() {
        Assertions.assertEquals(people, reminder.getPeople());
    }

    @Test
    public void getTags_returnsCorrectTags() {
        Assertions.assertEquals(tags, reminder.getTags());
    }

    @Test
    public void hashCode_returnsCorrectHashCode() {
        int expectedHashCode = Objects.hash("Test", reminder.getTime(), "Notes", people, tags);
        Assertions.assertEquals(expectedHashCode, reminder.hashCode());
    }
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

        // Null object -> returns false
        Assertions.assertFalse(sample1.equals(null));

        // Different class -> returns false
        String notAnEvent = "I am not an Event object";
        Assertions.assertFalse(sample1.equals(notAnEvent));

        // Different name -> returns false
        Reminder differentNameReminder = new Reminder("DifferentName", sample1.getTime(), sample1.getNotes(),
                sample1.getPeople(), sample1.getTags());
        Assertions.assertFalse(sample1.equals(differentNameReminder));

        // Same name -> returns true
        Reminder sameNameReminder = new Reminder(sample1.getName(), sample1.getTime(), sample1.getNotes(),
                sample1.getPeople(), sample1.getTags());
        Assertions.assertTrue(sample1.equals(sameNameReminder));

    }
}
