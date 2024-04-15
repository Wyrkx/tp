package tutorpro.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import tutorpro.model.AddressBook;
import tutorpro.model.ReadOnlyAddressBook;
import tutorpro.model.person.Address;
import tutorpro.model.person.Email;
import tutorpro.model.person.Name;
import tutorpro.model.person.Person;
import tutorpro.model.person.Phone;
import tutorpro.model.person.student.Level;
import tutorpro.model.person.student.Parent;
import tutorpro.model.person.student.Student;
import tutorpro.model.person.student.Subject;
import tutorpro.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        ArrayList<Student> students = new ArrayList<>(Arrays.asList(getSampleStudents()));
        Set<Tag> children = new HashSet<>();
        children.add(new Tag(students.get(2).getName().toString().replace(" ", "")));
        children.add(new Tag(students.get(3).getName().toString().replace(" ", "")));
        ArrayList<Person> people = new ArrayList<>(students);
        people.add(new Parent(new Name("Donovan Li"), new Phone("98758712"), new Email("donovan@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                children, new HashSet<>()));
        return people.toArray(new Person[]{});
    }

    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet(), new Level("P6"), getSubjectSet("Math-C")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet(), new Level("S2"), getSubjectSet("Math-B3", "English-B4")),
            new Student(new Name("Charlotte Li"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet(), new Level("J1"), getSubjectSet("Physics-B")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet(), new Level("UNI"), getSubjectSet("Chemistry-B+", "Biology-B-")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet(), new Level("K2"), getSubjectSet("Reading-A")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet(), new Level("OTHER"), getSubjectSet("Interview-A"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a subject set containing the list of strings given.
     */
    public static Set<Subject> getSubjectSet(String... strings) {
        return Arrays.stream(strings)
                .map(Subject::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a student set containing the list of students given.
     */
    public static Set<Student> getStudentSet(Student... students) {
        return Arrays.stream(students).collect(Collectors.toSet());
    }
}
