package se.proxus.mods.list.server;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class AutoEquip extends Mod {

    public AutoEquip(final Gallium client) {
	super("Auto Equip", ModCategory.SERVER, true, client);
    }

    @Override
    public void init() {
	setDescription("Currently not working.");
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