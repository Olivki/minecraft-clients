package se.proxus.mods.list;

import net.minecraft.src.MathHelper;
import se.proxus.events.Event;
import se.proxus.events.player.EventUpdate;
import se.proxus.mods.*;

public class ModStep extends BaseMod {

	public ModStep() {
		super("Step", new ModInfo("Steps up blocks.", "Oliver", "NONE", true), ModType.PLAYER, false);
		this.setOption("Step_height", Float.valueOf(1.5F), false);
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
		this.wrapper.getPlayer().stepHeight = 0.5F;
	}

	@Override
	public void onEvent(Event var0) {
		if(this.getState()) {
			if(var0 instanceof EventUpdate) {
				if(((Float)this.getOption("Step_height")).floatValue() == 0) {
					this.wrapper.getPlayer().stepHeight = 0.5F;
				} else {
					this.wrapper.getPlayer().stepHeight = ((Float)this.getOption("Step_height")).floatValue();
				}
			}
		} 
	}
}