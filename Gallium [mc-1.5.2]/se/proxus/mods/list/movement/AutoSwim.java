package se.proxus.mods.list.movement;

import se.proxus.Gallium;
import se.proxus.events.EventHandler;
import se.proxus.events.list.player.EventUpdate;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;

public class AutoSwim extends Mod {

    public AutoSwim(final Gallium client) {
	super("Auto Swim", ModCategory.MOVEMENT, false, client);
    }

    @Override
    public void init() {
	setDescription("Automagically swims for you.");
	registerSetting(0, 1.1F, "Speed", 10.0D, true, false, true);
    }

    @Override
    public void onEnable() {
	getClient().getEvents().registerListener(this);
    }

    @Override
    public void onDisable() {
	getClient().getEvents().unregisterListener(this);
	getClient().getMinecraft().gameSettings.keyBindJump.pressed = true;
    }

    @EventHandler
    public void onEventUpdate(final EventUpdate event) {
	if (getClient().getMinecraft().thePlayer.isInWater()) {
	    getClient().getMinecraft().thePlayer.jumpMovementFactor *= (Float) getSetting(0);
	    getClient().getMinecraft().gameSettings.keyBindJump.pressed = true;
	    getClient().getPlayer().setSprinting(true);

	    if (getClient().getMinecraft().thePlayer.isCollidedHorizontally) {
		getClient().getPlayer().setMotionY(0.3D);
		getClient().getMinecraft().gameSettings.keyBindJump.pressed = false;
	    }
	}
    }
}