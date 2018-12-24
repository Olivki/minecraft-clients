package se.proxus.inheritance;

import se.proxus.*;
import se.proxus.ai.i_Follow;
import se.proxus.utils.placeholders.p_Breadcrum;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class cEntityClientPlayerMP extends EntityClientPlayerMP {

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

	public cEntityClientPlayerMP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler) {
		super(par1Minecraft, par2World, par3Session, par4NetClientHandler);
		this.sendQueue = par4NetClientHandler;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
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
		if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ))) {
			super.onUpdate();
			this.sendMotionUpdates();
		}
	}

	/**
	 * Send updated motion and position information to the server
	 */
	public void sendMotionUpdates()
	{
		double playerX = lastTickPosX + (posX - lastTickPosX) * (double)mc.timer.renderPartialTicks;
		double playerY = lastTickPosY + (posY - lastTickPosY) * (double)mc.timer.renderPartialTicks;
		double playerZ = lastTickPosZ + (posZ - lastTickPosZ) * (double)mc.timer.renderPartialTicks;
		this.mc.dm.hacks.onUpdate();

		/*if(!(this.mc.dm.vars.trailArray.contains(new p_Breadcrum(playerX, playerY, playerZ)))) {
			this.mc.dm.vars.trailArray.add(new p_Breadcrum(playerX, playerY, playerZ));
		}*/

		if(!(this.mc.dm.vars.trailArray.contains(new p_Breadcrum(posX, posY, posZ)))) {
			this.mc.dm.vars.trailArray.add(new p_Breadcrum(posX, posY, posZ));
		}

		if(!(this.mc.dm.vars.canChangePos)) {
			this.mc.dm.vars.trailX = posX;
			this.mc.dm.vars.trailY = posY;
			this.mc.dm.vars.trailZ = posZ;
			this.mc.dm.vars.canChangePos = true;
		}

		boolean var1 = this.isSprinting();

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
			if (var14)
			{
				this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.motionX, -999.0D, -999.0D, this.motionZ, this.onGround));
			}
			else
			{
				this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0D, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
			}

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
		else if (this.wasOnGround != this.onGround)
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

	/**
	 * Called when player presses the drop item key
	 */
	public EntityItem dropOneItem()
	{
		this.sendQueue.addToSendQueue(new Packet14BlockDig(4, 0, 0, 0, 0));
		return null;
	}

	/**
	 * Joins the passed in entity item with the world. Args: entityItem
	 */
	protected void joinEntityItemWithWorld(EntityItem par1EntityItem) {}

	/**
	 * Sends a chat message from the player. Args: chatMessage
	 */
	public void sendChatMessage(String var1) {
		if(var1.charAt(0) == '.') {		
			this.mc.dm.cmds.runCommands(var1);
			return;
		} else {
			this.sendQueue.addToSendQueue(new Packet3Chat(var1));
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
		this.setEntityHealth(this.getHealth() - par2);
	}

	/**
	 * sets current screen to null (used on escape buttons of GUIs)
	 */
	public void closeScreen()
	{
		this.sendQueue.addToSendQueue(new Packet101CloseWindow(this.craftingInventory.windowId));
		this.inventory.setItemStack((ItemStack)null);
		super.closeScreen();
	}

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

	/* @Override
    public void onDeath(DamageSource var1) {
    	super.onDeath(var1);

    	mc.dm.vars.killCount = 0;
    	mc.dm.utils.log(mc.session.username + " died, resetting kill count.");
    }

    @Override
    public void onKillEntity(EntityLiving var1) {
    	super.onKillEntity(var1);
    	mc.dm.utils.log(var1.getEntityName());
    }*/

	@Override
	public void moveEntity(double var1, double var2, double var3) {
		float walked = distanceWalkedModified;

		/*if(mc.dp.bModList.fly.getState() || mc.dp.bModList.freeCam.getState()) {
			double flySpeed = mc.dp.settings.flySpeed;
			var2 = 0.0D;

			if(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.keyCode) && mc.inGameHasFocus) {
				var2 += flySpeed / 7.5D;
			} if(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.keyCode) && mc.inGameHasFocus) {
				var2 -= flySpeed / 7.5D;
			}

			var1 *= flySpeed;
			var3 *= flySpeed;

			fallDistance = 0.0F;
			motionY = 0.0D;
		}*/

		super.moveEntity(var1, var2, var3);

		if(isSprinting()) {
			distanceWalkedModified = walked;
		}
	}

	@Override
	public boolean isBlocking() {
		return true;
	}
	
	@Override
    public boolean isOnLadder() {
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.boundingBox.minY);
        int var3 = MathHelper.floor_double(this.posZ);
        int var4 = this.worldObj.getBlockId(var1, var2, var3);
        boolean var5 = Reflector.hasMethod(50) ? Reflector.callBoolean(98, new Object[] {Block.blocksList[var4], this.worldObj, Integer.valueOf(var1), Integer.valueOf(var2), Integer.valueOf(var3)}): var4 == Block.ladder.blockID || var4 == Block.vine.blockID;
        return this.mc.dm.hacks.glide.getState() ? true : var5;
    }
}