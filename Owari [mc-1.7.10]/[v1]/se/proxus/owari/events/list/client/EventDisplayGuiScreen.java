package se.proxus.owari.events.list.client;

import net.minecraft.client.gui.GuiScreen;
import se.proxus.owari.events.EventCancellable;

/**
 * 
 * @see net.minecraft.client.gui.GuiIngame#renderGameOverlay(float, boolean,
 *      int, int)
 * 
 **/
public class EventDisplayGuiScreen extends EventCancellable {

	private GuiScreen screen;

	public EventDisplayGuiScreen(final GuiScreen screen) {
		super("Event Display Gui Screen");
		setScreen(screen);
	}

	public GuiScreen getScreen() {
		return screen;
	}

	public void setScreen(final GuiScreen screen) {
		this.screen = screen;
	}
}