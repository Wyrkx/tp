package tutorpro.model.person.student;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.layout.Region;
import tutorpro.commons.util.CollectionUtil;
import tutorpro.commons.util.ToStringBuilder;
import tutorpro.model.person.Address;
import tutorpro.model.person.Email;
import tutorpro.model.person.Name;
import tutorpro.model.person.Person;
import tutorpro.model.person.Phone;
import tutorpro.model.tag.Tag;
import tutorpro.ui.ParentCard;
import tutorpro.ui.UiPart;

/**
 * Represents a Parent in TutorPro.
 */
public class Parent extends Person {

    public static final Tag PARENT_TAG = new Tag("Parent");

    private final Set<Student> children = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Parent(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<Student> children) {
        super(name, phone, email, address, tags);
        addTags(PARENT_TAG);
        CollectionUtil.requireAllNonNull(children);
        this.children.addAll(children);
    }
    /**
     * Creates a copy of the given Parent.
     * @return The students of the parent.
     */
    public Set<Student> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Parent)) {
            return false;
        }

        Parent otherParent = (Parent) other;
        return otherParent.getName().equals(getName())
                && otherParent.getPhone().equals(getPhone())
                && otherParent.getEmail().equals(getEmail())
                && otherParent.getAddress().equals(getAddress())
                && otherParent.getTags().equals(getTags())
                && otherParent.getChildren().equals(getChildren());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("phone", getPhone())
                .add("email", getEmail())
                .add("address", getAddress())
                .add("tags", getTags())
                .add("children", getChildren())
                .toString();
    }

    @Override
    public UiPart<Region> getCard(int displayIndex) {
        return new ParentCard(this, displayIndex);
    }
}
