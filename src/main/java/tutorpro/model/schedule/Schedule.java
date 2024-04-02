package tutorpro.model.schedule;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a Calendar that manages and stores Reminders.
 */
public class Schedule implements Iterable<Reminder> {

    private ObservableList<Reminder> events = FXCollections.observableArrayList();
    private ObservableList<Reminder> internalUnmodifiableEvents = FXCollections.unmodifiableObservableList(events);

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

    @Override
    public Iterator<Reminder> iterator() {
        return events.iterator();
    }
}
