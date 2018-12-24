package Comet.Mods;

import net.minecraft.src.*;
import Comet.Gui.InGame;
import Comet.Utils.Utils;
import Comet.Utils.Hooks.ClickBlock;
import Comet.Utils.Hooks.Tickable;

public class m_Nuker extends Base_Mod implements Tickable, ClickBlock {

	private int nukeID, fakeYaw;
	private float blockDamage;

	public m_Nuker() {
		super(Base_Enum.Nuker);
	}

	@Override
	public void onStart() {
		enabled = true;
		InGame.array.add("Nuker");
		Utils.addToTick(this);
		Utils.addToClickBlock(this);
	}

	@Override
	public void onStop() {
		enabled = false;
		InGame.array.remove("Nuker");
		Utils.removeFromTick(this);
		Utils.removeFromClickBlock(this);
	}

	@Override
	public void onClickBlock(int I1, int I2, int I3, int I4) {
		if (mc.theWorld.getBlockId(I1, I2, I3) != 7) {
			nukeID = mc.theWorld.getBlockId(I1, I2, I3);
			Utils.addChat("Nuking id : " + mc.theWorld.getBlockId(I1, I2, I3));
		}
	}

	@Override
	public void onTick() {
		int hl=0;
		int pitch = -90;
		int yaw = 0;
		int loops = mc.playerController.shouldDrawHUD() ? 1 : 2;
		if (nukeID!=0) {
			Block bl = Block.blocksList[nukeID];
			if (blockDamage<1F+bl.blockStrength(mc.thePlayer)*3)
			{
				blockDamage+=bl.blockStrength(mc.thePlayer);
				return;
			}
		}
		for (int h=0;h<72;h++) {
			for (int g=0;g<36;g++)
			{
				pitch+=5;
				if (pitch < -90)
				{
					pitch = -90;
				}
				if (pitch > 90)
				{
					pitch = 90;
				}

				double d = mc.playerController.getBlockReachDistance()-0.5F;
				MovingObjectPosition obj = rayTraceCustom(d, 1F, pitch, fakeYaw);
				if (obj!=null && obj.typeOfHit == EnumMovingObjectType.TILE) {
					int j = obj.blockX;
					int k = obj.blockY;
					int l = obj.blockZ;
					if (mc.theWorld.getBlockId(j, k, l)==nukeID) {
						int i1 = obj.sideHit;
						mc.thePlayer.swingItem();
						Utils.sendPacket(new Packet12PlayerLook(fakeYaw, pitch, mc.thePlayer.onGround));
						Utils.sendPacket(new Packet14BlockDig(0, j, k, l, i1));
						Utils.sendPacket(new Packet14BlockDig(2, j, k, l, i1));
						blockDamage=0;
						hl++;
						break;
					}

				}
			}
			if (hl>1)
			{
				break;
			}
			fakeYaw+=5;
			if (fakeYaw>360) {
				fakeYaw=0;
			}

		}
	}

	public MovingObjectPosition rayTraceCustom(double d, float f,int pitch, int yaw) {
		Vec3D vec3d = mc.thePlayer.getPosition(f);
		Vec3D vec3d1 = getCustomLook(pitch,yaw);
		Vec3D vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
		return mc.theWorld.rayTraceBlocks(vec3d, vec3d2);
	}

	public Vec3D getCustomLook(int pitch, int yaw) {
		float f1 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
		float f3 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-pitch * 0.01745329F);
		float f7 = MathHelper.sin(-pitch * 0.01745329F);
		return Vec3D.createVector(f3 * f5, f7, f1 * f5);
	}

}
