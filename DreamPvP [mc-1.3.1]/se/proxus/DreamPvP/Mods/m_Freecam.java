package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;
import se.proxus.DreamPvP.Interfaces.*;
import net.minecraft.src.*;

public class m_Freecam extends Base_Mod {

	public m_Freecam() {
		super('7', "Freecam", "Makes it so you can noclip thru stuff.", KEY_NONE, "Player", "[§eP§r] ");
	}

	@Override
	public void onEnabled() {
		dp.mc.thePlayer.noClip = true;
	}

	@Override
	public void onDisabled() {
		dp.mc.thePlayer.noClip = false;
	}
}