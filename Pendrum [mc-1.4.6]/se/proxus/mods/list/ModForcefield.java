package se.proxus.mods.list;

import net.minecraft.src.*;
import se.proxus.events.*;
import se.proxus.events.player.*;
import se.proxus.events.render.*;
import se.proxus.mods.*;

public class ModForcefield extends BaseMod {

	private long currentMS = 0;

	private long lastHit = 0;

	private long threshhold = 69L;

	private float yaw = 0.0F;

	private float yawHead = 0.0F;

	private float pitch = 0.0F;

	public EntityLiving current = null;

	public ModForcefield() {
		super("Forcefield", new ModInfo("Attacks players.", "Oliver", "NONE", true), ModType.COMBAT, false);
		this.setOption("Forcefield_range", Float.valueOf(4.2F), false);
		this.setOption("Forcefield_threshhold", Long.valueOf(69L), false);
		this.setOption("Forcefield_silent_aimbot", Boolean.valueOf(true), false);
		this.setOption("Forcefield_mobs", Boolean.valueOf(false), false);
		this.setOption("Forcefield_animals", Boolean.valueOf(false), false);
		this.setOption("Forcefield_players", Boolean.valueOf(true), false);
		this.setOption("Forcefield_crits", Boolean.valueOf(false), false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}
	
	@Override
	public void registerEvents() {
		this.getEvent().registerEvent(EventUpdate.class);
		this.getEvent().registerEvent(EventPostUpdate.class);
	}

	@Override
	public void onEnabled() {

	}

	@Override
	public void onDisabled() {
		this.current = null;
	}
	
	@Override
	public void onEvent(Event var0) {
		if(this.getState()) {
			if(var0 instanceof EventUpdate) {
				EventUpdate var1 = (EventUpdate)var0;
				
				this.performForcefield();
			} if(var0 instanceof EventPostUpdate) {
				EventPostUpdate var1 = (EventPostUpdate)var0;
				
				this.performPostUpdate();
			}
		}
	}
	
	public void performPostUpdate() {
		if(this.getState()) {
			if(((Boolean)this.getOption("Forcefield_silent_aimbot") && this.canAttack(this.current))) {
				this.wrapper.getPlayer().rotationPitch = this.pitch;
				this.wrapper.getPlayer().rotationYaw = this.yaw;
				this.wrapper.getPlayer().rotationYawHead = this.yawHead;
			}
		}
	}
	
	public void performForcefield() {
		if(this.getState() && !(this.mods.freecam.getState())) {
			this.currentMS = System.nanoTime() / 1000000L;

			if(this.current == null) {
				this.current = this.getClosestLivingEntity(((Float)this.getOption("Forcefield_range")).floatValue());
			} else if(this.utils.entityDistance(this.wrapper.getPlayer(), this.current) >= ((Float)this.getOption("Forcefield_range")).floatValue()) {
				this.current = null;
			} else if(!(this.wrapper.getWorld().loadedEntityList.contains(this.current))) {
				this.current = null;
			} if(this.current != null) {
				boolean var0 = this.currentMS - this.lastHit >= ((Long)this.getOption("Forcefield_threshhold")).longValue();

				if(!(((Boolean)this.getOption("Forcefield_silent_aimbot"))) && this.canAttack(this.current)) {
					this.faceEntity(this.current);
				}

				if(var0 && this.canAttack(this.current)) {
					if(this.mods.autoTool.getState()) {
						this.mods.autoTool.autoToolEntity(this.current);
					}
					
					this.attack(this.current);
				}
			} if(((Boolean)this.getOption("Forcefield_silent_aimbot") && this.canAttack(this.current))) {
				this.pitch = this.wrapper.getPlayer().rotationPitch;
				this.yaw = this.wrapper.getPlayer().rotationYaw;
				this.yawHead = this.wrapper.getPlayer().rotationYawHead;
				this.faceEntity(this.current);
			}
		}
	}

	private void faceEntity(EntityLiving e) {
		if(e != null) {
			double var1 = 0.0D;
			double var3 = e.posX - this.wrapper.getPlayer().posX;
			double var5 = e.posZ - this.wrapper.getPlayer().posZ;
			double var7 = e.posY - this.wrapper.getPlayer().posY + (double)(e.height);

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
			this.wrapper.getPlayer().rotationPitch = var10 - 2.0F;
			this.wrapper.getPlayer().rotationYaw = (float)var1;
			this.wrapper.getPlayer().rotationYawHead = (float)var1;
		}
	}

	public void attack(Entity var1) {
		this.lastHit = System.nanoTime() / 1000000L;
		
		if(((Boolean)this.getOption("Forcefield_crits"))) {
			this.wrapper.getPlayer().onGround = false;
		}

		this.wrapper.getPlayer().swingItem();
		this.wrapper.getController().attackEntity(this.wrapper.getPlayer(), var1);
	}

	public EntityLiving getClosestLivingEntity(double var1) {
		EntityClientPlayerMP var3 = this.wrapper.getPlayer();
		EntityLiving var4 = null;
		double var5 = var1;

		for(int var7 = 0; var7 < this.wrapper.getWorld().loadedEntityList.size(); ++var7) {
			Entity var8 = (Entity)this.wrapper.getWorld().loadedEntityList.get(var7);

			if(var8 != this.wrapper.getPlayer() && var8 != null && var8 instanceof EntityLiving) {
				if(var8 instanceof EntityPlayer) {
					EntityPlayer var9 = (EntityPlayer)var8;

					if(this.sett.isFriend(var9.username)) {
						continue;
					}
				}

				double var11 = this.utils.entityDistance(this.wrapper.getPlayer(), var8);

				if(!(this.wrapper.getPlayer().canEntityBeSeen(var8))) {
					var11 *= 2.0D;
				}

				if(var11 < var5) {
					var5 = var11;
					var4 = (EntityLiving)var8;
				}
			}
		}

		return var4;
	}

	private boolean canAttack(EntityLiving e) {
		return (((Boolean)this.getOption("Forcefield_mobs")) && e instanceof EntityMob) 
				|| ((Boolean)this.getOption("Forcefield_animals") && e instanceof EntityAnimal)
				|| ((Boolean)this.getOption("Forcefield_players") && e instanceof EntityPlayer);
	}
}