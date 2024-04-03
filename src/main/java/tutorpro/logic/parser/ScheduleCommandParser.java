package tutorpro.logic.parser;


import tutorpro.logic.commands.ScheduleCommand;
import tutorpro.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns a ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        int numOfDays;
        if (trimmedArgs.isEmpty()) {
            numOfDays = 14;
        } else {
            numOfDays = Integer.parseInt(trimmedArgs);
        }

        return new ScheduleCommand(numOfDays);
    }

}
