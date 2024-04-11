package tutorpro.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorpro.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static tutorpro.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorpro.logic.parser.CliSyntax.PREFIX_PERSON;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TAG;
import static tutorpro.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import tutorpro.commons.core.index.Index;
import tutorpro.commons.util.CollectionUtil;
import tutorpro.logic.Messages;
import tutorpro.model.Model;
import tutorpro.model.person.Person;
import tutorpro.model.schedule.Reminder;
import tutorpro.model.tag.Tag;

/**
 * Adds a reminder to the address book.
 */
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to your schedule. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "TIME "
            + "[" + PREFIX_DESCRIPTION + "DESC] "
            + "[" + PREFIX_PERSON + "INDEX]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Bob uni app deadline "
            + PREFIX_TIME + "2024-03-04 12:00 "
            + PREFIX_DESCRIPTION + "ONLINE "
            + PREFIX_PERSON + "1 "
            + PREFIX_TAG + "urgent";

    public static final String MESSAGE_SUCCESS = "New reminder added: %1$s";

    private final String name;
    private final LocalDateTime time;
    private final String notes;
    private final Set<Index> indexes;
    private final Set<Tag> tagList;

    /**
     * Creates a RemindCommand to add the specified {@code Reminder}
     */
    public RemindCommand(Reminder reminder) {
        requireNonNull(reminder);
        this.name = reminder.getName();
        this.time = reminder.getTime();
        this.notes = reminder.getNotes();
        this.indexes = new HashSet<>();
        this.tagList = reminder.getTags();
    }

    /**
     * Creates a RemindCommand to add a {@code Reminder} with the specified attributes.
     */
    public RemindCommand(String name, LocalDateTime time, String notes, Set<Index> indexes, Set<Tag> tagList) {
        CollectionUtil.requireAllNonNull(name, time, notes, indexes, tagList);
        this.name = name;
        this.time = time;
        this.notes = notes;
        this.indexes = indexes;
        this.tagList = tagList;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Set<Person> people = indexes.stream().filter(index -> index.getZeroBased() >= lastShownList.size())
                .map(index -> lastShownList.get(index.getZeroBased())).collect(Collectors.toSet());
        Reminder reminder = new Reminder(name, time, notes, people, tagList);
        model.addReminder(reminder);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(reminder)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof RemindCommand)) {
            return false;
        }
        RemindCommand otherAddCommand = (RemindCommand) other;
        return this.name == otherAddCommand.name
                && this.notes == otherAddCommand.name
                && this.indexes.equals(otherAddCommand)
                && this.tagList.equals(otherAddCommand)
                && this.time.equals(otherAddCommand);
    }

    @Override
    public String toString() {
        return "RemindCommand{"
                + "name='" + name + '\''
                + ", time=" + time
                + ", notes='" + notes + '\''
                + ", indexes=" + indexes
                + ", tagList=" + tagList
                + '}';
    }
}
