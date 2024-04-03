package tutorpro.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tutorpro.logic.parser.Prefix;
import tutorpro.model.person.Person;
import tutorpro.model.schedule.Event;
import tutorpro.model.schedule.Reminder;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code reminder} for display to the user.
     */
    public static String format(Reminder reminder) {
        final StringBuilder builder = new StringBuilder();
        builder.append(reminder.getName())
                .append("; Time: ")
                .append(reminder.getTime())
                .append("; Tags: ");
        reminder.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code event} for display to the user.
     */
    public static String format(Event event) {
        final StringBuilder builder = new StringBuilder();
        builder.append(event.getName())
                .append("; Time: ")
                .append(event.getTime())
                .append("; Duration: ")
                .append(event.getDuration())
                .append("; Tags: ");
        event.getTags().forEach(builder::append);
        return builder.toString();
    }
}
