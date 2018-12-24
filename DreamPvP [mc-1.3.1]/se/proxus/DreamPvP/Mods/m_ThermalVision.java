package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

public class m_ThermalVision extends Base_Mod {

	public m_ThermalVision() {
		super('7', "Thermal_Vision", "I honestly duno.", KEY_NONE, "Player", "[§eP§r] ");
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