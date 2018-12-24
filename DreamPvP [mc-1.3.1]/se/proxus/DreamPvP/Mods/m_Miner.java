package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

public class m_Miner extends Base_Mod {
	
	public float mineSpeed = 0.3F;

	public m_Miner() {
		super('7', "Miner", "Makes you mine faster.", KEY_M, "World", "[§eW§r] ");
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		
	}
}