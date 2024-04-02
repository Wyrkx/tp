package tutorpro.logic.schedule;

public class Event {
    String description;
    String startTime;
    String endTime;

    public Event(String description, String startTime, String endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(String description, String startTime) {
        this.description = description;
        this.startTime = startTime;
    }
}
