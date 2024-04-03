package tutorpro.logic.parser;

import static tutorpro.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import tutorpro.logic.Messages;
import tutorpro.logic.commands.RemindCommand;
import tutorpro.model.schedule.Reminder;

public class RemindCommandParserTest {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    Reminder sampleReminder = new Reminder("sample1",
            LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER), "sample1",
            new HashSet<>(), new HashSet<>());
    private RemindCommandParser parser = new RemindCommandParser();

    @Test
    public void parse_emptyArg_throwsException() {
        assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
    }

}
