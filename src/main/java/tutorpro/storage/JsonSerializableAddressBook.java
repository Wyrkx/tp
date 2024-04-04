package tutorpro.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import tutorpro.commons.exceptions.IllegalValueException;
import tutorpro.model.AddressBook;
import tutorpro.model.ReadOnlyAddressBook;
import tutorpro.model.person.Person;
import tutorpro.model.person.student.Parent;
import tutorpro.model.person.student.Student;
import tutorpro.model.schedule.Event;
import tutorpro.model.schedule.Reminder;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedReminder> reminders = new ArrayList<>();
    private final List<JsonAdaptedParent> parents = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("students") List<JsonAdaptedStudent> students,
                                       @JsonProperty("parents") List<JsonAdaptedParent> parents,
                                       @JsonProperty("reminders") List<JsonAdaptedReminder> reminders,
                                       @JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.students.addAll(students);
        this.parents.addAll(parents);
        this.reminders.addAll(reminders);
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        students.addAll(source.getPersonList().stream().filter(person -> person instanceof Student)
                .map(person -> new JsonAdaptedStudent((Student) person)).collect(Collectors.toList()));
        parents.addAll(source.getPersonList().stream().filter(person -> person instanceof Parent)
                .map(person -> new JsonAdaptedParent((Parent) person)).collect(Collectors.toList()));
        events.addAll(source.getSchedule().stream().filter(reminder -> reminder instanceof Event)
                .map(reminder -> new JsonAdaptedEvent((Event) reminder)).collect(Collectors.toList()));
        reminders.addAll(source.getSchedule().stream().filter(reminder -> !(reminder instanceof Event))
                .map(reminder -> new JsonAdaptedReminder(reminder)).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Person person = jsonAdaptedStudent.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (JsonAdaptedParent jsonAdaptedParent : parents) {
            Person person = jsonAdaptedParent.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Reminder reminder = jsonAdaptedEvent.toModelType();
            addressBook.addReminder(reminder);
        }
        for (JsonAdaptedReminder jsonAdaptedReminder : reminders) {
            Reminder reminder = jsonAdaptedReminder.toModelType();
            addressBook.addReminder(reminder);
        }
        return addressBook;
    }
}
