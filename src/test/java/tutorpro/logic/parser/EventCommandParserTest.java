package tutorpro.logic.parser;

import static tutorpro.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import tutorpro.logic.Messages;
import tutorpro.logic.commands.EventCommand;
import tutorpro.model.schedule.Event;

public class EventCommandParserTest {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Event sampleEvent1 = new Event("Sample Event 1",
            LocalDateTime.parse("2024-05-05 12:00", DATE_TIME_FORMATTER), 1, "Sample Notes 1",
            new HashSet<>(), new HashSet<>());
    private EventCommandParser parser = new EventCommandParser();

    @Test
    public void parse_emptyArg_throwsException() {
        assertParseFailure(parser, "     ",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
    }

}
