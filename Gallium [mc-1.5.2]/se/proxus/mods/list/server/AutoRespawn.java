package se.proxus.mods.list.server;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class AutoRespawn extends Mod {

    public AutoRespawn(final Gallium client) {
	super("Auto Respawn", ModCategory.SERVER, true, client);
    }

    @Override
    public void init() {
	setDescription("Tries to respawn you once dead.");
	setState(true);
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
    public void onEventUpdate(final EventUpdate event) {
	if (getClient().getMinecraft().thePlayer.isDead)
	    getClient().getMinecraft().thePlayer.respawnPlayer();
    }
}