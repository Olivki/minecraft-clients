package se.proxus.mods.list;

import se.proxus.mods.*;

public class ModFlight extends BaseMod {

	public ModFlight() {
		super("Flight", new ModInfo("Makes you fly like a nigger.", "Oliver", "NONE", false), ModType.PLAYER, false);
		this.setOption("Flight_speed", Double.valueOf(2.5D), false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		
	}
}