package se.proxus.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class HookEntityClientPlayer extends EntityClientPlayerMP {

	public NetClientHandler sendQueue;
	private double oldPosX;

	/** Old Minimum Y of the bounding box */
	private double oldMinY;
	private double oldPosY;
	private double oldPosZ;
	private float oldRotationYaw;
	private float oldRotationPitch;

	/** Check if was on ground last update */
	private boolean wasOnGround = false;

	/** should the player stop sneaking? */
	private boolean shouldStopSneaking = false;
	private boolean wasSneaking = false;
	private int field_71168_co = 0;

	/** has the client player's health been set? */
	private boolean hasSetHealth = false;

	public HookEntityClientPlayer(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler) {
		super(par1Minecraft, par2World, par3Session, par4NetClientHandler);
		this.sendQueue = par4NetClientHandler;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		return false;
	}

	/**
	 * Heal living entity (param: amount of half-hearts)
	 */
	public void heal(int par1) {}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)))
		{
			super.onUpdate();
			this.sendMotionUpdates();
		}
	}

	/**
	 * Send updated motion and position information to the server
	 */
	public void sendMotionUpdates()
	{
		boolean var1 = this.isSprinting();

		/** Pendrum. **/
		this.mc.pm.mods.handleUpdate();

		if(!(this.mc.pm.mods.freecam.getState())) {
			if (var1 != this.wasSneaking)
			{
				if (var1)
				{
					this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
				}
				else
				{
					this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
				}

				this.wasSneaking = var1;
			}

			boolean var2 = this.isSneaking();

			if (var2 != this.shouldStopSneaking)
			{
				if (var2)
				{
					this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
				}
				else
				{
					this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
				}

				this.shouldStopSneaking = var2;
			}

			double var3 = this.posX - this.oldPosX;
			double var5 = this.boundingBox.minY - this.oldMinY;
			double var7 = this.posZ - this.oldPosZ;
			double var9 = (double)(this.rotationYaw - this.oldRotationYaw);
			double var11 = (double)(this.rotationPitch - this.oldRotationPitch);
			boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || this.field_71168_co >= 20;
			boolean var14 = var9 != 0.0D || var11 != 0.0D;

			if (this.ridingEntity != null)
			{
				this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0D, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
				var13 = false;
			}
			else if (var13 && var14)
			{
				this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
			}
			else if (var13)
			{
				this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
			}
			else if (var14)
			{
				this.sendQueue.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
			}
			else
			{
				this.sendQueue.addToSendQueue(new Packet10Flying(this.onGround));
			}

			++this.field_71168_co;
			this.wasOnGround = this.onGround;

			if (var13)
			{
				this.oldPosX = this.posX;
				this.oldMinY = this.boundingBox.minY;
				this.oldPosY = this.posY;
				this.oldPosZ = this.posZ;
				this.field_71168_co = 0;
			}

			if (var14)
			{
				this.oldRotationYaw = this.rotationYaw;
				this.oldRotationPitch = this.rotationPitch;
			}
		}
		
		/** Pendrum. **/
		this.mc.pm.mods.handlePostMotionUpdate();
	}

	/**
	 * Called when player presses the drop item key
	 */
	public EntityItem dropOneItem(boolean par1)
	{
		int var2 = par1 ? 3 : 4;
		this.sendQueue.addToSendQueue(new Packet14BlockDig(var2, 0, 0, 0, 0));
		return null;
	}

	/**
	 * Joins the passed in entity item with the world. Args: entityItem
	 */
	protected void joinEntityItemWithWorld(EntityItem par1EntityItem) {}

	/**
	 * Sends a chat message from the player. Args: chatMessage
	 */
	public void sendChatMessage(String var0) {
		if(var0.charAt(0) == '-' && !(var0.startsWith(" "))) {
			this.mc.pm.cmds.runCommands(var0.substring(1), 0);
			return;
		} else {
			this.sendQueue.addToSendQueue(new Packet3Chat(var0));
			return;
		}
	}

	/**
	 * Swings the item the player is holding.
	 */
	public void swingItem()
	{
		super.swingItem();
		this.sendQueue.addToSendQueue(new Packet18Animation(this, 1));
	}

	public void respawnPlayer()
	{
		this.sendQueue.addToSendQueue(new Packet205ClientCommand(1));
	}

	/**
	 * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
	 * second with the reduced value. Args: damageAmount
	 */
	protected void damageEntity(DamageSource par1DamageSource, int par2)
	{
		if (!this.func_85032_ar())
		{
			this.setEntityHealth(this.getHealth() - par2);
		}
	}

	/**
	 * sets current screen to null (used on escape buttons of GUIs)
	 */

	/**
	 * Updates health locally.
	 */
	public void setHealth(int par1)
	{
		if (this.hasSetHealth)
		{
			super.setHealth(par1);
		}
		else
		{
			this.setEntityHealth(par1);
			this.hasSetHealth = true;
		}
	}

	/**
	 * Adds a value to a statistic field.
	 */
	public void addStat(StatBase par1StatBase, int par2)
	{
		if (par1StatBase != null)
		{
			if (par1StatBase.isIndependent)
			{
				super.addStat(par1StatBase, par2);
			}
		}
	}

	/**
	 * Used by NetClientHandler.handleStatistic
	 */
	public void incrementStat(StatBase par1StatBase, int par2)
	{
		if (par1StatBase != null)
		{
			if (!par1StatBase.isIndependent)
			{
				super.addStat(par1StatBase, par2);
			}
		}
	}

	/**
	 * Sends the player's abilities to the server (if there is one).
	 */
	public void sendPlayerAbilities()
	{
		this.sendQueue.addToSendQueue(new Packet202PlayerAbilities(this.capabilities));
	}

	public boolean func_71066_bF()
	{
		return true;
	}

	@Override
	public void moveEntity(double var1, double var2, double var3) {
		float walked = this.distanceWalkedModified;

		double x = var1;
		double y = var2;
		double z = var3;

		if(this.mc.pm.mods.sprint.getState() && (this.movementInput.moveForward > 0.0F && !(this.movementInput.moveStrafe != 0))) {
			x *= this.mc.pm.mods.nocheat.getState() ? 1.06D : ((Double)this.mc.pm.mods.sprint.getOption("Sprint_speed"));
			z *= this.mc.pm.mods.nocheat.getState() ? 1.06D : ((Double)this.mc.pm.mods.sprint.getOption("Sprint_speed"));
		}

		if(this.mc.pm.mods.flight.getState()) {
			this.motionY = 0.0D;
			this.fallDistance = 0.0F;
			y = (0.0D + (this.mc.gameSettings.keyBindJump.pressed ? 0.5D : 0.0D)) - (this.mc.gameSettings.keyBindSneak.pressed ? 0.5D : 0.0D);
			x = var1 * ((Double)this.mc.pm.mods.flight.getOption("Flight_speed")).doubleValue();
			z = var3 * ((Double)this.mc.pm.mods.flight.getOption("Flight_speed")).doubleValue();
		}

		super.moveEntity(x, y, z);

		if(this.isSprinting() || this.mc.pm.mods.flight.getState()) {
			this.distanceWalkedModified = walked;
		} if(this.mc.pm.mods.flight.getState()) {
			this.onGround = true;
		}
	}

	@Override
	protected boolean pushOutOfBlocks(double par1, double par3, double par5) {
		if(this.mc.pm.mods.freecam.getState()) {
			return false;
		}

		return super.pushOutOfBlocks(par1, par3, par5);
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {
		if(this.mc.pm.mods.freecam.getState()) {
			return false;
		}

		return super.isEntityInsideOpaqueBlock();
	}
}
