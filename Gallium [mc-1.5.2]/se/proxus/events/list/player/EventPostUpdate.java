package se.proxus.events.list.player;

import se.proxus.events.Event;

public class EventPostUpdate extends Event {

    public EventPostUpdate() {
	super("Post Update");
    }

    public long getCurrentMilliseconds() {
	return this.getNanoTime() / 1000000;
    }

    public long getNanoTime() {
	return System.nanoTime();
    }
}