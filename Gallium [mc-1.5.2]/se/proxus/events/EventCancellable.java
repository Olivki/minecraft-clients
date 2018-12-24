package se.proxus.events;

public class EventCancellable extends Event {
	
	public EventCancellable(String name, EventPriority priority) {
		super(name, priority);
	}

	private boolean cancelled;

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}