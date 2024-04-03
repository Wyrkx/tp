package tutorpro.logic.commands;

import tutorpro.logic.Messages;
import tutorpro.logic.commands.exceptions.CommandException;
import tutorpro.model.Model;

/**
 * Format user's schedule for the next specified number of days for display.
 * If no number of days is specified, displays user's schedule for the next 14 days
 *
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows user's schedule.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_SCHEDULE_MESSAGE = "Opened schedule.";
    private int numOfDaysToShow;

    public ScheduleCommand(int n) {
        this.numOfDaysToShow = n;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (numOfDaysToShow < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.getTruncatedSchedule(numOfDaysToShow);
        return new CommandResult(SHOWING_SCHEDULE_MESSAGE, false, false, true);
    }

}
