package se.proxus.hacks.hacks;

import se.proxus.hacks.*;

public class h_Miner extends Base_Hack {

	public h_Miner() {
		super('7', "Miner", "Makes you mine faster.", "NONE", "World", true);
		this.hacks.addHack(this);
		this.setOption(Base_Options.MINE_SPEED, Float.valueOf(0.3F));
		this.setOption(Base_Options.MINE_DELAY, Integer.valueOf(5));
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		
	}

	@Override
	public void onUpdate() {
		
	}
}