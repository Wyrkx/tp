package tutorpro.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorpro.logic.parser.CliSyntax.PREFIX_HOURS;
import static tutorpro.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TIME;

import tutorpro.commons.util.ToStringBuilder;
import tutorpro.logic.Messages;
import tutorpro.model.Model;
import tutorpro.model.schedule.Event;

/**
 * Adds an event to the address book.
 */
public class EventCommand extends Command {

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to your schedule. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "TIME "
            + PREFIX_HOURS + "HOURS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Bob math tutoring "
            + PREFIX_TIME + "2024-03-04 12:00 "
            + PREFIX_HOURS + "2 "
            + PREFIX_TAG + "math "
            + PREFIX_TAG + "tutoring ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    private final Event toAdd;

    /**
     * Creates a EventCommand to add the specified {@code Event}
     */
    public EventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) {
        model.addReminder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof RemindCommand)) {
            return false;
        }
        EventCommand otherAddCommand = (EventCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
