package se.proxus.events.list.client;

import net.minecraft.client.Minecraft;
import se.proxus.events.Event;

public class EventRendered3D extends Event {

    public EventRendered3D() {
	super("Rendered 3D");
    }

    public double getRenderX(final double x) {
	double x1 = Minecraft.getMinecraft().thePlayer.lastTickPosX
		+ (Minecraft.getMinecraft().thePlayer.posX - Minecraft
			.getMinecraft().thePlayer.lastTickPosX)
		* Minecraft.getMinecraft().getTimer().renderPartialTicks;
	return x - x1;
    }

    public double getRenderY(final double y) {
	double y1 = Minecraft.getMinecraft().thePlayer.lastTickPosY
		+ (Minecraft.getMinecraft().thePlayer.posY - Minecraft
			.getMinecraft().thePlayer.lastTickPosY)
		* Minecraft.getMinecraft().getTimer().renderPartialTicks;
	return y - y1;
    }

    public double getRenderZ(final double z) {
	double z1 = Minecraft.getMinecraft().thePlayer.lastTickPosZ
		+ (Minecraft.getMinecraft().thePlayer.posZ - Minecraft
			.getMinecraft().thePlayer.lastTickPosZ)
		* Minecraft.getMinecraft().getTimer().renderPartialTicks;
	return z - z1;
    }

    public float getRenderYaw(final float yaw) {
	float yaw1 = Minecraft.getMinecraft().thePlayer.prevRotationYaw
		+ (Minecraft.getMinecraft().thePlayer.rotationYaw - Minecraft
			.getMinecraft().thePlayer.prevRotationYaw)
		* Minecraft.getMinecraft().getTimer().renderPartialTicks;
	return yaw1 - yaw;
    }

    public float getRenderPitch(final float pitch) {
	float pitch1 = Minecraft.getMinecraft().thePlayer.prevRotationPitch
		+ (Minecraft.getMinecraft().thePlayer.rotationPitch - Minecraft
			.getMinecraft().thePlayer.prevRotationPitch)
		* Minecraft.getMinecraft().getTimer().renderPartialTicks;
	return pitch - pitch1;
    }
}