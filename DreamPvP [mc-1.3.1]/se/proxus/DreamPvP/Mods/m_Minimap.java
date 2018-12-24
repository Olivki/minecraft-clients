package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;
import net.minecraft.src.*;

public class m_Minimap extends Base_Mod {

	public m_Minimap() {
		super('7', "Radar", "Shows up a radar / minimap.", KEY_NONE, "Gui", "[§eG§r] ");
	}

	@Override
	public void onEnabled() {
		dp.ingame.guiArray.remove("§" + getCol() + getExtra() + getName());
	}

	@Override
	public void onDisabled() {
		
	}
}