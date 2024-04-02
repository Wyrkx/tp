package tutorpro.model.schedule;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import tutorpro.commons.util.CollectionUtil;
import tutorpro.model.person.Person;
import tutorpro.model.tag.Tag;

/**
 * Represents an Event, a Reminder that also has an ending time and a duration (in hours).
 */
public class Event extends Reminder {

    public static final String END_BEFORE_START = "Events cannot end before they start.";
    private final long duration;

    /**
     * Every field must be present and not null.
     */
    public Event(String name, LocalDateTime startTime, long duration, String notes, Set<Person> people, Set<Tag> tags) {
        super(name, startTime, notes, people, tags);
        CollectionUtil.requireAllNonNull(duration);
        this.duration = duration;
    }

    /**
     * Returns the duration of the event in hours.
     */
    public long getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return getTime().plusHours(duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || !(o instanceof Event)) {
            return false;
        } else if (!super.equals(o)) {
            return false;
        }
        Event event = (Event) o;
        return duration == event.duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duration);
    }
}
