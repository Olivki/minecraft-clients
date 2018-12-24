package se.proxus.kanon.event4j.events.render;

import net.minecraft.client.entity.EntityPlayerSP;
import se.proxus.kanon.render.Render3D;
import se.proxus.kanon.utils.templates.Location;
import se.proxus.kanon.wrapper.minecraft.Minekraft;
import se.proxus.kanon.wrapper.minecraft.Player;

public class EventRender3D extends Render3D {

    public double getX(final double x) {
        final double renderX = getPlayer().lastTickPosX +
                (getPlayer().posX - getPlayer().lastTickPosX) * getRenderPartialTicks();
        return x - renderX;
    }

    public double getY(final double y) {
        final double renderY = getPlayer().lastTickPosY +
                (getPlayer().posY - getPlayer().lastTickPosY) * getRenderPartialTicks();
        return y - renderY;
    }

    public double getZ(final double z) {
        final double renderZ = getPlayer().lastTickPosZ +
                (getPlayer().posZ - getPlayer().lastTickPosZ) * getRenderPartialTicks();
        return z - renderZ;
    }

    public float getYaw(final float yaw){
        final float renderYaw = getPlayer().prevRotationYaw +
                (getPlayer().rotationYaw - getPlayer().prevRotationYaw) * getRenderPartialTicks();
        return renderYaw - yaw;
    }

    public float getPitch(final float pitch) {
        final float renderPitch = getPlayer().prevRotationPitch +
                (getPlayer().rotationPitch - getPlayer().prevRotationPitch) * getRenderPartialTicks();
        return pitch - renderPitch;
    }

    public Location getLocation(final double x, final double y, final double z){
        return new Location(getX(x), getY(y), getZ(z));
    }
    
    public Location getLocation(final Location location){
        return new Location(getX(location.getX()), getY(location.getY()), getZ(location.getZ()));
    }

    private EntityPlayerSP getPlayer() {
        return Player.getPlayer();
    }

    private float getRenderPartialTicks() {
        return Minekraft.getRenderPartialTicks();
    }
}