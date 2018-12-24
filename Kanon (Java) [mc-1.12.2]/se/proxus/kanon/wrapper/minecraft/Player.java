package se.proxus.kanon.wrapper.minecraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import se.proxus.kanon.utils.minecraft.player.Rotationz;
import se.proxus.kanon.utils.system.Reflectionz;
import se.proxus.kanon.utils.templates.Location;

import java.lang.reflect.Field;

public final class Player {
    
    public static float getReach() { return getController().getBlockReachDistance(); }

    public static String getUsername() {
        return getPlayer().getName();
    }
    
    public static float getHealth() {
        return getPlayer().getHealth();
    }

    public static void setHealth(final float health) {
        getPlayer().setHealth(health);
    }

    public static int getArmour() {
        return getPlayer().getTotalArmorValue();
    }

    public static boolean isSneaking() {
        return getPlayer().isSneaking();
    }

    public static void setSneaking(final boolean sneaking) {
        getPlayer().setSneaking(sneaking);
    }

    public static boolean isSprinting() {
        return getPlayer().isSprinting();
    }

    public static void setSprinting(final boolean sprinting) {
        getPlayer().setSprinting(sprinting);
    }

    public static boolean isSwinging() {
        return getPlayer().isSwingInProgress;
    }

    public static void setSwinging(final boolean swinging) {
        getPlayer().isSwingInProgress = swinging;
    }

    public static boolean isOnGround() {
        return getPlayer().onGround;
    }

    public static void setOnGround(final boolean onGround) {
        getPlayer().onGround = onGround;
    }

    public static boolean isOnLadder() {
        return getPlayer().isOnLadder();
    }

    public static boolean isAutoJumpEnabled() {
        return getPlayer().isAutoJumpEnabled();
    }

    public static float getJumpMovementFactor() {
        return getPlayer().jumpMovementFactor;
    }

    public static void setJumpMovementFactor(final float jumpMovementFactor) {
        getPlayer().jumpMovementFactor = jumpMovementFactor;
    }

    public static float getRotationPitch() {
        return getPlayer().rotationPitch;
    }

    public static float getPrevRotationPitch() {
        return getPlayer().prevRotationPitch;
    }

    public static float getRotationYaw() {
        return getPlayer().rotationYaw;
    }

    public static float getPrevRotationYaw() {
        return getPlayer().prevRotationYaw;
    }

    public static float getYaw() {
        return getPlayer().rotationYaw;
    }

    public static void setYaw(final float yaw) {
        getPlayer().rotationYaw = yaw;
    }

    public static float getYawHead() {
        return getPlayer().rotationYawHead;
    }

    public static void setYawHead(final float yawHead) {
        getPlayer().rotationYawHead = yawHead;
    }

    public static float getPitch() {
        return getPlayer().rotationPitch;
    }

    public static void setPitch(final float pitch) {
        getPlayer().rotationPitch = pitch;
    }

    public static void setAngles(final float yaw, final float pitch) {
        getPlayer().setAngles(yaw, pitch);
    }
    
    public static float getCurrentBlockDamage() throws NoSuchFieldException, IllegalAccessException {
        final Field blockDamageField = Reflectionz.getNonAccessibleField(PlayerControllerMP.class,
                                                                         "curBlockDamageMP");
        return (float) blockDamageField.get(getController());
    }

    public static double distanceTo(final Entity entity) {
        return new Location(entity).distanceToPlayer();
    }

    public static void faceLocation(final Location location, final boolean local) {
        if (local) {
            Rotationz.Client.faceVector(location.getVector());
        } else {
            Rotationz.Server.faceVector(location.getVector());
        }
    }
    
    public static float getEyeHeight() {
        return getPlayer().getEyeHeight();
    }

    public static double getX() {
        return getPlayer().posX;
    }

    public static void setX(final double x) {
        getPlayer().posX = x;
    }

    public static double getY() {
        return getPlayer().posY;
    }

    public static void setY(final double y) {
        getPlayer().posY = y;
    }

    public static double getZ() {
        return getPlayer().posZ;
    }

    public static void setZ(final double z) {
        getPlayer().posZ = z;
    }
    
    public static double getMotionX() {
        return getPlayer().motionX;
    }
    
    public static void setMotionX(final double motionX) {
        getPlayer().motionX = motionX;
    }
    
    public static double getMotionY() {
        return getPlayer().motionY;
    }
    
    public static void setMotionY(final double motionY) {
        getPlayer().motionY = motionY;
    }
    
    public static double getMotionZ() {
        return getPlayer().motionZ;
    }

    public static void setMotionZ(final double motionZ) {
        getPlayer().motionZ = motionZ;
    }

