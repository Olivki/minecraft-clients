package se.proxus.mods.list.none;

import se.proxus.Gallium;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class Panels extends Mod {

    public Panels(final Gallium client) {
	super("Panels", ModCategory.NONE, true, client);
    }

    @Override
    public void init() {
	setKey("RSHIFT", false);
	setHidden(true);
    }

    @Override
    public void onEnable() {
	getClient().getMinecraft().displayGuiScreen(getClient().getFrames());
	toggle();
    }

    @Override
    public void onDisable() {

    }
}