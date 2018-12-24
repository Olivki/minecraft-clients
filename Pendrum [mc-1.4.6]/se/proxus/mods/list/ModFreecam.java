package se.proxus.mods.list;

import net.minecraft.client.*;
import net.minecraft.src.*;
import se.proxus.mods.*;

public class ModFreecam extends BaseMod {
	
	public double x = 0.0D;
	
	public double y = 0.0D;
	
	public double z = 0.0D;

	public float rotYaw = 0.0F;
	
	public float rotPitch = 0.0F;

	public EntityOtherPlayerMP freecamEntity = null;

	public ModFreecam() {
		super("Freecam", new ModInfo("Lets you freecam.", "Oliver", "NONE", true), ModType.PLAYER, false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void onEnabled() {
		this.wrapper.getPlayer().noClip = true;
		this.freecamEntity = new EntityOtherPlayerMP(this.wrapper.getWorld(), this.wrapper.getPlayer().username);
		this.freecamEntity.setPositionAndRotation(this.wrapper.getPlayer().posX, this.wrapper.getPlayer().posY - (double)this.wrapper.getPlayer().yOffset, this.wrapper.getPlayer().posZ, this.wrapper.getPlayer().rotationYaw, this.wrapper.getPlayer().rotationPitch);
		WorldClient worldclient = (WorldClient)this.wrapper.getWorld();
		worldclient.addEntityToWorld(-1, freecamEntity);
		this.x = this.wrapper.getPlayer().posX;
		this.y = this.wrapper.getPlayer().posY;
		this.z = this.wrapper.getPlayer().posZ;
		this.rotYaw = this.wrapper.getPlayer().rotationYaw;
		this.rotPitch = this.wrapper.getPlayer().rotationPitch;
	}

	@Override
	public void onDisabled() {
		try {
			this.wrapper.getPlayer().noClip = false;
			WorldClient worldclient = (WorldClient)this.wrapper.getWorld();
			worldclient.removeEntityFromWorld(-1);

			if(this.wrapper.getPlayer().getDistanceToEntity(this.freecamEntity) > 9 || this.mods.nocheat.getState()) {
				this.wrapper.getPlayer().setPositionAndRotation(this.x, this.y, this.z, this.rotYaw, this.rotPitch);
			}
		} catch (Exception e) {

		}
	}
}