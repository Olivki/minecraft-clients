package se.proxus.hooks;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.InventoryPlayer;
import se.proxus.tools.Location;

public interface Player {

    public String getUsername();

    public void setUsername(String username);

    public int getHealth();

    public void setHealth(int health);

    public int getArmour();

    public void setArmour(int armour);

    public boolean isSneaking();

    public void setSneaking(boolean sneaking);

    public boolean isSprinting();

    public void setSprinting(boolean sprinting);

    public boolean isSwinging();

    public void setSwinging(boolean swinging);

    public float getYaw();

    public void setYaw(float yaw);

    public float getYawHead();

    public void setYawHead(float yawHead);

    public float getPitch();

    public void setPitch(float pitch);

    public void setAngles(float yaw, float pitch);

    public float getAttackYaw(EntityLiving target);

    public float getAttackPitch(EntityLiving target);

    public double getX();

    public void setX(double x);

    public double getY();

    public void setY(double y);

    public double getZ();

    public void setZ(double z);

    public double getMotionX();

    public void setMotionX(double motionX);

    public double getMotionY();

    public void setMotionY(double motionY);

    public double getMotionZ();

    public void setMotionZ(double motionZ);

    public InventoryPlayer getInventory();

    public void setPosition(double x, double y, double z);

    public void setPositionAndAngles(double x, double y, double z, float yaw,
	    float pitch);

    public Location getLocation();

    public EntityLiving getTarget();

    public void setTarget(final EntityLiving target);

    public Location getTargetLocation();

    public void addMessage(String message);

    public void sendMessage(String message);

    public float getYawDistanceFromEntity(EntityLiving entity);

    public float getPitchDistanceFromEntity(EntityLiving entity);

}