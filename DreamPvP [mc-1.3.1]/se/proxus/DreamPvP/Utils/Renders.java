package se.proxus.DreamPvP.Utils;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.*;

import se.proxus.DreamPvP.Gui.Utils.TTF;

public class Renders extends Utils {

	public RenderLiving rl;

	public static void enableDefaults() {
		dp.mc.entityRenderer.disableLightmap(1.0D);
		glEnable(3042 /*GL_BLEND*/);
		glDisable(3553 /*GL_TEXTURE_2D*/);
		glDisable(2896 /*GL_LIGHTING*/);
		glDisable(2929 /*GL_DEPTH_TEST*/);
		glDepthMask(false);
		glBlendFunc(770, 771);
		glEnable(2848 /*GL_LINE_SMOOTH*/);
		glPushMatrix();
	}

	public static void disableDefaults() {
		glPopMatrix();
		glDisable(2848 /*GL_LINE_SMOOTH*/);
		glDepthMask(true);
		glEnable(2929 /*GL_DEPTH_TEST*/);
		glEnable(3553 /*GL_TEXTURE_2D*/);
		glEnable(2896 /*GL_LIGHTING*/);
		glDisable(3042 /*GL_BLEND*/);
		dp.mc.entityRenderer.enableLightmap(1.0D);
	}

