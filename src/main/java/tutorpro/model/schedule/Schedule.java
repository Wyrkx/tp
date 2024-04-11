package tutorpro.model.schedule;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a Schedule that manages and stores Reminders and events.
 */
public class Schedule implements Iterable<Reminder> {

    private final ObservableList<Reminder> events = FXCollections.observableArrayList();
    private final ObservableList<Reminder> internalUnmodifiableEvents =
            FXCollections.unmodifiableObservableList(events);

    /**
     * Adds a reminder to the Schedule.
     */
    public void add(Reminder toAdd) {
        events.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reminder> getEvents() {
        return internalUnmodifiableEvents;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reminder> asUnmodifiableObservableEvents() {
        return internalUnmodifiableEvents;
    }

    @Override
    public Iterator<Reminder> iterator() {
        return events.iterator();
    }
}
