package se.proxus.mods.list.gui;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.client.EventRendered2D;
import se.proxus.frames.list.components.Frame;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.tools.Colours;
import se.proxus.tools.RenderMap;

public class Gui extends Mod {

    private RenderMap map;

    public Gui(final Gallium client) {
	super("Ingame Gui", ModCategory.GUI, true, client);
    }

    @Override
    public void init() {
	setDescription("Renders the ingame gui.");
	registerSetting(0, false, "White ArrayList", 0.0D, true, false, true);
	setState(true);
	setHidden(true);
	checkState();
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
    }

    @EventHandler
    public void onEventRendered2D(final EventRendered2D event) {
	if (map == null)
	    map = new RenderMap();
	map.render();
	event.getFont().drawStringWithShadow(getClient().toString(), 2, 2,
		0xFFFFFFFF);
	event.getFont().drawStringWithShadow(
		getClient().getPlayer().getLocation().toString(), 2, 12,
		0xFFFFFFFF);
	if (getClient().getPlayer().getTarget() != null)
	    event.getFont().drawStringWithShadow(
		    getClient().getPlayer().getTarget().getEntityName(), 2,
		    getClient().getResolution().getScaledHeight(), 0x50FFFFFF);
	for (int var0 = 0; var0 < getClient().getMods().getActiveMods().size(); var0++) {
	    Mod mod = getClient().getMods().getActiveMods().get(var0);
	    if (!mod.isHidden()) {
		String name = ((Boolean) getSetting(0) ? Colours.RESET : mod
			.getCategory().getColour()) + mod.getName();
		event.getFont().drawStringWithShadow(
			name,
			getClient().getResolution().getScaledWidth()
				- event.getFont().getStringWidth(name) - 2,
			3 + var0 * 10, 0xFFFFFFFF);
	    }
	}
	for (Frame frame : getClient().getFrames().getLoadedFrames())
	    if (frame.isPinned()
		    && getClient().getMinecraft().currentScreen != getClient()
			    .getFrames())
		frame.draw(getClient().getFontFactory(), -900, -900, 1.0F);
    }
}