package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;
import net.minecraft.src.*;

public class m_Sneak extends Base_Mod {

	public m_Sneak() {
		super('7', "Sneak", "Makes your player sneak.", KEY_Z, "Player", "[§eN§r] ");
	}

	@Override
	public void onEnabled() {
		dp.utils.sendPacket(new Packet19EntityAction(dp.mc.thePlayer, 1));
	}

	@Override
	public void onDisabled() {
		dp.utils.sendPacket(new Packet19EntityAction(dp.mc.thePlayer, 2));
	}
}