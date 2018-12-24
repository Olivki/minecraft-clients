package se.proxus.mods.list.movement;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class Sprint extends Mod {

    public Sprint(final Gallium client) {
	super("Sprint", ModCategory.MOVEMENT, false, client);
    }

    @Override
    public void init() {
	setDescription("Makes you sprint.");
	registerSetting(0, 0.25F, "Speed", 1.0D, true, false, true);
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
	getClient().getPlayer().setSprinting(false);
    }

    @EventHandler
    public void onEventUpdate(final EventUpdate event) {
	if (getClient().getMinecraft().thePlayer.movementInput.moveForward > 0.0F
		&& !getClient().getMinecraft().thePlayer.isCollidedHorizontally
		&& !getClient().getPlayer().isSneaking()) {
	    getClient().getPlayer().setSprinting(true);
	    getClient().getMinecraft().thePlayer.landMovementFactor = (Float) getSetting(0) / 2;
	}
    }
}