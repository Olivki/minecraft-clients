package se.proxus.kanon.event4j.events.render;

import se.proxus.kanon.event4j.events.EventCancellable;

public class EventRenderString extends EventCancellable {

    private String text;
    private int colour;

    public EventRenderString(final String text, final int colour) {
        this.text = text;
        this.colour = colour;
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