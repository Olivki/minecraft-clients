package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

public class m_Sprint extends Base_Mod {

	public m_Sprint() {
		super('7', "Sprint", "Makes you sprint.", KEY_NONE, "World", "[§eW§r]");
	}

	@Override
	public void onEnabled() {
		dp.mc.thePlayer.setSprinting(true);
	}

	@Override
	public void onDisabled() {
		dp.mc.thePlayer.setSprinting(false);
	}
}