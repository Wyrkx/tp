package tutorpro.logic.parser;

import static tutorpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorpro.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import tutorpro.logic.commands.RemindCommand;
import tutorpro.logic.parser.exceptions.ParseException;
import tutorpro.model.schedule.Reminder;
import tutorpro.model.tag.Tag;

/**
 * Parses input arguments and creates a new RemindCommand object
 */
public class RemindCommandParser implements Parser<RemindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemindCommand
     * and returns an RemindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TIME, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TIME);
        String name = argMultimap.getValue(PREFIX_NAME).get();
        LocalDateTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Reminder reminder = new Reminder(name, time, "", new HashSet<>(), tagList);

        return new RemindCommand(reminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
