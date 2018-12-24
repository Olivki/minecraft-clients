package se.proxus.rori.events.list.player;

import se.proxus.rori.events.Event;

/**
 * 
 * Can be found in the EntityClientPlayerMP class.
 * 
 */
public class EventPostUpdate extends Event {

	public EventPostUpdate() {
		super("Event Post Update");
	}

	public long getCurrentMilliseconds() {
		return getNanoTime() / 1000000;
	}

	public long getNanoTime() {
		return System.nanoTime();
	}
}