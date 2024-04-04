package tutorpro.model;

import javafx.collections.ObservableList;
import tutorpro.model.person.Person;
import tutorpro.model.schedule.Reminder;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    ///**
    // * Returns an unmodifiable view of the persons list.
    // * This list will not contain any duplicate persons.
    // */
    //ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the student list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();



    /**
     * Returns the user's list of reminders and events (their schedule)
     */
    ObservableList<Reminder> getSchedule();
}
