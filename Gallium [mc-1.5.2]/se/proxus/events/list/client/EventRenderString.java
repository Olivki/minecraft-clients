package se.proxus.events.list.client;

import se.proxus.events.Event;

public class EventRenderString extends Event {

    private String text;
    private int colour;

    public EventRenderString(final String text, final int colour) {
	super("Render String");
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