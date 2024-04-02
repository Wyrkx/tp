package tutorpro.model.schedule;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import tutorpro.commons.util.CollectionUtil;
import tutorpro.model.person.Person;
import tutorpro.model.tag.Tag;

/**
 * A class representing a Reminder.
 */
public class Reminder {
    private final Set<Person> people = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    private final String name;
    private final LocalDateTime time;
    private final String notes;

    /**
     * Every field must be present and not null.
     */
    public Reminder(String name, LocalDateTime time, String notes, Set<Person> people, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(name, time, people, notes, tags);
        this.name = name;
        this.time = time;
        this.notes = notes;
        this.people.addAll(people);
        this.tags.addAll(tags);
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getNotes() {
        return notes;
    }

    public Set<Person> getPeople() {
        return Collections.unmodifiableSet(people);
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Reminder)) {
            return false;
        }
        Reminder rmd = (Reminder) obj;
        return this.name == rmd.name
                && this.time.equals(rmd.time)
                && this.people.equals(rmd.people)
                && this.tags.equals(rmd.tags)
                && this.notes == rmd.notes;
    }

    public int hashCode() {
        return Objects.hash(name, time, notes, people, tags);
    }
}