	public static void drawBox(double x, double y, double z, double x2, double y2, double z2) {
		glBegin(GL_QUADS);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_QUADS);      
		glVertex3d(x, y2, z);        
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_QUADS);       
		glVertex3d(x, y, z);        
		glVertex3d(x2, y, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y, z);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_QUADS);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();
	}

	public static void drawOutlinedBox(double x, double y, double z, double x2, double y2, double z2, float l1) {
		glLineWidth(l1);

		glBegin(GL_LINES);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_LINES);      
		glVertex3d(x, y2, z);        
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_LINES);       
		glVertex3d(x, y, z);        
		glVertex3d(x2, y, z);
		glVertex3d(x2, y, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y, z);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y, z); 
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();
	}

	public static void drawLines(double x, double y, double z, double x2, double y2, double z2, float line) {
		glLineWidth(line);
		glBegin(GL_LINES);
		glVertex3d(x, y2, z);
		glVertex3d(x, y, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x2, y2, z);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y2, z);
		glVertex3d(x2, y2, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y, z);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y, z2);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y2, z2);
		glVertex3d(x2, y2, z2);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y, z2);
		glVertex3d(x2, y, z2);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y, z2);
		glVertex3d(x, y, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x2, y, z2);
		glVertex3d(x2, y, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x, y2, z2);
		glVertex3d(x, y2, z);
		glEnd();

		glBegin(GL_LINES);
		glVertex3d(x2, y2, z2);
		glVertex3d(x2, y2, z);
		glEnd();
	}

	public static void drawSphere(double d1, double d2, double d3, double d4, double x, double y, double z, float size, int slices, int stacks, float lWidth) {
		Sphere sphere = new Sphere();

		enableDefaults();
		glColor4d(d1, d2, d3, d4);
		glTranslated(x, y, z);
		glLineWidth(lWidth);
		sphere.setDrawStyle(GLU.GLU_SILHOUETTE);
		sphere.draw(size, slices, stacks);
		disableDefaults();
	}

	public static void drawPlayerESP(double x, double y, double z, EntityPlayer e) {
		ArrayList friend = dp.settings.friendArray;
		ArrayList enemy = dp.settings.enemyArray;
		boolean isFriend = friend.contains(e.username);
		boolean isEnemy = enemy.contains(e.username);
		boolean isLockedOnto = dp.settings.lockUsername.equalsIgnoreCase(e.username);
		double d1 = 1.0D, d2 = 1.0D, d3 = 1.0D;

		if(isEnemy && !isFriend) {
			d1 = 1.0D;
			d2 = 0.0D;
			d3 = 0.0D;
		} else {
			d1 = 1.0D;
			d2 = 1.0D;
			d3 = 1.0D;
		} if(isFriend && !isEnemy) {
			d1 = 0.0D;
			d2 = 0.0D;
			d3 = 1.0D;
		} else {
			d1 = 1.0D;
			d2 = 1.0D;
			d3 = 1.0D;
		} if(isLockedOnto) {
			d1 = 0.0D;
			d2 = 1.0D;
			d3 = 0.0D;
		} else {
			d1 = 1.0D;
			d2 = 1.0D;
			d3 = 1.0D;
		}
		
		if(dp.settings.espMode == 1) {
			glPushMatrix();
			drawSphere(d1, d2, d3, 1.0D, x, y, z, 2.0F, 20, 15, 1.1F);
			glPopMatrix();
		} if(dp.settings.espMode == 2) {
			enableDefaults();
			glColor4d(d1, d2, d3, 0.15D);
			drawBox(x - 0.5D, y - 1.0D, z - 0.5D, x + 0.5D, y + 1.0D, z + 0.5D);
			glColor4d(d1, d2, d3, 1.0D);
			drawOutlinedBox(x - 0.5D, y - 1.0D, z - 0.5D, x + 0.5D, y + 1.0D, z + 0.5D, 1.0F);
			disableDefaults();
		}
	}

	public static void drawBlockESP(double x, double y, double z, float r, float g, float b) {
		EntityPlayerSP ep = dp.mc.thePlayer;

		double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)dp.mc.timer.renderPartialTicks;
		double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)dp.mc.timer.renderPartialTicks;
		double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)dp.mc.timer.renderPartialTicks;
		double d3 = x - d;
		double d4 = y - d1;
		double d5 = z - d2;

		glPushMatrix();
		glEnable(3042 /*GL_BLEND*/);
		glDisable(3553 /*GL_TEXTURE_2D*/);
		glDisable(2896 /*GL_LIGHTING*/);
		glDisable(2929 /*GL_DEPTH_TEST*/);
		glDepthMask(false);
		glBlendFunc(770, 771);
		glEnable(2848 /*GL_LINE_SMOOTH*/);

		glColor4f(r, g, b, 0.15F);
		drawBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1);
		glColor4f(r, g, b, 1.0F);
		drawOutlinedBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1, 1.0F);
		//drawLines(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1, 1.0F);

		glDisable(2848 /*GL_LINE_SMOOTH*/);
		glDepthMask(true);
		glEnable(2929 /*GL_DEPTH_TEST*/);
		glEnable(3553 /*GL_TEXTURE_2D*/);
		glEnable(2896 /*GL_LIGHTING*/);
		glDisable(3042 /*GL_BLEND*/);
		glPopMatrix();
	}

	public static void drawWaypointESP(double x, double y, double z, float r, float g, float b) {
		EntityPlayerSP ep = dp.mc.thePlayer;

		double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)dp.mc.timer.renderPartialTicks;
		double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)dp.mc.timer.renderPartialTicks;
		double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)dp.mc.timer.renderPartialTicks;
		double d3 = x - d;
		double d4 = y - d1;
		double d5 = z - d2;

		glPushMatrix();
		glEnable(3042 /*GL_BLEND*/);
		glDisable(3553 /*GL_TEXTURE_2D*/);
		glDisable(2896 /*GL_LIGHTING*/);
		glDisable(2929 /*GL_DEPTH_TEST*/);
		glDepthMask(false);
		glBlendFunc(770, 771);
		glEnable(2848 /*GL_LINE_SMOOTH*/);

		glColor4f(r, g, b, 0.15F);
		drawBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1);
		glColor4f(r, g, b, 1.0F);
		drawOutlinedBox(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1, 1.0F);
		drawLines(d3, d4, d5, d3 + 1, d4 + 1, d5 + 1, 1.0F);

		glDisable(2848 /*GL_LINE_SMOOTH*/);
		glDepthMask(true);
		glEnable(2929 /*GL_DEPTH_TEST*/);
		glEnable(3553 /*GL_TEXTURE_2D*/);
		glEnable(2896 /*GL_LIGHTING*/);
		glDisable(3042 /*GL_BLEND*/);
		glPopMatrix();
	}

	public static void drawTracerTo(double x, double y, double z, double r, double g, double b, float lWidth) {
		EntityPlayerSP ep = dp.mc.thePlayer;

		double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)dp.mc.timer.renderPartialTicks;
		double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)dp.mc.timer.renderPartialTicks;
		double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)dp.mc.timer.renderPartialTicks;
		double d3 = x - d;
		double d4 = y - d1;
		double d5 = z - d2;

		enableDefaults();
		glColor4d(r, g, b, 1.0D);
		glLineWidth(lWidth);

		glBegin(GL_LINES);
		glVertex2d(0.0D, 0.0D);
		glVertex3d(d3, d4, d5);
		glEnd();

		disableDefaults();
	}

	public static void drawTag(String s, double x, double y, double z) {
		EntityPlayerSP ep = dp.mc.thePlayer;

		double playerX = dp.mc.thePlayer.lastTickPosX + (dp.mc.thePlayer.posX - dp.mc.thePlayer.lastTickPosX) * (double)dp.mc.timer.renderPartialTicks;
		double playerY = dp.mc.thePlayer.lastTickPosY + (dp.mc.thePlayer.posY - dp.mc.thePlayer.lastTickPosY) * (double)dp.mc.timer.renderPartialTicks;
		double playerZ = dp.mc.thePlayer.lastTickPosZ + (dp.mc.thePlayer.posZ - dp.mc.thePlayer.lastTickPosZ) * (double)dp.mc.timer.renderPartialTicks;
		double d = (int)x - playerX;
		double d1 = (int)y - playerY;
		double d2 = (int)z - playerZ;

		RenderManager renderManager = Render.getRenderManager();
		float f3 = (float)(dp.mc.thePlayer.posX - x);
		float f1 = (float)(dp.mc.thePlayer.posY - y);
		float f2 = (float)(dp.mc.thePlayer.posZ - z);
		float f =  MathHelper.sqrt_float(f3 * f3 + f1 * f1 + f2 * f2);

		dp.mc.entityRenderer.disableLightmap(0D);
		d += 0.5D;
		d2 += 0.5D;
		FontRenderer fontrenderer = dp.mc.fontRenderer;
		f1 = 1.6F;
		f2 = 0.01666667F * f1;
		int color = 0xFFFFFFFF;

		if (f > 3F) {
			f2 *= f / 3F;
		}     

		float scale = f / 100;
		RenderManager renderManager1 = Render.getRenderManager();
		glPushMatrix();
		glTranslatef((float)d + 0.0F, (float)d1 + 2.5F, (float)d2 - 0.5F);
		glNormal3f(0.0F, 1.0F, 0.0F);
		glRotatef(-renderManager1.playerViewY, 0.0F, 1.0F, 0.0F);
		glRotatef(renderManager1.playerViewX, 1.0F, 0.0F, 0.0F);

		if(f > 3) {
			glScalef(-scale, -scale, scale);
		} else {
			glScalef(-f2, -f2, f2);
		}

		glDisable(2896 /*GL_LIGHTING*/);
		glDisable(2929 /*GL_DEPTH_TEST*/);
		glEnable(3042 /*GL_BLEND*/);
		glBlendFunc(770, 771);
		//Tessellator tessellator = Tessellator.instance;
		//tessellator.startDrawingQuads();
		byte byte0 = 0;
		int i = fontrenderer.getStringWidth(s) / 2;
		/*tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.35F);
		tessellator.addVertex(-i - 1, -1 + byte0, 0.0D);
		tessellator.addVertex(-i - 1, 9 + byte0, 0.0D);
		tessellator.addVertex(i + 2, 9 + byte0, 0.0D);
		tessellator.addVertex(i + 2, -1 + byte0, 0.0D);
		tessellator.draw();*/
		fontrenderer.drawStringWithShadow(s, -fontrenderer.getStringWidth(s) / 2, byte0, color);
		fontrenderer.drawStringWithShadow(s, -fontrenderer.getStringWidth(s) / 2, byte0, color);
		glEnable(2929 /*GL_DEPTH_TEST*/);
		glEnable(2896 /*GL_LIGHTING*/);
		glDisable(3042 /*GL_BLEND*/);
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		glPopMatrix();
		dp.mc.entityRenderer.enableLightmap(0D);
	}

	public void renderTags(RenderLiving rl, EntityLiving e, double x, double y, double z) {
		this.rl = rl;

	}

	public static void nukeESP(double x, double y, double z, float r, float g, float b) {
		EntityPlayerSP ep = dp.mc.thePlayer;

		double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)dp.mc.timer.renderPartialTicks;
		double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)dp.mc.timer.renderPartialTicks;
		double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)dp.mc.timer.renderPartialTicks;
		double d3 = x - d;
		double d4 = y - d1;
		double d5 = z - d2;

		glPushMatrix();
		glEnable(3042 /*GL_BLEND*/);
		glDisable(3553 /*GL_TEXTURE_2D*/);
		glDisable(2896 /*GL_LIGHTING*/);
		glDisable(2929 /*GL_DEPTH_TEST*/);
		glDepthMask(false);
		glBlendFunc(770, 771);
		glEnable(2848 /*GL_LINE_SMOOTH*/);

		glColor4f(r, g, b, 0.15F);
		drawBox(d3, d4, d5, d3 + 1, d4  + 1, d5 + 1);
		glColor4f(r, g, b, 1.0F);
		drawOutlinedBox(d3, d4, d5, d3 + 1, d4  + 1, d5 + 1, 1.0F);

		glDisable(2848 /*GL_LINE_SMOOTH*/);
		glDepthMask(true);
		glEnable(2929 /*GL_DEPTH_TEST*/);
		glEnable(3553 /*GL_TEXTURE_2D*/);
		glEnable(2896 /*GL_LIGHTING*/);
		glDisable(3042 /*GL_BLEND*/);
		glPopMatrix();
	}
}