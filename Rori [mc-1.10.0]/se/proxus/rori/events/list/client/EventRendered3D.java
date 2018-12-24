package se.proxus.rori.events.list.client;

import se.proxus.rori.events.Event;
import se.proxus.rori.tools.Location;
import se.proxus.rori.tools.Tools;

/**
 * 
 * Can be found in the RenderGlobal class
 * 
 */
public class EventRendered3D extends Event {

	public EventRendered3D() {
		super("Event Rendered 3D");
	}

	public double getRenderX(final double x) throws Exception {
		double renderX = Tools.getPlayer().lastTickPosX
				+ (Tools.getPlayer().posX - Tools.getPlayer().lastTickPosX)
				* Tools.getTimer().renderPartialTicks;
		return x - renderX;
	}

	public double getRenderY(final double y) throws Exception {
		double renderY = Tools.getPlayer().lastTickPosY
				+ (Tools.getPlayer().posY - Tools.getPlayer().lastTickPosY)
				* Tools.getTimer().renderPartialTicks;
		return y - renderY;
	}

	public double getRenderZ(final double z) throws Exception {
		double renderZ = Tools.getPlayer().lastTickPosZ
				+ (Tools.getPlayer().posZ - Tools.getPlayer().lastTickPosZ)
				* Tools.getTimer().renderPartialTicks;
		return z - renderZ;
	}

	public float getRenderYaw(final float yaw) throws Exception {
		float renderYaw = Tools.getPlayer().prevRotationYaw
				+ (Tools.getPlayer().rotationYaw - Tools.getPlayer().prevRotationYaw)
				* Tools.getTimer().renderPartialTicks;
		return renderYaw - yaw;
	}

	public float getRenderPitch(final float pitch) throws Exception {
		float renderPitch = Tools.getPlayer().prevRotationPitch
				+ (Tools.getPlayer().rotationPitch - Tools.getPlayer().prevRotationPitch)
				* Tools.getTimer().renderPartialTicks;
		return pitch - renderPitch;
	}

	public Location getRenderLocation(final double x, final double y, final double z)
			throws Exception {
		return new Location(getRenderX(x), getRenderY(y), getRenderZ(z));
	}
}