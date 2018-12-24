package ab.Bytes.Render;

import java.util.ArrayList;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

import ab.Bytes.Utils.*;

public class r_Waypoint extends Base_Render {
	
	public static ArrayList<Waypoint> waypointArray = new ArrayList<Waypoint>();

	@Override
	public void onRenderGlobal() {
		for(Waypoint wp : waypointArray) {
			playerESP(wp.posX, wp.posY, wp.posZ, wp.colR, wp.colG, wp.colB);
		}
	}
	
	public void renderWaypoint(String name, double posX, double posY, double posZ, double colR, double colG, double colB) {
		bs.mc.entityRenderer.disableLightmap(1.0D);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(2929);
		GL11.glColor4d(colR, colG, colB, 0.3D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);  
		GL11.glDepthMask(false);
		GL11.glLineWidth(2.0F);
		drawBoundingBox(new AxisAlignedBB(posX - 0.5D, posY, posZ - 0.5D, posX + 0.5D, posY + 2.0D, posZ + 0.5D));
		GL11.glColor4d(colR, colG, colB, 1.0D);
		drawOutlinedBoundingBox(new AxisAlignedBB(posX - 0.5D, posY, posZ - 0.5D, posX + 0.5D, posY + 2.0D, posZ + 0.5D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);  
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glDisable(3042);
	}
	
	public void playerESP(double d, double d1, double d2, double R, double B, double G){
		bs.mc.entityRenderer.disableLightmap(0.D);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(2.0F);
		GL11.glColor4d(R, B, G, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);  
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(false);
		drawBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		GL11.glColor4d(R, B, G, 1.0F);
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		GL11.glLineWidth(10.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D); 
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(true);
		GL11.glDisable(3042 /*GL_BLEND*/);
	}
}