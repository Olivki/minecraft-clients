package se.proxus.DreamPvP.Mods;

import static org.lwjgl.input.Keyboard.*;

import java.util.ArrayList;

import paulscode.sound.Vector3D;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;
import se.proxus.DreamPvP.Utils.Placeholders.u_NBlock;

public class m_Nuker extends Base_Mod implements ClickBlock, Renderer, Updates {
	
	public static ArrayList<u_NBlock> blockArray = new ArrayList<u_NBlock>();
	public int nukeID, fakeYaw, random = 1;
	public float blockDamage;

	public m_Nuker() {
		super('7', "Nuker", "Mines blocks for you.", KEY_NONE, "World", "[§eW§r] ");
	}

	@Override
	public void onEnabled() {
		dp.interfaces.clickBlockArray.add(this);
		dp.interfaces.updateArray.add(this);
		dp.interfaces.renderArray.add(this);
	}

	@Override
	public void onDisabled() {
		dp.interfaces.clickBlockArray.remove(this);
		dp.interfaces.updateArray.remove(this);
		dp.interfaces.renderArray.remove(this);

		nukeID = 0;
	}

	@Override
	public void onRendered() {
		if(enabled) {
			for(int i = 0; i < blockArray.size(); i++) {
				u_NBlock block = blockArray.get(i);
				int x = block.x;
				int y = block.y;
				int z = block.z;
				int id = dp.mc.theWorld.getBlockId(x, y, z);

				if(id == 0 || dp.mc.thePlayer.getDistance(x, y, z) > 4.5 || id != nukeID) {
					blockArray.remove(i);
				}
				
				dp.render.nukeESP(x, y, z, dp.settings.bC1, dp.settings.bC2, dp.settings.bC3);
			}
		} else if(blockArray.size() > 0) {
			blockArray.clear();
		}
	}

	@Override
	public void onClickBlock(int I1, int I2, int I3, int I4) {
		int I5 = dp.mc.theWorld.getBlockId(I1, I2, I3);
		boolean I6 = I5 != 0 && I5 != 7 && I5 != nukeID;

		if(I6) {
			nukeID = dp.mc.theWorld.getBlockId(I1, I2, I3);
			dp.bModList.autoTool.blockAutoTool();
			dp.utils.addChat("Nuke ID has been set to : " + I5);
		}
	}

	@Override
	public void onUpdate() {
		int hl = 0;
		int pitch = -90;
		int yaw = 0;
		int loops = dp.mc.playerControllerMP.shouldDrawHUD() ? 1 : 2;

		if (nukeID != 0) {
			Block bl = Block.blocksList[nukeID];
			if (blockDamage < 1F + bl.blockStrength(dp.mc.thePlayer) * 3) {
				blockDamage += bl.blockStrength(dp.mc.thePlayer);
				return;
			}
		}

		for(int h = 0; h < 72; h++) {

			for(int g = 0; g < 36; g++) {

				pitch += 5;

				if (pitch < -90) {
					pitch = -90;
				}

				if (pitch > 90) {
					pitch = 90;
				}

				double d = dp.mc.playerControllerMP.getBlockReachDistance() - 0.5F;
				MovingObjectPosition obj = rayTraceCustom(d, 1F, pitch, fakeYaw);

				if (obj != null && obj.typeOfHit == EnumMovingObjectType.TILE) {
					int j = obj.blockX;
					int k = obj.blockY;
					int l = obj.blockZ;

					if (dp.mc.theWorld.getBlockId(j, k, l) == nukeID) {
						int i1 = obj.sideHit;
						blockArray.add(new u_NBlock(j, k, l));				
						dp.bModList.autoTool.blockAutoTool();
						//dp.utils.sendPacket(new Packet18Animation(dp.mc.thePlayer, 1));
						dp.mc.thePlayer.swingItem();
						dp.utils.sendPacket(new Packet12PlayerLook(fakeYaw, pitch, dp.mc.thePlayer.onGround));
						dp.utils.sendPacket(new Packet14BlockDig(0, j, k, l, i1));
						dp.utils.sendPacket(new Packet14BlockDig(2, j, k, l, i1));						
						blockDamage = 0;
						hl++;
						break;
					}
				}
			}

			if (hl > 1) {
				break;
			}

			fakeYaw += 5;

			if (fakeYaw > 360) {
				fakeYaw = 0;
			}
		}
	}
	
	public MovingObjectPosition rayTraceCustom(double d, float f,int pitch, int yaw) {
		Vec3 vec3d = dp.mc.thePlayer.getPosition(f);
		Vec3 vec3d1 = getCustomLook(pitch,yaw);
		Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
		return dp.mc.theWorld.rayTraceBlocks(vec3d, vec3d2);
	}

	public Vec3 getCustomLook(int pitch, int yaw) {
		float f1 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
		float f3 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-pitch * 0.01745329F);
		float f7 = MathHelper.sin(-pitch * 0.01745329F);
		return Vec3.createVectorHelper(f3 * f5, f7, f1 * f5);
	}
}