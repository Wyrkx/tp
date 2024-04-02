package tutorpro.logic.commands;

import tutorpro.model.Model;

/**
 * Format user's schedule for display.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows user's schedule.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_SCHEDULE_MESSAGE = "Opened schedule.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_SCHEDULE_MESSAGE, false, false, true);
    }
}
