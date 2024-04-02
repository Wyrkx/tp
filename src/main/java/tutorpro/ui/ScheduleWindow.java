package tutorpro.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import tutorpro.commons.core.LogsCenter;
public class ScheduleWindow extends UiPart<Stage> {

    //public static final String USERGUIDE_URL = "https://tarinpairor.github.io/tp/";
    public static final String SCHEDULE_MESSAGE = "Here is your schedule!";

    private static final Logger logger = LogsCenter.getLogger(ScheduleWindow.class);
    private static final String FXML = "ScheduleWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label scheduleMessage;

    /**
     * Creates a new ScheduleWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public ScheduleWindow(Stage root) {
        super(FXML, root);
        scheduleMessage.setText(SCHEDULE_MESSAGE);
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

    /**
     * Copies the URL to the user guide to the clipboard.
     */
//    @FXML
//    private void copyUrl() {
//        final Clipboard clipboard = Clipboard.getSystemClipboard();
//        final ClipboardContent url = new ClipboardContent();
//        url.putString(USERGUIDE_URL);
//        clipboard.setContent(url);
//    }
}
