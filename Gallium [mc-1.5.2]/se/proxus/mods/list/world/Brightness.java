package se.proxus.mods.list.world;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class Brightness extends Mod {

    public Brightness(final Gallium client) {
	super("Brightness", ModCategory.WORLD, false, client);
    }

    @Override
    public void init() {
	setDescription("Makes the world brighter.");
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
	registerSetting(0,
		getClient().getMinecraft().entityRenderer.lightmapTexture,
		true, true);
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
	getClient().getMinecraft().entityRenderer.lightmapTexture = (Integer) getSetting(0);
    }

    @EventHandler
    public void onEventUpdate(final EventUpdate event) {
	getClient().getMinecraft().entityRenderer.lightmapTexture = 1;
    }
}