package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

public class m_xRay extends Base_Mod {

	public m_xRay() {
		super('7', "xRay", "Renders blocks that you can't normally see.", KEY_X, "World", "[§eW§r] ");
	}

	@Override
	public void onEnabled() {
		dp.mc.gameSettings.gammaSetting = dp.mc.gameSettings.gammaSetting + 10;
		dp.mc.renderGlobal.loadRenderers();
	}

	@Override
	public void onDisabled() {
		dp.mc.gameSettings.gammaSetting = dp.mc.gameSettings.gammaSetting - 10;
		dp.mc.renderGlobal.loadRenderers();
	}
}