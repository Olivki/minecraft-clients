package se.proxus.owari.events.list.player;

import se.proxus.owari.events.EventCancellable;

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