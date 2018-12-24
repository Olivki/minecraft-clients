package ab.Bytes.Mods;

import org.lwjgl.input.Keyboard;

public class m_xRay extends Base_Mod {
	
	public static float brightness;

	public m_xRay() {
		super('7', "xRay", Keyboard.KEY_X);
	}

	@Override
	public void onEnabled() {
		bs.utils.refreshWorld();
		brightness = 10.0F;
	}

	@Override
	public void onDisabled() {
		bs.utils.refreshWorld();
		brightness = 0.0F;
	}
}