package se.proxus.hooks;

import java.util.Random;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet3Chat;
import se.proxus.Gallium;
import se.proxus.tools.Location;

public class PlayerImpl implements Player {

    private Gallium client;

    private EntityLiving target;

    public PlayerImpl(final Gallium client) {
	setClient(client);
    }

    @Override
    public String getUsername() {
	return getClient().getMinecraft().thePlayer.username;
    }

    @Override
    public void setUsername(final String username) {
	getClient().getMinecraft().session.username = username;
    }

    @Override
    public int getHealth() {
	return getClient().getMinecraft().thePlayer.getHealth();
    }

    @Override
    public void setHealth(final int health) {
	getClient().getMinecraft().thePlayer.setHealth(health);
    }

    @Override
    public int getArmour() {
	return 0;
    }

    @Override
    public void setArmour(final int armour) {

    }

    @Override
    public boolean isSneaking() {
	return getClient().getMinecraft().thePlayer.isSneaking();
    }

    @Override
    public void setSneaking(final boolean sneaking) {
	getClient().getMinecraft().thePlayer.setSneaking(sneaking);
    }

    @Override
    public boolean isSprinting() {
	return getClient().getMinecraft().thePlayer.isSprinting();
    }

    @Override
    public void setSprinting(final boolean sprinting) {
	getClient().getMinecraft().thePlayer.setSprinting(sprinting);
    }

    @Override
    public boolean isSwinging() {
	return getClient().getMinecraft().thePlayer.isSwingInProgress;
    }

    @Override
    public void setSwinging(final boolean swinging) {
	getClient().getMinecraft().thePlayer.isSwingInProgress = swinging;
    }

    @Override
    public float getYaw() {
	return getClient().getMinecraft().thePlayer.rotationYaw;
    }

    @Override
    public void setYaw(final float yaw) {
	getClient().getMinecraft().thePlayer.rotationYaw = yaw;
    }

    @Override
    public float getYawHead() {
	return getClient().getMinecraft().thePlayer.rotationYawHead;
    }

    @Override
    public void setYawHead(final float yawHead) {
	getClient().getMinecraft().thePlayer.rotationYawHead = yawHead;
    }

    @Override
    public float getPitch() {
	return getClient().getMinecraft().thePlayer.rotationPitch;
    }

    @Override
    public void setPitch(final float pitch) {
	getClient().getMinecraft().thePlayer.rotationPitch = pitch;
    }

    @Override
    public void setAngles(final float yaw, final float pitch) {
	getClient().getMinecraft().thePlayer.setAngles(yaw, pitch);
    }

    @Override
    public float getAttackYaw(final EntityLiving target) {
	double xDistance = target.posX - getX();
	double zDistance = target.posZ - getZ();
	return (float) (Math.atan2(zDistance, xDistance) * 180D / Math.PI) - 90F;
    }

    @Override
    public float getAttackPitch(final EntityLiving target) {
	return (float) (Math
		.atan2(getLocation().distanceTo(target.posX, target.posY,
			target.posZ), target.posY + 0.8D - getY() + 1) * 180D / Math.PI) - 90F;
    }

    @Override
    public double getX() {
	return getClient().getMinecraft().thePlayer.posX;
    }

    @Override
    public void setX(final double x) {
	getClient().getMinecraft().thePlayer.posX = x;
    }

    @Override
    public double getY() {
	return getClient().getMinecraft().thePlayer.posY;
    }

    @Override
    public void setY(final double y) {
	getClient().getMinecraft().thePlayer.posY = y;
    }

    @Override
    public double getZ() {
	return getClient().getMinecraft().thePlayer.posZ;
    }

    @Override
    public void setZ(final double z) {
	getClient().getMinecraft().thePlayer.posZ = z;
    }

    @Override
    public double getMotionX() {
	return getClient().getMinecraft().thePlayer.motionX;
    }

    @Override
    public void setMotionX(final double motionX) {
	getClient().getMinecraft().thePlayer.motionX = motionX;
    }

    @Override
    public double getMotionY() {
	return getClient().getMinecraft().thePlayer.motionY;
    }

    @Override
    public void setMotionY(final double motionY) {
	getClient().getMinecraft().thePlayer.motionY = motionY;
    }

    @Override
    public double getMotionZ() {
	return getClient().getMinecraft().thePlayer.motionZ;
    }

    @Override
    public void setMotionZ(final double motionZ) {
	getClient().getMinecraft().thePlayer.motionZ = motionZ;
    }

    @Override
    public void setPosition(final double x, final double y, final double z) {
	getClient().getMinecraft().thePlayer.setPosition(x, y, z);
    }

    @Override
    public void setPositionAndAngles(final double x, final double y,
	    final double z, final float yaw, final float pitch) {
	getClient().getMinecraft().thePlayer.setPositionAndRotation(x, y, z,
		yaw, pitch);
    }

    @Override
    public Location getLocation() {
	return new Location(getX(), getY(), getZ());
    }

    @Override
    public EntityLiving getTarget() {
	return target;
    }

    @Override
    public void setTarget(final EntityLiving target) {
	this.target = target;
    }

    @Override
    public Location getTargetLocation() {
	return new Location(getTarget().posX, getTarget().posY,
		getTarget().posZ);
    }

    @Override
    public InventoryPlayer getInventory() {
	return getClient().getMinecraft().thePlayer.inventory;
    }

    @Override
    public void addMessage(final String message) {
	/*
	 * getClient().getMinecraft().thePlayer.addChatMessage(Colours.GOLD +
	 * "[" + getClient().getName() + "]: " + Colours.GREY + message);
	 */
	getClient().getMinecraft().thePlayer.addChatMessage(message);
    }

    @Override
    public void sendMessage(final String message) {
	getClient().sendPacket(new Packet3Chat(message));
    }

    @Override
    public float getYawDistanceFromEntity(final EntityLiving entity) {
	Random rng = getClient().getRNG();
	int plusOrMinus = rng.nextInt(2);
	return getAttackYaw(entity)
		+ (plusOrMinus == 0 ? rng.nextInt(6) : -rng.nextInt(6));
    }

    @Override
    public float getPitchDistanceFromEntity(final EntityLiving entity) {
	Random rng = getClient().getRNG();
	double xDistance = entity.posX - getX();
	double yDistance = getY()
		+ getClient().getMinecraft().thePlayer.getEyeHeight()
		- (entity.posY + entity.getEyeHeight());
	double zDistance = entity.posZ - getZ();
	double sqrt = MathHelper.sqrt_double(xDistance * xDistance + zDistance
		* zDistance);
	int plusOrMinus = rng.nextInt(2);
	return (float) -(-(Math.atan2(yDistance, sqrt) * 180.0D / Math.PI) + (plusOrMinus == 0 ? rng
		.nextInt(6) : -rng.nextInt(6)));
    }

    public Gallium getClient() {
	return client;
    }

    public void setClient(final Gallium client) {
	this.client = client;
    }
}