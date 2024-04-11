package tutorpro.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tutorpro.commons.core.LogsCenter;
import tutorpro.logic.Logic;

/**
 * Window showing the Schedule.
 */
public class ScheduleWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ScheduleWindow.class);
    private static final String FXML = "ScheduleWindow.fxml";
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ReminderListPanel reminderListPanel;

    @FXML
    private StackPane reminderListPanelPlaceholder;

    /**
     * Creates a new ScheduleWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public ScheduleWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ScheduleWindow.
     */
    public ScheduleWindow() {
        this(new Stage());
    }

    /**
     * Shows the schedule window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing your schedule.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        reminderListPanel = new ReminderListPanel(logic.getTruncatedFilteredSchedule());
        reminderListPanelPlaceholder.getChildren().add(reminderListPanel.getRoot());
    }
    /**
     * Returns true if the schedule window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the schedule window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the schedule window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
