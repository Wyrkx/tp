package tutorpro.model.schedule;

import java.util.Comparator;

/**
 * A comparator for the Reminder class.
 */
public class ReminderComparator implements Comparator<Reminder> {
    @Override
    public int compare(Reminder o1, Reminder o2) {
        if (o1.getTime().isBefore(o2.getTime())) {
            return -1;
        } else if (o1.getTime().isEqual(o2.getTime())) {
            return 0;
        } else {
            return 1;
        }
    }
}
