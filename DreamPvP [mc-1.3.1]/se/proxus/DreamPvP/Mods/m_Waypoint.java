package se.proxus.DreamPvP.Mods;

import net.minecraft.src.*;
import se.proxus.DreamPvP.Interfaces.*;
import se.proxus.DreamPvP.Utils.*;
import se.proxus.DreamPvP.Utils.Placeholders.*;

import static org.lwjgl.input.Keyboard.*;

import static org.lwjgl.opengl.GL11.*;

public class m_Waypoint extends Base_Mod implements Renderer {

	private int espSize = 0;
	private int espRefreshTimer = 10;
	private int espCheckSize = 32;

	public m_Waypoint() {
		super('7', "Waypoint", "Draws a box around places.", KEY_P, "World", "[§eW§r] ");
	}

	@Override
	public void onEnabled() {
		dp.interfaces.renderArray.add(this);
	}

	@Override
	public void onDisabled() {
		dp.interfaces.renderArray.remove(this);
	}

	@Override
	public void onRendered() {
		for(u_Waypoint wt : dp.settings.waypointArray) {
			if(wt.ip.equalsIgnoreCase(dp.settings.curServerIP)) {
				renderWaypoint(dp.utils.getDistance(wt.name, dp.mc.thePlayer, wt.x, wt.z), wt.x, wt.y, wt.z, wt.r, wt.g, wt.b);
			}
		}
	}

	public void renderWaypoint(String s, double x, double y, double z, double r, double g, double b) {
		EntityPlayerSP ep = dp.mc.thePlayer;

		double playerX = dp.mc.thePlayer.lastTickPosX + (dp.mc.thePlayer.posX - dp.mc.thePlayer.lastTickPosX) * (double)dp.mc.timer.renderPartialTicks;
		double playerY = dp.mc.thePlayer.lastTickPosY + (dp.mc.thePlayer.posY - dp.mc.thePlayer.lastTickPosY) * (double)dp.mc.timer.renderPartialTicks;
		double playerZ = dp.mc.thePlayer.lastTickPosZ + (dp.mc.thePlayer.posZ - dp.mc.thePlayer.lastTickPosZ) * (double)dp.mc.timer.renderPartialTicks;
		double d = (int)x - playerX;
		double d1 = (int)y - playerY;
		double d2 = (int)z - playerZ;

		//{dp.render.drawWaypointESP(x, y, z, r, g, b);}
		{
			if(dp.settings.espMode == 1) {
				dp.render.drawSphere(r, g, b, 1.0D, d, d1, d2, 2, 20, 25, 1.1F); 
			} if(dp.settings.espMode == 2) {
				dp.render.enableDefaults();
				glColor4d(r, g, b, 0.15D);
				dp.render.drawBox(d, d1 - 1.0D, d2, d + 1.0D, d1 + 1.0D, d2 + 1.0D);
				glColor4d(r, g, b, 1.0D);
				dp.render.drawOutlinedBox(d, d1 - 1.0D, d2, d + 1.0D, d1 + 1.0D, d2 + 1.0D, 1.0F);
				dp.render.disableDefaults();
			}
			
			dp.render.drawTracerTo(x, y, z, r, g, b, 2.0F); 
			dp.render.drawTag(s, x, y, z);
		}
	}
}
