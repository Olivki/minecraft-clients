package Comet.Mods;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Comet.*;
import Comet.Utils.Utils;
import Comet.Utils.Hooks.Tickable;
import net.minecraft.src.*;

public class m_KillAura extends Base_Mod implements Tickable {

	public static float delay;
	private Random timerdelay = new Random();

	public m_KillAura() {
		super(Base_Enum.KillAura);
	}

	@Override
	public void onStart() {
		Comet.utils.addToTick(this);
		enabled = true;
		Comet.ingame.array.add("Kill aura");
	}

	@Override
	public void onStop() {
		Comet.utils.removeFromTick(this);
		enabled = false;
		Comet.ingame.array.remove("Kill aura");
	}

	@Override
	public void onTick() {
		Utils utils = new Utils();
		
		for (int a = 0; a < mc.theWorld.playerEntities.size(); a++) {
			EntityPlayer e = (EntityPlayer)mc.theWorld.playerEntities.get(a);
			String username = e.username;

			if (e != mc.thePlayer &&  mc.thePlayer.canEntityBeSeen(e) && e.isDead != true && !mc.comet.settings.friends.contains(username)) {
				if (mc.thePlayer.getDistanceToEntity(e) <= getRange() + 4) {                                
					delay++;
					if(delay > timerdelay.nextFloat() + 4.1F) {
						faceEntity(e);
						if(mc.thePlayer.getDistanceToEntity(e) <= getRange()) {
							utils.sendPacket(new Packet7UseEntity(mc.thePlayer.entityId, e.entityId, 1));
							mc.thePlayer.swingItem();
							delay = 0;
						}
					}
				}
			}
		}
	}

	public static float getRange() {
		return mc.comet.settings.auraRange;
	}

	public static void faceEntity(Entity I1) {
		Utils utils = new Utils();
		if (I1 != mc.thePlayer && !I1.isDead) {
			if (mc.thePlayer.getDistanceToEntity(I1) < getRange()) {
				double d = I1.posX - mc.thePlayer.posX;
				double d1 = I1.posY - mc.thePlayer.posY;
				double d2 = I1.posZ - mc.thePlayer.posZ;
				double d3 = (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight()) - (I1.posY + (double)I1.getEyeHeight());
				double d4 = MathHelper.sqrt_double(d * d + d2 * d2);
				float yaw = (float)((Math.atan2(d2, d) * 180D) / Math.PI) - 90F;
				float pitch = (float)(((Math.atan2(d3, d4) * 180D) / Math.PI));
				utils.sendPacket(new Packet12PlayerLook(yaw, pitch, mc.thePlayer.onGround));
			}
		}
	}

}
