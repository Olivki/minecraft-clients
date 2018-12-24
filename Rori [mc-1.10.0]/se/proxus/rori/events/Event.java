package se.proxus.rori.events;

public class Event {

	private String name;

	public Event(final String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}