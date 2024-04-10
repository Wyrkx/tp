package tutorpro.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import tutorpro.commons.exceptions.IllegalValueException;
import tutorpro.model.person.Address;
import tutorpro.model.person.Email;
import tutorpro.model.person.Name;
import tutorpro.model.person.Person;
import tutorpro.model.person.Phone;
import tutorpro.model.schedule.Event;
import tutorpro.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent extends JsonAdaptedReminder {

    private String duration;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given person details.
     */
    public JsonAdaptedEvent(@JsonProperty("description") String description, @JsonProperty("time") LocalDateTime time,
                            @JsonProperty("notes") String notes, @JsonProperty("people") List<String> people,
                            @JsonProperty("tags") List<JsonAdaptedTag> tags,
                            @JsonProperty("duration") String duration) {
        super(description, time, notes, people, tags);
        this.duration = duration + "";
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event event) {
        super(event);
        duration = event.getDuration() + ""; // Convert double to string
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        final List<Person> reminderPeople = new ArrayList<>();
        for (String person : people) {
            reminderPeople.add(new Person(new Name(person), new Phone("88888888"),
                    new Email("a@bc"), new Address("0"), new HashSet<>()));
        }

        final List<Tag> reminderTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            reminderTags.add(tag.toModelType());
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(description)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final String modelName = description;

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDateTime.class.getSimpleName()));
        }
        final LocalDateTime modelTime = time;

        if (notes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }

        final String modelNotes = notes;

        duration += "d"; // convert back to Double
        final double modelDuration = Double.parseDouble(duration);

        final Set<Person> modelPeople = new HashSet<>(reminderPeople);

        final Set<Tag> modelTags = new HashSet<>(reminderTags);

        return new Event(modelName, modelTime, modelDuration, modelNotes, modelPeople, modelTags);
    }
}
