package se.proxus.events.list.player;

import se.proxus.events.EventCancellable;
import se.proxus.events.EventPriority;

public class EventUpdate extends EventCancellable {

    public EventUpdate() {
	super("Update", EventPriority.MEDIUM);

    }

    public long getCurrentMilliseconds() {
	return this.getNanoTime() / 1000000;
    }

    public long getNanoTime() {
	return System.nanoTime();
    }
}