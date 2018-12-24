package se.proxus.eien.events.list.player;

import se.proxus.eien.events.EventCancellable;

public class EventUpdate extends EventCancellable {

	public EventUpdate() {
		super("Event Update");

	}

	public long getCurrentMilliseconds() {
		return getNanoTime() / 1000000;
	}

	public long getNanoTime() {
		return System.nanoTime();
	}
}