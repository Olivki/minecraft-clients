package se.proxus.utils;

import net.minecraft.src.EntityLiving;
import se.proxus.*;

public class UtilAim extends Pendrum {

	public Position pos = null;

	public Rotation rot = null;

	public boolean silent = false;

	public UtilAim(Position pos, Rotation rot, boolean silent) {
		this.pos = pos;
		this.rot = rot;
		this.silent = silent;
	}

	public void onUpdate() {
		if(this.silent) {
			this.rot.pitch = this.wrapper.getPlayer().rotationPitch;
			this.rot.yaw = this.wrapper.getPlayer().rotationYaw;
			this.rot.yawHead = this.wrapper.getPlayer().rotationYawHead;
			this.aimPos(this.pos.x, this.pos.z, this.pos.y);
		} else {
			this.aimPos(this.pos.x, this.pos.z, this.pos.y);
		}
	}

	public void onPostUpdate() {
		if(this.silent) {
			this.wrapper.getPlayer().rotationPitch = this.rot.pitch;
			this.wrapper.getPlayer().rotationYaw = this.rot.yaw;
			this.wrapper.getPlayer().rotationYawHead = this.rot.yawHead;
		}
	}

	public void aimPos(double x, double y, double z) {
		double var1 = 0.0D;
		double var3 = x - this.wrapper.getPlayer().posX;
		double var5 = y - this.wrapper.getPlayer().posZ;
		double var7 = z - this.wrapper.getPlayer().posY;

		if(var5 > 0.0D && var3 > 0.0D) {
			var1 = Math.toDegrees(-Math.atan(var3 / var5));
		} else if (var5 > 0.0D && var3 < 0.0D) {
			var1 = Math.toDegrees(-Math.atan(var3 / var5));
		} else if (var5 < 0.0D && var3 > 0.0D) {
			var1 = -90.0D + Math.toDegrees(Math.atan(var5 / var3));
		} else if (var5 < 0.0D && var3 < 0.0D) {
			var1 = 90.0D + Math.toDegrees(Math.atan(var5 / var3));
		}

		float var9 = (float)Math.sqrt(var5 * var5 + var3 * var3);
		float var10 = (float)(-Math.toDegrees(Math.atan(var7 / (double)var9)));
		this.wrapper.getPlayer().rotationPitch = var10;
		this.wrapper.getPlayer().rotationYaw = (float)var1;
		this.wrapper.getPlayer().rotationYawHead = (float)var1;
	}
}