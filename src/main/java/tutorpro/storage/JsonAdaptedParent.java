package tutorpro.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutorpro.commons.exceptions.IllegalValueException;
import tutorpro.model.person.Address;
import tutorpro.model.person.Email;
import tutorpro.model.person.Name;
import tutorpro.model.person.Phone;
import tutorpro.model.person.student.Level;
import tutorpro.model.person.student.Parent;
import tutorpro.model.person.student.Student;
import tutorpro.model.tag.Tag;

/**
* Jackson-friendly version of {@link Parent}.
*/
public class JsonAdaptedParent extends JsonAdaptedPerson {

    private final List<String> children = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedParent} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedParent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("email") String email, @JsonProperty("address") String address,
                              @JsonProperty("children") List<String> children,
                              @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(name, phone, email, address, tags);
        this.children.addAll(children);
    }

    /**
     * Converts a given {@code Parent} into this class for Jackson use.
     */
    public JsonAdaptedParent(Parent source) {
        super(source.getName().fullName, source.getPhone().value, source.getEmail().value, source.getAddress().value,
                source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));
        children.addAll(source.getChildren().stream().map(child -> child.getName().toString())
                .collect(Collectors.toList()));
    }

    @Override
    public Parent toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        System.out.println(children);

        final Set<Student> modelChildren = new HashSet<>(children.stream()
                .map(name -> new Student(new Name(name), new Phone("88888888"), new Email("a@bc"), new Address("0"),
                    new HashSet<>(), new Level("OTHER"), new HashSet<>())).collect(Collectors.toSet()));
        System.out.println(modelChildren);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Parent(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelChildren);
    }
}
