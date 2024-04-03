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
    private static ScheduleCommand instance = new ScheduleCommand(14);
    private int numOfDaysToShow;
    private ScheduleCommand(int n) {
        this.numOfDaysToShow = n;
    }

    public int getNumOfDays() {
        return numOfDaysToShow;
    }
    public void setNumOfDays(int n) {
        numOfDaysToShow = n;
    }
    public static ScheduleCommand getInstance() {
        return instance;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (numOfDaysToShow < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_NUMBER_OF_DAYS);
        }

        model.getTruncatedSchedule(numOfDaysToShow);
        return new CommandResult(SHOWING_SCHEDULE_MESSAGE, false, false, true);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof ScheduleCommand)) {
            return false;
        }
        ScheduleCommand otherScheduleCommand = (ScheduleCommand) other;
        return ScheduleCommand.instance.numOfDaysToShow == otherScheduleCommand.numOfDaysToShow;
    }
}
