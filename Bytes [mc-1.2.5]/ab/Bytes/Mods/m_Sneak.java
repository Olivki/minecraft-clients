package ab.Bytes.Mods;

import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;

public class m_Sneak extends Base_Mod {

	public m_Sneak() {
		super('2', "Sneak", Keyboard.KEY_Z);
	}

	@Override
	public void onEnabled() {
		bs.utils.sendPacket(new Packet19EntityAction(bs.mc.thePlayer, 1));
	}

	@Override
	public void onDisabled() {
		bs.utils.sendPacket(new Packet19EntityAction(bs.mc.thePlayer, 2));
	}
}