package tutorpro.logic.parser;

import static tutorpro.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tutorpro.logic.commands.ScheduleCommand;


public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_emptyArg_returnsScheduleCommand() {
        // Returns correct ScheduleCommand -> success
        assertParseSuccess(parser, " ", ScheduleCommand.getInstance());
    }

}
