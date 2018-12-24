package se.proxus.mods.list;

import se.proxus.events.*;
import se.proxus.events.player.*;
import se.proxus.mods.*;

public class ModSprint extends BaseMod {

	public ModSprint() {
		super("Sprint", new ModInfo("Makes you sprint.", "Oliver", "NONE", true), ModType.PLAYER, false);
		this.setOption("Sprint_speed", Double.valueOf(1.3D), false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void registerEvents() {
		this.getEvent().registerEvent(EventUpdate.class);
	}

	@Override
	public void onEnabled() {

	}

	@Override
	public void onDisabled() {

	}

	@Override
	public void onEvent(Event var0) {
		if(this.getState()) {
			if(var0 instanceof EventUpdate) {
				if((this.wrapper.getPlayer().movementInput.moveForward > 0.0F) && !(this.wrapper.getPlayer().isSneaking())) {
					this.wrapper.getPlayer().setSprinting(true);
				}
			}
		}
	}
}