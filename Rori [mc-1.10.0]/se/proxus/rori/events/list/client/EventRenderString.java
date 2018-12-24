package se.proxus.rori.events.list.client;

import se.proxus.rori.events.Event;

/**
 * 
 * Can be found in the FontRenderer class.
 * 
 */
public class EventRenderString extends Event {

	private String text;
	private int colour;

	public EventRenderString(final String text, final int colour) {
		super("Event Render String");
		setText(text);
		setColour(colour);
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public int getColour() {
		return colour;
	}

	public void setColour(final int colour) {
		this.colour = colour;
	}
}