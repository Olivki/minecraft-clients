package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

import java.util.Random;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;

public class m_MobAura extends Base_Mod implements Updates {

	public float hitDelay = 0, auraDelay = 11;

	public m_MobAura() {
		super('7', "Mob_Aura", "Makes you hit other mobs..", KEY_L, "Aura", "[§eA§r] ");
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
		for(Object o : dp.mc.theWorld.loadedEntityList) {
			Entity em = (Entity)o;
			EntityPlayerSP e = dp.mc.thePlayer;
			boolean checks = e.canEntityBeSeen(em) && e.getDistanceToEntity(em) <= 4 && em instanceof EntityMob;

			if(e.getDistanceToEntity(em) <= 8) {
				hitDelay++;
				if(checks && hitDelay > auraDelay) {
					silentAimbot(dp.utils.getNearestEntityMob());
					
					if(dp.bModList.autoTool.getState()) {
						getBestWeapon(dp.utils.getNearestEntityMob());
					}
					
					e.swingItem();
					dp.mc.playerControllerMP.attackEntity(e, dp.utils.getNearestEntityMob());
					dp.utils.log("Hit the entity mob \"" + em.getEntityName() + "\".");
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