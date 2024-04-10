package tutorpro.model.schedule;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.scene.layout.Region;
import tutorpro.commons.util.CollectionUtil;
import tutorpro.model.person.Person;
import tutorpro.model.tag.Tag;
import tutorpro.ui.ReminderCard;
import tutorpro.ui.UiPart;

/**
 * A class representing a Reminder.
 */
public class Reminder {
    private final Set<Person> people = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    private final String description;
    private final LocalDateTime time;
    private final String notes;

    /**
     * Every field must be present and not null.
     */
    public Reminder(String description, LocalDateTime time, String notes, Set<Person> people, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(description, time, people, notes, tags);
        this.description = description;
        this.time = time;
        this.notes = notes;
        this.people.addAll(people);
        this.tags.addAll(tags);
    }

    public String getDescription() {
        return description;
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
        return this.description == rmd.description
                && this.time.equals(rmd.time)
                && this.people.equals(rmd.people)
                && this.tags.equals(rmd.tags)
                && this.notes == rmd.notes;
    }

    public int hashCode() {
        return Objects.hash(description, time, notes, people, tags);
    }

    public UiPart<Region> getCard(int displayIndex) {
        return new ReminderCard(this, displayIndex);
    }
}