    public static void setPosition(final double x, final double y, final double z) {
        getPlayer().setLocationAndAngles(x, y, z, getYaw(), getPitch());
    }

    public static void setPosition(final Location location) {
        getPlayer().setLocationAndAngles(location.getX(), location.getY(), location.getZ(), getYaw(),
                getPitch());
    }

    public static void setPositionAndAngles(final double x, final double y,
            final double z, final float yaw, final float pitch) {
        getPlayer().setLocationAndAngles(x, y, z, yaw, pitch);
    }

    public static Location getLocation() {
        return new Location(getX(), getY(), getZ());
    }

    public static InventoryPlayer getInventory() {
        return getPlayer().inventory;
    }

    public static void addMessage(final String message) {
        final TextComponentString componentString = new TextComponentString(message);

        componentString.getStyle().setColor(TextFormatting.GRAY);

        getPlayer().addChatMessage(componentString);
    }

    public static void sendMessage(final String message) {
        getPlayer().sendChatMessage(message);
    }

    public static AxisAlignedBB getBoundingBox() {
        return getPlayer().getCollisionBoundingBox();
    }

    public static PlayerControllerMP getController() {
        return Minekraft.getController();
    }

    public static boolean isCollidedHorizontally() {
        return getPlayer().isCollidedHorizontally;
    }

    public static boolean isCollidedVertically() {
        return getPlayer().isCollidedVertically;
    }

    public static boolean isCollided() {
        return getPlayer().isCollided;
    }

    public static boolean isInWater() {
        return getPlayer().isInWater();
    }

    public static boolean isPushedByWater() {
        return getPlayer().isPushedByWater();
    }

    public static boolean isWet() {
        return getPlayer().isWet();
    }

    public static boolean isInCreativeMode() {
        return getController().isInCreativeMode();
    }

    public static boolean isInSpectatorMode() {
        return getController().isSpectatorMode();
    }

    public static boolean isNotInCreative() {
        return getController().isNotCreative();
    }

    public static boolean isInAdventureOrSurvival() {
        return getController().gameIsSurvivalOrAdventure();
    }

    public static FoodStats getFoodStats() {
        return getPlayer().getFoodStats();
    }

    public static int getFoodLevel() {
        return getFoodStats().getFoodLevel();
    }

    public static boolean canSprint() {
        return !isSneaking() && getPlayer().movementInput.forwardKeyDown && !isCollidedHorizontally() &&
                !isInWater() && (isInCreativeMode() || getFoodLevel() > 6);
    }

    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor(getBoundingBox().minX); x < MathHelper.floor(getBoundingBox().maxX) + 1;
             x++) {
            for (int y = MathHelper.floor(getBoundingBox().minY);
                 y < MathHelper.floor(getBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor(getBoundingBox().minZ);
                     z < MathHelper.floor(getBoundingBox().maxZ) + 1; z++) {
                    final BlockPos blockPos = new BlockPos(x, y, z);
                    final World world = Minekraft.getWorld();
                    final IBlockState blockState = world.getBlockState(blockPos);
                    final Block block = blockState.getBlock();

                    if (block instanceof BlockAir) {
                        continue;
                    }

                    final AxisAlignedBB blockBoundingBox =
                            blockState.getCollisionBoundingBox(world, blockPos);

                    if (blockBoundingBox != null && getBoundingBox().intersectsWith(blockBoundingBox)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    public static EnumFacing getFacing(final BlockPos pos) {
        return EnumFacing.func_190914_a(pos, getPlayer());
    }
    
    public static void swing(final boolean local) {
        if (local) {
            getPlayer().swingArm(EnumHand.MAIN_HAND);
        } else {
            Minekraft.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
        }
    }
    
    public static void attack(final EntityLivingBase entity, final boolean local) {
        if (local) {
            getController().attackEntity(getPlayer(), entity);
            swing(true);
        } else {
            Minekraft.sendPacket(new CPacketUseEntity(entity, EnumHand.MAIN_HAND));
            swing(false);
        }
    }

    public static boolean canEntityBeSeen(final Entity entity) {
        return getPlayer().canEntityBeSeen(entity);
    }

    public static boolean getTargetChecks(final EntityLiving entity) {
        return !(entity.isDead)
                && canEntityBeSeen(entity)
                && distanceTo(entity) <= 3.5F
                && entity.ticksExisted > 100;
    }
    
    public static Vec3d getEyeVector() {
        return new Vec3d(getX(), (getY() + getEyeHeight()), getZ());
    }

    public static int getDirection() {
        return MathHelper.floor((double) (getRotationYaw() * 4.0F / 360.0F) + 0.5D) & 3;
    }

    public static EntityPlayerSP getPlayer() {
        return Minekraft.getPlayer();
    }
}
