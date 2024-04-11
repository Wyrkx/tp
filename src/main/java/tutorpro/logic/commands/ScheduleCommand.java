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
            + "Parameters: DAYS (must be a positive integer)\n"
            + "If no parameters entered, user's schedule for the next 14 days will be displayed."
            + "Example: " + COMMAND_WORD + " or " + COMMAND_WORD + " 6";

    public static final String SHOWING_SCHEDULE_MESSAGE = "Opened schedule.";
    private static int numOfDaysToShow;

    public ScheduleCommand(int n) {
        numOfDaysToShow = n;
    }

    public ScheduleCommand() {
        numOfDaysToShow = 14;
    }

    public int getNumOfDays() {
        return numOfDaysToShow;
    }

    public void setNumOfDays(int n) {
        numOfDaysToShow = n;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (numOfDaysToShow < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_NUMBER_OF_DAYS);
        }

        model.updateTruncatedScheduleList(numOfDaysToShow);
        //return new CommandResult(SHOWING_SCHEDULE_MESSAGE, false, false, true);
        return new CommandResult(
                String.format(Messages.MESSAGE_SCHEDULE_LISTED, model.getTruncatedScheduleList().size()),
                true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof ScheduleCommand)) {
            return false;
        }
        ScheduleCommand otherScheduleCommand = (ScheduleCommand) other;
        return ScheduleCommand.numOfDaysToShow == otherScheduleCommand.numOfDaysToShow;
    }
}
