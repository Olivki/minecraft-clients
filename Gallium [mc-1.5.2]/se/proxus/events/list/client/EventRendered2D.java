package se.proxus.events.list.client;

import net.minecraft.src.FontRenderer;
import se.proxus.events.Event;

public class EventRendered2D extends Event {

    private FontRenderer font;

    public EventRendered2D(final FontRenderer font) {
	super("Rendered 2D");
	setFont(font);
    }

    public FontRenderer getFont() {
	return font;
    }

    public void setFont(final FontRenderer font) {
	this.font = font;
    }
}