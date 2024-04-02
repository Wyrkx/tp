package tutorpro.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorpro.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TIME;

import tutorpro.commons.util.ToStringBuilder;
import tutorpro.logic.Messages;
import tutorpro.model.Model;
import tutorpro.model.schedule.Reminder;

/**
 * Adds a reminder to the address book.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to your schedule. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "TIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Bob uni app deadline "
            + PREFIX_TIME + "2024-03-04 12:00 "
            + PREFIX_TAG + "urgent";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";

    private final Reminder toAdd;

    /**
     * Creates a RemindCommand to add the specified {@code Reminder}
     */
    public RemindCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
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
        RemindCommand otherAddCommand = (RemindCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
