---
layout: page
title: Developer Guide
---

- Table of Contents
  {:toc}

---

## **Acknowledgements**

- {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S2-CS2103T-F12-3/tp/tree/master/src/main/java/tutorpro/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, 
`StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures 
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files 
that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2324S2-CS2103T-F12-3/tp/tree/master/src/main/java/tutorpro/ui/MainWindow.java) is specified in 
[`MainWindow.fxml`](https://github.com/AY2324S2-CS2103T-F12-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person`, `Student` and `Parent` objects residing in 
- the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2324S2-CS2103T-F12-3/tp/tree/master/src/main/java/tutorpro/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/AY2324S2-CS2103T-F12-3/tp/blob/master/src/main/java/tutorpro/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object), and all 
`Reminder` objects.
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which 
is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this 
list so that the UI automatically updates when the data in the list change.
- `Student` and `Parent` objects both inherit from `Person` class.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** : [`Storage.java`](https://github.com/AY2324S2-CS2103T-F12-3/tp/blob/master/src/main/java/tutorpro/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

- can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `tutorpro.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add feature

#### Current implementation

The revised add mechanism is facilitated by `Student`. It extends `Person` and contains the following additional fields
compared to the original version from AB3. 
- `Level` - Representing the education level
- `Subject` - Representing a subject, as well as its corresponding grade

As compared to `Person`, instances of `Student` will automatically have a "Student" `Tag` added on construction.

Otherwise, all other implementation details are the same as AB3.

### Save feature

#### Current implementation

The revised save mechanism is facilitated by `JsonSerializableAddressBook`, that directly or indirectly contains
various other classes that convert objects to be saved to Jackson-friendly versions, heavily inspired by the original AB3 code.
- `JsonAdaptedStudent` for the `Student` class
- `JsonAdaptedSubject` for the `Subject` class
- `JsonAdaptedParent` for the `Parent` class
- `JsonAdaptedReminder` for the `Reminder` class
- `JsonAdaptedEvent` for the `Event` class

When saving, a new `JsonSerializableAddressBook` object is created, with a `ReadOnlyAddressBook` as a parameter.
All `Person`s and child classes are retrieved from the `ReadOnlyAddressBook` as a stream, 
then split using filter to create their respective Jackson-friendly versions. The same as done for all `Reminder`s and child classes.   

Loading is implemented in the same way as AB3.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

- **Alternative 1 (current choice):** Saves the entire address book.

  - Pros: Easy to implement.
  - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  - Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  - Cons: We must ensure that the implementation of each individual command are correct.


---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- Private tutors who prefer CLI over GUI
- Private tutors who go to tutees' houses and teach one-to-one
- Private tutors who teach multiple subjects
- Private tutors who teach multiple levels or a specific level, e.g. primary school students
- Private tutors who need to track the progress of each student e.g. grades
- Private tutors who need to keep track of lesson plans
- Private tutors who need to manage their schedule
- Private tutors who need to manage a list of student contacts
- Private tutors who need to manage a list of parent contacts

**Value proposition**: Allows for easy track and management of tutees, e.g. grades, addresses, contacts

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​       | I want to …​                                           | So that I can…​                                    |
| -------- | ------------- |--------------------------------------------------------| -------------------------------------------------- |
| `* * *`  | private tutor | track my tutee's grades, addresses and contacts easily | efficiently manage my tutoring sessions            |
| `* * *`  | private tutor | input new tutees' progress seamlessly                  | add new tutees' information                        |
| `* * *`  | private tutor | delete previously created inputs                       | remove information that I no longer need           |
| `* * *`  | private tutor | track my project tasks and timelines seamlessly        | effectively manage my workflow and deliver on time |
| `* *`    | private tutor | set reminders for important deadlines and milestones   | never miss a crucial event                         |

### Use cases

(For all use cases below, the **System** is `TutorPro` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a student**

**MSS**

1. The user requests to add a specific student
2. TutorPro adds the student

   Use case ends.

**Extensions**

- 2a. The student has the same details as someone already in the list.

  - 2a1. TutorPro shows an error message.

    Use case ends.

**Use case: Delete a student**

**MSS**

1.  The user requests to list students
2.  TutorPro shows a list of students
3.  User requests to delete a specific student in the list
4.  TutorPro deletes the student

    Use case ends.

**Extensions**

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. TutorPro shows an error message.

  Use case resumes at step 2.

- 3b. The given student does not exist in the list.

  - 3b1. TutorPro displays an error message.

  Use case resumes at step 2.

**Use case: Find a student**

**MSS**

1. The user requests to find a specific student.
2. TutorPro displays a list of students whose details match the user input.

   Use case ends.

**Extensions**

- 2a. The specified student does not exist in the list.

  - 2a1. TutorPro shows an error message.

    Use case ends.

**Use case: Update a student's progress/particulars**

**MSS**

1. The user requests to update a specific student’s progress/particulars.
2. TutorPro updates the student’s progress/particulars.
3. TutorPro displays the updated information to the user for verification.

   Use case ends.

**Extensions**

- 2a. The specified student does not exist in the list.

  - 2a1. TutorPro shows an error message.

    Use case ends.

- 2b. The given category is not one of the available categories.

  - 2b1. TutorPro shows an error message.

    Use case ends.

- 2c. Given new information to update does not match the input format for the particular category.

  - 2c1. TutorPro shows an error message.

    Use case ends.

**Use case: Setting a reminder**

**MSS**

1. The user requests to set a reminder.
2. TutorPro sets the reminder.
3. TutorPro displays a list of currently scheduled reminders.

   Use case ends.

**Extensions**

- 2a. The given date or time is in an incorrect format.

  - 2a1. TutorPro shows an error message.

    Use case ends.

**Use case: Create a tag**

**MSS**

1. The user requests to create a tag.
2. TutorPro creates the specified tag.
3. TutorPro displays a list of created tags.

   Use case ends.

**Extensions**

- 2a. The specified tag to create already exists.

  - 2a1. TutorPro shows an error message.

    Use case ends.

**Use case: Tag a student**

**MSS**

1. The user requests to tag a student.
2. TutorPro tags the student.
3. TutorPro displays the list of students who have the same tag.

   Use case ends.

**Extensions**

- 2a. The specified student does not exist in the list.

  - 2a1. TutorPro displays an error message.

    Use case ends.

- 2b. The specified tag does not exist.

  - 2b1. TutorPro displays an error message.

    Use case ends.

**Use case: Display the user's schedule**

**MSS**

1. The user requests to display their current schedule.
2. TutorPro displays the user’s current schedule

   Use case ends.

**Extensions**

- 2a. The user has nothing in their schedule.

  - 2a1. TutorPro shows an error message.

    Use case ends.

**Use case: Set a recurring event**

**MSS**

1. The user requests to set an event that recurs weekly.
2. TutorPro sets the event.
3. TutorPro displays the list of the user’s weekly recurring events.

   Use case ends.

**Extensions**

- 2a. Given day(s) is/are in the wrong input format.

  - 2a1. TutorPro shows an error message.

    Use case ends.

- 2b. Given student does not exist in the list.

  - 2b1. TutorPro shows an error message.

    Use case ends.

### Non-Functional Requirements

1.  Security:

- The application must ensure secure storage and transmission of sensitive student data, such as grades and contact information, adhering to industry-standard _encryption_ protocols.
- Access to student records must be restricted to authorized users only, with role-based access control mechanisms in place.

2. Privacy:

- The application should comply with relevant privacy regulations (e.g., GDPR, HIPAA) to safeguard students' personal information.
- Student data should only be accessible to the student, their assigned tutor, and authorized administrative staff.

3. Scalability:

- The system must be able to handle a growing number of students and tutors without a significant decrease in performance.
- It should support concurrent access by multiple users without degradation in response time or system stability.

4. Reliability:

- The application should have minimal downtime and be available for use during critical academic periods, such as exam periods or assignment deadlines.
- It should have mechanisms in place to recover from failures gracefully, ensuring _data integrity_ and continuity of service.

5. Interoperability:

- The application should be compatible with various devices and _operating systems_ commonly used by both tutors and students, such as laptops, tablets, and smartphones.
- It should support integration with other academic systems or tools, such as learning management systems or scheduling software.

6. Usability:

- The application interface should be intuitive and user-friendly, requiring minimal training for tutors and students to navigate and use effectively.
- It should provide clear instructions and guidance for inputting and accessing academic information and contact details.

7. Performance:

- The system should respond promptly to user actions, such as loading student profiles, updating grades, or scheduling tutoring sessions, aiming for sub-second response times.
- It should efficiently manage database queries and data retrieval to prevent delays in accessing information.

8. Maintainability:

- The application codebase should be well-documented and _modular_, facilitating ease of maintenance and future enhancements.
- It should support version control and have mechanisms for bug tracking and resolution.

9. Accessibility:

- The application interface should be accessible to users with disabilities, following web accessibility guidelines such as WCAG.
- It should support alternative input methods, screen readers, and keyboard navigation for users with visual or motor impairments.

10. Backup and Disaster Recovery:

- Regular backups of student data should be performed, with procedures in place for data restoration in case of accidental deletion or system failure.
- The application should have disaster recovery measures to ensure minimal data loss and service disruption in the event of server or infrastructure failures.

### Glossary

- **Encryption** - The process of converting information or data into a code, especially to prevent unauthorized access.
- **Data integrity** - The accuracy, completeness, and quality of data as it’s maintained over time and across formats.
- **Operating system** - Mainstream operating systems are: Windows, Linux, Unix, MacOS
- **Modular** - By modularizing a codebase you will define more clear boundaries between different clusters of objects that make up a screen of feature.

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.


### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
