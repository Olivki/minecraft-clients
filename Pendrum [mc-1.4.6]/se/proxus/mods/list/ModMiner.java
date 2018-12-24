package se.proxus.mods.list;

import se.proxus.mods.*;

public class ModMiner extends BaseMod {

	public ModMiner() {
		super("Miner", new ModInfo("Makes you mine faster.", "Oliver", "NONE", true), ModType.WORLD, false);
		this.setOption("Miner_speed", Float.valueOf(0.3F), false);
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