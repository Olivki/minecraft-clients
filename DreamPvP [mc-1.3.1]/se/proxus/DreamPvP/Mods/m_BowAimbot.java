package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

import java.util.Random;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;

public class m_BowAimbot extends Base_Mod implements Updates {

	public m_BowAimbot() {
		super('7', "Bow_Aimbot", "Makes you aim at players serversidedely with a bow.", KEY_NONE, "Aura", "[§eA§r] ");
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
			if(dp.mc.thePlayer.getCurrentEquippedItem().itemID == Item.bow.shiftedIndex) {
				for(Object o : dp.mc.theWorld.playerEntities) {
					EntityPlayer ep = (EntityPlayer)o;
					EntityPlayerSP e = dp.mc.thePlayer;
					boolean isFriend = dp.mc.dp.settings.friendArray.contains(ep.username);
					boolean checks = e.canEntityBeSeen(ep) && ep != e && !isFriend && e.isUsingItem();

					if(e.getDistanceToEntity(ep) <= 25 && checks) {
						silentAimbot(dp.utils.getNearestEntityPlayer());
						break;
					}
				}
			}
		} catch (Exception e) {

		}
	}

	public void silentAimbot(Entity e) {
		if(!dp.utils.isFriend(e.getEntityName())) {
			if(dp.settings.aimbotMode == 1) {
				EntityPlayerSP tp = dp.mc.thePlayer;
				double x = e.posX - tp.posX, y = e.posY - tp.posY, z = e.posZ - tp.posZ;
				double var1 = (tp.posY + (double)tp.getEyeHeight()) - (e.posY + (double)e.getEyeHeight()), var2 = MathHelper.sqrt_double(x * x + z * z);
				float var3 = (float)((Math.atan2(z, x) * 180D) / Math.PI) - 90F, pitch = (float)(((Math.atan2(var1, var2) * 180D) / Math.PI));             
				double var4 = tp.boundingBox.minY - (tp.onGround ? 0.0001D : 0.0D);

				dp.utils.sendPacket((new Packet13PlayerLookMove(tp.posX, var4, tp.posY, tp.posZ, var3, pitch, tp.onGround)));
			} else if(dp.settings.aimbotMode == 2) {
				EntityPlayerSP tp = dp.mc.thePlayer;
				tp.faceEntity(e, 360F, 360F);
			}

			dp.utils.log("Facing entity \"" + e.getEntityName() + "\".");
		}
	}
}