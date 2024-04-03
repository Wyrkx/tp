package tutorpro.logic.parser;

import static tutorpro.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import tutorpro.logic.Messages;
import tutorpro.logic.commands.RemindCommand;

public class RemindCommandParserTest {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private RemindCommandParser parser = new RemindCommandParser();

    @Test
    public void parse_emptyArg_throwsException() {
        assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
    }

}
