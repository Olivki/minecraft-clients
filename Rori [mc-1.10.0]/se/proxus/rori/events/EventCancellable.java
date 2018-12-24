package se.proxus.rori.events;

public class EventCancellable extends Event {

	public EventCancellable(final String name) {
		super(name);
	}

	private boolean cancelled;

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}
}