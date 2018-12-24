package se.proxus.rori.events.list.client;

import net.minecraft.client.gui.FontRenderer;
import se.proxus.rori.events.Event;

/**
 * 
 * @see net.minecraft.client.gui.GuiIngame#renderGameOverlay(float, boolean,
 *      int, int)
 * 
 **/
public class EventRendered2D extends Event {

	private FontRenderer font;

	public EventRendered2D(final FontRenderer font) {
		super("Event Rendered 2D");
		setFont(font);
	}

	public FontRenderer getFont() {
		return font;
	}

	public void setFont(final FontRenderer font) {
		this.font = font;
	}
}