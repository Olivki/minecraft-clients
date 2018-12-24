package se.proxus.events;

public class Event {

    private String name;

    private EventPriority priority;

    public Event(final String name, final EventPriority priority) {
	setName(name);
	setPriority(priority);
    }

    public Event(final String name) {
	this(name, EventPriority.MEDIUM);
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public EventPriority getPriority() {
	return priority;
    }

    public void setPriority(final EventPriority priority) {
	this.priority = priority;
    }
}