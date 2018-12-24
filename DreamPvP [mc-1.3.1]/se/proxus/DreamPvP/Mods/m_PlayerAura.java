package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

import java.util.Random;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;

public class m_PlayerAura extends Base_Mod implements Updates {

	public float hitDelay = 0, auraDelay = 6;

	public m_PlayerAura() {
		super('7', "Aura", "Makes you hit other players.", KEY_F, "Aura", "[§eA§r] ");
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
		for(Object o : dp.mc.theWorld.playerEntities) {
			EntityPlayer ep = (EntityPlayer)o;
			EntityPlayerSP e = dp.mc.thePlayer;
			boolean isFriend = dp.mc.dp.settings.friendArray.contains(ep.username);
			boolean checks = e.canEntityBeSeen(ep) && e.getDistanceToEntity(ep) <= 3.5F && ep != e && !isFriend;

			if(e.getDistanceToEntity(ep) <= 8) {
				hitDelay++;
				if(checks && hitDelay > auraDelay) {
					silentAimbot(dp.utils.getNearestEntityPlayer());

					if(dp.bModList.autoTool.getState()) {
						getBestWeapon(dp.utils.getNearestEntityPlayer());
					}

					e.swingItem();
					dp.mc.playerControllerMP.attackEntity(e, dp.utils.getNearestEntityPlayer());
					dp.utils.log("Hitting the entity player \"" + ep.username + "\".");
					hitDelay = 0;
					break;
				}
			}
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

	public void getBestWeapon(Entity e) {
		EntityPlayerSP entityplayersp = dp.mc.thePlayer;
		InventoryPlayer inventoryplayer = entityplayersp.inventory;
		int i = 1;
		int item = -1;

		for (int holder = 0; holder < 9; holder++) {
			ItemStack itemstack = inventoryplayer.mainInventory[holder];

			if (itemstack == null) {
				continue;
			}

			int dmgVsEntity = itemstack.getDamageVsEntity(e);

			if (dmgVsEntity > i) {
				item = holder;
				i = dmgVsEntity;
			}
		}

		if (!(item < 0)) {
			dp.mc.thePlayer.inventory.currentItem = item;
			return;
		}
	}
}