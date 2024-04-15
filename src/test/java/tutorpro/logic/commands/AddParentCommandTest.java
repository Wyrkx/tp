package tutorpro.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tutorpro.commons.core.GuiSettings;
import tutorpro.logic.commands.exceptions.CommandException;
import tutorpro.model.AddressBook;
import tutorpro.model.Model;
import tutorpro.model.ReadOnlyAddressBook;
import tutorpro.model.ReadOnlyUserPrefs;
import tutorpro.model.person.Person;
import tutorpro.model.person.student.Parent;
import tutorpro.model.schedule.Reminder;
import tutorpro.testutil.Assert;
import tutorpro.testutil.ParentBuilder;
import tutorpro.testutil.TypicalParents;

public class AddParentCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddParentCommand(null));
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Parent validPerson = new ParentBuilder().build();
        AddParentCommand addParentCommand = new AddParentCommand(validPerson);
        AddParentCommandTest.ModelStub modelStub = new AddParentCommandTest.ModelStubWithPerson(validPerson);

        Assert.assertThrows(CommandException.class,
                AddParentCommand.MESSAGE_DUPLICATE_PERSON, () -> addParentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Parent alice = new ParentBuilder().withName("Alice").build();
        Parent bob = new ParentBuilder().withName("Bob").build();
        AddParentCommand addAliceCommand = new AddParentCommand(alice);
        AddParentCommand addBobCommand = new AddParentCommand(bob);

        // same object -> returns true
        assertTrue(addBobCommand.equals(addBobCommand));

        // different types -> returns false
        assertFalse(addBobCommand.equals(1));

        // null -> returns false
        assertFalse(addBobCommand.equals(null));

        // different person -> returns false
        assertFalse(addBobCommand.equals(addAliceCommand));
    }

    @Test
    public void toStringMethod() {
        AddParentCommand addParentCommand = new AddParentCommand(TypicalParents.BOB);
        String expected = AddParentCommand.class.getCanonicalName() + "{toAdd=" + TypicalParents.BOB + "}";
        assertEquals(expected, addParentCommand.toString());
    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReminder(Reminder reminder) {

        }

        @Override
        public ObservableList<Reminder> getTruncatedSchedule(int n) {
            return null;
        }

        @Override
        public List<Reminder> getSchedule() {
            return null;
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends AddParentCommandTest.ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends AddParentCommandTest.ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
