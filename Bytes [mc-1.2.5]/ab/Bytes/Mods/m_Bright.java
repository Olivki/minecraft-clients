package ab.Bytes.Mods;

import org.lwjgl.input.Keyboard;

public class m_Bright extends Base_Mod {
	
	public static float brightness;

	public m_Bright() {
		super('e', "Bright", Keyboard.KEY_C);
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