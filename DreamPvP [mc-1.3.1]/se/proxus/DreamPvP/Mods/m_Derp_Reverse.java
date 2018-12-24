package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

import java.util.Random;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Inheritance.cEntityClientPlayerMP;
import se.proxus.DreamPvP.Interfaces.*;

public class m_Derp_Reverse extends Base_Mod implements Updates {

	public float derpDelay = 0;

	public m_Derp_Reverse() {
		super('7', "Derp_backward", "Makes you look reversed.", KEY_NONE, "Derp", "[§eP§r] ");
	}

	@Override
	public void onEnabled() {
		dp.interfaces.updateArray.add(this);
	}

	@Override
	public void onDisabled() {
		dp.interfaces.updateArray.remove(this);
	}

	@Override
	public void onUpdate() {
		try {
			cEntityClientPlayerMP thePlayer = dp.mc.thePlayer;
			derpDelay++;

			if (derpDelay >= 6) {
				dp.utils.sendPacket(new Packet12PlayerLook(thePlayer.rotationYaw - 180, thePlayer.rotationPitch, thePlayer.onGround));
				derpDelay = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}