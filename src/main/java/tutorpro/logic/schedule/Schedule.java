package tutorpro.logic.schedule;

import java.util.ArrayList;

public class Schedule {
    static ArrayList<Event> events;

    public Schedule() {
        if (events == null) {
            events = new ArrayList<Event>();
        }
    }


}
