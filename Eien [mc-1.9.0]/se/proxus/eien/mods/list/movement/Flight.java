package se.proxus.eien.mods.list.movement;

import org.lwjgl.input.Keyboard;

import se.proxus.eien.events.EventHandler;
import se.proxus.eien.events.EventManager;
import se.proxus.eien.events.list.player.EventUpdate;
import se.proxus.eien.mods.Mod;
import se.proxus.eien.mods.ModCategory;
import se.proxus.eien.tools.Tools;

public class Flight extends Mod {

	public Flight() {
		super("Flight", ModCategory.MOVEMENT, false);
	}

	@Override
	public void init() {
		setValue("Description", "Makes you fly like a blackman.", false);
		addValue("Speed", 1.0D, "The speed of the flight mod.", 2.5D, true, false);
		addValue("Fall Damage", false, "Whether you want to take fall damage when landing.", true,
				false);
	}

	@Override
	public void onEnable() {
		EventManager.registerListener(this);
	}

	@Override
	public void onDisable() {
		EventManager.unregisterListener(this);
	}

	@EventHandler
	public void onEventUpdate(final EventUpdate event) {

		double tempSpeed = getValue("Speed").getDouble();
		float speed = (float) tempSpeed;

		Tools.getPlayer().motionX = 0.0D;
		Tools.getPlayer().motionY = 0.0D;
		Tools.getPlayer().motionZ = 0.0D;
		getClient().getMinecraft().thePlayer.jumpMovementFactor = speed / 2;
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && getClient().getMinecraft().inGameHasFocus) {
			Tools.getPlayer().motionY = Tools.getPlayer().motionY + (speed / 2 + 0.2F);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && getClient().getMinecraft().inGameHasFocus) {
			Tools.getPlayer().motionY = Tools.getPlayer().motionY - speed;
			if (!getValue("Fall Damage").getBoolean()) {
				Tools.getPlayer().onGround = true;
			}
		}

	}
}