package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

public class m_Bright extends Base_Mod {
	
	public float brightness = 0.0F;

	public m_Bright() {
		super('7', "Bright", "Lightens up entites and blocks.", KEY_C, "World", "[§eW§r] ");
	}

	@Override
	public void onEnabled() {
		brightness = 10.0F;
	}

	@Override
	public void onDisabled() {
		brightness = 0.0F;
	}
}