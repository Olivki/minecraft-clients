package se.proxus.mods.list.gui;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.client.EventRendered2D;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.tools.Colours;

public class Chat extends Mod {

    public Chat(final Gallium client) {
	super("Chat", ModCategory.GUI, true, client);
    }

    @Override
    public void init() {
	setDescription("Renders a TTF chat with borders and stuff.");
	setState(true);
    }

    @Override
    public void onEnable() {
	
    }

    @Override
    public void onDisable() {
	
    }
}