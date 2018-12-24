package Comet.Mods;

import Comet.*;
import net.minecraft.src.*;

public class m_Sneak extends Base_Mod {
	
	public m_Sneak() {
		super(Base_Enum.Sneak);
	}

	@Override
	public void onStart() {
		enabled = true;
		Comet.utils.sendPacket(new Packet19EntityAction(mc.thePlayer, 1));
		Comet.ingame.array.add("Sneak");
	}

	@Override
	public void onStop() {
		enabled = false;
		Comet.utils.sendPacket(new Packet19EntityAction(mc.thePlayer, 2));
		Comet.ingame.array.remove("Sneak");
	}

}
