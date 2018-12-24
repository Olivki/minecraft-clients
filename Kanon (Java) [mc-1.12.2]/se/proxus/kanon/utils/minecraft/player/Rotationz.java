package se.proxus.kanon.utils.minecraft.player;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import se.proxus.kanon.wrapper.minecraft.Minekraft;
import se.proxus.kanon.wrapper.minecraft.Player;

// Taken from Wurst, because fuck trig.
public final class Rotationz {
    
    @Getter @Setter private static boolean fakeRotation;
    @Getter @Setter private static float serverYaw;
    @Getter @Setter private static float serverPitch;
    
    public static Vec3d getEyeVector() {
        return new Vec3d(Player.getX(), Player.getY() + Player.getEyeHeight(), Player.getZ());
    }
    
    private static float[] getNeededRotations(final Vec3d vector) {
        final Vec3d eyesPos = getEyeVector();
        
        final double diffX = vector.xCoord - eyesPos.xCoord;
        final double diffY = vector.yCoord - eyesPos.yCoord;
        final double diffZ = vector.zCoord - eyesPos.zCoord;
        
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        final float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
        
        return new float[]{MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch)};
    }
    
    private static float[] getNeededRotations2(final Vec3d vector) {
        final Vec3d eyesPos = getEyeVector();
        
        final double diffX = vector.xCoord - eyesPos.xCoord;
        final double diffY = vector.yCoord - eyesPos.yCoord;
        final double diffZ = vector.zCoord - eyesPos.zCoord;
        
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        
        final float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        final float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));
        
        return new float[]{ Player.getYaw() + MathHelper.wrapDegrees(yaw - Player.getYaw()),
                Player.getPitch() + MathHelper.wrapDegrees(pitch - Player.getPitch()) };
    }
    
    public static double getAngleToLastReportedLookVector(final Vec3d vector) {
        final float[] rotations = getNeededRotations(vector);
        
        final EntityPlayerSP player = Player.getPlayer();
        final float lastReportedYaw = MathHelper.wrapDegrees(player.lastReportedYaw);
        final float lastReportedPitch = MathHelper.wrapDegrees(player.lastReportedPitch);
        
        final float diffYaw = lastReportedYaw - rotations[0];
        final float diffPitch = lastReportedPitch - rotations[1];
        
        return Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
    }
    
    public static float limitAngleChange(final float current, final float intended, final float maxChange) {
        float change = MathHelper.wrapDegrees(intended - current);
        
        change = MathHelper.clamp(change, -maxChange, maxChange);
        
        return MathHelper.wrapDegrees(current + change);
    }
    
    public static boolean faceVectorForWalking(final Vec3d vec) {
        final float[] rotations = getNeededRotations(vec);
        
        final float oldYaw = Player.getPrevRotationYaw();
        
        Player.setYaw(limitAngleChange(oldYaw, rotations[0], 30));
        Player.setPitch(0);
        
        return Math.abs(oldYaw - rotations[0]) < 1F;
    }
    
    public static final class Client {
        
        public static Vec3d getLookVector() {
            final float cosYaw = MathHelper.cos(-Player.getYaw() * 0.017453292F - (float)Math.PI);
            final float sinYaw = MathHelper.sin(-Player.getYaw() * 0.017453292F - (float)Math.PI);
            final float cosPitch = -MathHelper.cos(-Player.getPitch() * 0.017453292F);
            final float sinPitch = MathHelper.sin(-Player.getPitch() * 0.017453292F);
            
            return new Vec3d(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        }
    
        public static boolean faceVector(final Vec3d vector) {
            final float[] rotations = getNeededRotations(vector);
        
            final float oldYaw = Player.getPrevRotationYaw();
            final float oldPitch = Player.getPrevRotationPitch();
        
            Player.setYaw(limitAngleChange(oldYaw, rotations[0], 30));
            Player.setPitch(rotations[1]);
        
            return Math.abs(oldYaw - rotations[0]) + Math.abs(oldPitch - rotations[1]) < 1F;
        }
    
        public static boolean faceEntity(final Entity entity) {
            // get position & rotation
            final Vec3d eyes = getEyeVector();
            final Vec3d look = Server.getLookVector();
        
            // try to face center of boundingBox
            final AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
            
            if(faceVector(boundingBox.getCenter()))
                return true;
        
            // if not facing center, check if facing anything in boundingBox
            return boundingBox.calculateIntercept(eyes, eyes.add(look.scale(6))) != null;
        }
    
        public static float getAngleRotation(final Vec3d vector) {
            final float[] rotations = getNeededRotations(vector);
            final float diffYaw = MathHelper.wrapDegrees(Player.getYaw()) - rotations[0];
            final float diffPitch = MathHelper.wrapDegrees(Player.getPitch()) - rotations[1];
            final float angle = (float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        
            return angle;
        }
    
        public static float getHorizontalAngleRotation(final Vec3d vector) {
            final float[] needed = getNeededRotations(vector);
            final float angle = MathHelper.wrapDegrees(Player.getYaw()) - needed[0];
        
            return angle;
        }
    }
    
    public static final class Server {
        
        public static Vec3d getLookVector() {
            final float cosYaw = MathHelper.cos(-serverYaw * 0.017453292F - (float)Math.PI);
            final float sinYaw = MathHelper.sin(-serverYaw * 0.017453292F - (float)Math.PI);
            final float cosPitch = -MathHelper.cos(-serverPitch * 0.017453292F);
            final float sinPitch = MathHelper.sin(-serverPitch * 0.017453292F);
            
            return new Vec3d(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        }
    
        public static boolean faceVector(final Vec3d vector) {
            // use fake rotation in next packet
            fakeRotation = true;
        
            final float[] rotations = getNeededRotations(vector);
        
            serverYaw = limitAngleChange(serverYaw, rotations[0], 30);
            serverPitch = rotations[1];
        
            return Math.abs(serverYaw - rotations[0]) < 1F;
        }
    
        public static void faceVectorPacketInstant(final Vec3d vector) {
            final float[] rotations = getNeededRotations2(vector);
    
            Minekraft.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1], Player.isOnGround()));
        }
    
        public static boolean faceEntity(final Entity entity) {
            // get position & rotation
            final Vec3d eyes = getEyeVector();
            final Vec3d look = getLookVector();
        
            // try to face center of boundingBox
            final AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
            
            if(faceVector(boundingBox.getCenter()))
                return true;
        
            // if not facing center, check if facing anything in boundingBox
            return boundingBox.calculateIntercept(eyes, eyes.add(look.scale(6))) != null;
        }
    
        public static float getAngleRotation(final Vec3d vector) {
            final float[] rotations = getNeededRotations(vector);
            final float diffYaw = serverYaw - rotations[0];
            final float diffPitch = serverPitch - rotations[1];
            final float angle = (float)Math.sqrt(diffYaw * diffYaw + diffPitch * diffPitch);
        
            return angle;
        }
    
        public static void updateRotation() {
            // disable fake rotation in next packet unless manually enabled again
            if(fakeRotation) {
                fakeRotation = false;
                return;
            }
        
            // slowly synchronize server rotation with client
            serverYaw = limitAngleChange(serverYaw, Player.getYaw(), 30);
            serverPitch = Player.getPitch();
        }
    }
}
