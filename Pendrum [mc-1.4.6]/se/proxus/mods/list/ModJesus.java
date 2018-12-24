package se.proxus.mods.list;

import net.minecraft.src.*;
import se.proxus.events.*;
import se.proxus.events.player.*;
import se.proxus.events.world.*;
import se.proxus.mods.*;

public class ModJesus extends BaseMod {

	public float brightness = 0.0F;

	public ModJesus() {
		super("Jesus", new ModInfo("Makes you do stuff in water.", "Vesta", "NONE", true), ModType.WORLD, false);
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
				if(this.wrapper.getPlayer().isInWater()) {
					this.wrapper.getPlayer().motionY = 0.28;
					this.wrapper.getPlayer().jumpMovementFactor = (float)(this.wrapper.getPlayer().jumpMovementFactor * 2.59);
				}
			}
		}
	}
}