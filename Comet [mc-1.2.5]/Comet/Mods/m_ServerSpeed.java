package Comet.Mods;

import Comet.*;
import Comet.Utils.Hooks.Tickable;
import net.minecraft.src.*;

public class m_ServerSpeed extends Base_Mod implements Tickable {

	public m_ServerSpeed() {
		super(Base_Enum.ServerSpeed);
	}

	@Override
	public void onStart() {
		enabled = true;
		Comet.utils.addToTick(this);
		Comet.ingame.array.add("Server speed");
	}

	@Override
	public void onStop() {
		enabled = false;
		Comet.utils.removeFromTick(this);
		Comet.ingame.array.remove("Server speed");
	}

	@Override
	public void onTick() {
		if(!mc.thePlayer.isWet()) {
			for(int ss = 0; ss < 10; ss++) {
				Comet.utils.sendPacket(new Packet10Flying(mc.thePlayer.onGround));
			}
		}
	}

}
