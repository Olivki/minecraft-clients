package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

import java.util.Random;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;

public class m_Derp_Headless extends Base_Mod implements Updates {

	public float derpDelay = 0;

	public m_Derp_Headless() {
		super('7', "Derp_headless", "Makes your head pop into your body.", KEY_NONE, "Derp", "[§eP§r] ");
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
			derpDelay++;

			if (derpDelay >= 6) {
				dp.utils.sendPacket(new Packet12PlayerLook(dp.mc.thePlayer.rotationYaw - 180, 180, dp.mc.thePlayer.onGround));
				derpDelay = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}