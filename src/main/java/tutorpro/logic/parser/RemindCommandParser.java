package tutorpro.logic.parser;

import static tutorpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorpro.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static tutorpro.logic.parser.CliSyntax.PREFIX_NOTES;
import static tutorpro.logic.parser.CliSyntax.PREFIX_PERSON;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import tutorpro.commons.core.index.Index;
import tutorpro.logic.commands.RemindCommand;
import tutorpro.logic.parser.exceptions.ParseException;
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
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_TIME, PREFIX_TAG,
                        PREFIX_PERSON, PREFIX_NOTES);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DESCRIPTION, PREFIX_TIME);
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        String notes = argMultimap.getValue(PREFIX_NOTES).orElse("");
        LocalDateTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<Index> personList = ParserUtil.parseIndexes(argMultimap.getAllValues(PREFIX_PERSON));

        return new RemindCommand(description, time, notes, personList, tagList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
