package tutorpro.logic.parser;

import static tutorpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorpro.logic.parser.CliSyntax.PREFIX_HOURS;
import static tutorpro.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import tutorpro.logic.commands.EventCommand;
import tutorpro.logic.parser.exceptions.ParseException;
import tutorpro.model.schedule.Event;
import tutorpro.model.tag.Tag;

/**
 * Parses inputs commands and creates a EventCommand object.
 */
public class EventCommandParser implements Parser<EventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemindCommand
     * and returns an RemindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TIME, PREFIX_TAG, PREFIX_HOURS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TIME, PREFIX_HOURS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TIME, PREFIX_HOURS);
        String name = argMultimap.getValue(PREFIX_NAME).get();
        LocalDateTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        float hours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_HOURS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Event event = new Event(name, time, hours, "", new HashSet<>(), tagList);

        return new EventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
