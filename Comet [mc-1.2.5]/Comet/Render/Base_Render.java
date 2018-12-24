package Comet.Render;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Quadric;

import Comet.Utils.Waypoints;

public class Base_Render {

	public static Minecraft mc = Minecraft.theMinecraft;

	public static Mark espBlock[];
	public static int espSize = 0;
	public static int espRefreshTimer = 10;
	public static int espCheckSize = 32;

	public static Cylinder cylinder = new Cylinder();

	public Base_Render() {
		espBlock = new Mark[10000000];
	}

	public static void renderGlobal() {
		//renderESP();
		for(int l = 0; l < mc.comet.settings.waypoints.size(); l++) {
			Waypoints point = (Waypoints)mc.comet.settings.waypoints.get(l);
			renderWaypoint(point.name, point.posX, point.posY, point.posZ);
		}
	}

	public static void renderPlayer(EntityPlayer par1EntityPlayer, double d, double d1, double d2) {
		renderPlayerESP(d, d1, d2, 1F, 1.0D, 1.0D, 1.0D);
	}

	public static void renderPlayerESP(double d, double d1, double d2, float F, double R, double B, double G){
		mc.entityRenderer.disableLightmap(F);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(2.0F);
		GL11.glColor4d(R, B, G, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);  
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(false);
		drawBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		GL11.glColor4d(R, B, G, 1.0F);

		/*drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.0, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));*/

		drawOutlinedBoundingBox(new AxisAlignedBB(d + 0.1, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d + 0.2, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d + 0.3, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d + 0.4, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));

		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.1, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.2, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.3, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.4, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));

		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 + 0.1, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 + 0.2, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 + 0.3, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 + 0.4, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 + 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));

		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 - 0.1, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 - 0.2, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 - 0.3, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 - 0.4, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));

		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.2, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.3, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.4, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.5, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.6, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.7, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.8, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.9, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.0, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.2, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.3, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.4, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.5, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.6, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.7, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.8, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.9, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 2.0, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		GL11.glLineWidth(1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D); 
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(true);
		GL11.glDisable(3042 /*GL_BLEND*/);
	}

	public static void renderESP() {
		espRefreshTimer--;
		if (espRefreshTimer == 0) {espRefresh(); espRefreshTimer = 40;}

		double playerX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double)mc.timer.renderPartialTicks;
		double playerY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)mc.timer.renderPartialTicks;
		double playerZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double)mc.timer.renderPartialTicks;

		for (int cur = 0; cur < espSize; cur++) {
			if (espBlock[cur].type==2) //Redstone
				{colorESP((double)espBlock[cur].posX-playerX, (double)espBlock[cur].posY-playerY, (double)espBlock[cur].posZ-playerZ, 1, 2, 2, 2);}
		}
	}

	public static void espRefresh() {
		espSize = 0;
		for (int y = 0; y < 256; y++) {

			for (int x = 0; x < espCheckSize; x++) {

				for (int z = 0; z < espCheckSize; z++) {

					int cX = (int)mc.thePlayer.posX-espCheckSize/2+x;
					int cY = y;
					int cZ = (int)mc.thePlayer.posZ-espCheckSize/2+z;
					int id = mc.theWorld.getBlockId(cX, cY, cZ);
					if (id == 57 || id == 56) {espBlock[espSize++] = new Mark(cX, cY, cZ, 2);}
				}
			}
		}
	}
	
	public static void blockESP(double d, double d1, double d2, float f, double R, double B, double G) {
        mc.entityRenderer.disableLightmap(f);
    	GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(GL11.GL_TEXTURE_2D); 
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
        GL11.glDepthMask(false);
        GL11.glLineWidth(2.0F); //sets line width to two
        GL11.glColor4d(R, B, G, 0.15F);
        drawBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
        GL11.glColor4d(R, B, G, 1F);
        chestLines(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
        GL11.glEnable(GL11.GL_TEXTURE_2D); 
        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
        GL11.glDepthMask(true);
        GL11.glDisable(3042 /*GL_BLEND*/);
    }

	public static void renderLivingLabel(EntityLiving par1EntityLiving, String par2Str, double par3, double par5, double par7, int par9) {
		mc.entityRenderer.disableLightmap(0D);
		RenderManager renderManager = RenderLiving.renderManager;
		float f = par1EntityLiving.getDistanceToEntity(renderManager.livingPlayer);

		if (f > (float)par9) {return;}

		FontRenderer fontrenderer = mc.fontRenderer;
		float f1 = 1.6F;
		float f2 = 0.01666667F * f1;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par3 + 0.0F, (float)par5 + 2.3F, (float)par7);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-f2, -f2, f2);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.instance;

		byte byte0 = 0;

		if (par2Str.equals("deadmau5")) {byte0 = -10;}

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		int i = fontrenderer.getStringWidth(par2Str) / 2;
		mc.comet.ingame.drawSmallWBorderedRect(-2 - i, -2, 2 + i, 10, 0xFF000000, 0x500E0E0E);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		fontrenderer.drawStringWithShadow(par2Str, -fontrenderer.getStringWidth(par2Str) / 2, byte0, 0x20ffffff);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		fontrenderer.drawStringWithShadow(par2Str, -fontrenderer.getStringWidth(par2Str) / 2, byte0, -1);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		mc.entityRenderer.enableLightmap(0D);
	}

	public static void renderBlockBreaking(EntityPlayer I1, MovingObjectPosition I2, int I3, ItemStack I4, float I5) {
		if (I3 == 0 && I2.typeOfHit == EnumMovingObjectType.TILE) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glLineWidth(3.0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			float f = 0.002F;
			int i = mc.theWorld.getBlockId(I2.blockX, I2.blockY, I2.blockZ);

			if (i > 0) {
				Block.blocksList[i].setBlockBoundsBasedOnState(mc.theWorld, I2.blockX, I2.blockY, I2.blockZ);
				double d = I1.lastTickPosX + (I1.posX - I1.lastTickPosX) * (double)I5;
				double d1 = I1.lastTickPosY + (I1.posY - I1.lastTickPosY) * (double)I5;
				double d2 = I1.lastTickPosZ + (I1.posZ - I1.lastTickPosZ) * (double)I5;
				drawOutlinedBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(mc.theWorld, I2.blockX, I2.blockY, I2.blockZ).expand(f, f, f - (mc.renderGlobal.damagePartialTime / 2)).getOffsetBoundingBox(-d, -d1, -d2));
				drawOutlinedBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(mc.theWorld, I2.blockX, I2.blockY, I2.blockZ).expand(f - (mc.renderGlobal.damagePartialTime / 2), f, f).getOffsetBoundingBox(-d, -d1, -d2));
				drawOutlinedBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(mc.theWorld, I2.blockX, I2.blockY, I2.blockZ).expand(f, f - (mc.renderGlobal.damagePartialTime / 2), f).getOffsetBoundingBox(-d, -d1, -d2));
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.11F);
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	public static void renderSel(EntityPlayer I1, MovingObjectPosition I2, int I3, ItemStack I4, float I5) {
		//renderBuildPrev(I1, I2, I3, I4, I5);

		if (I3 == 0 && I2.typeOfHit == EnumMovingObjectType.TILE) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glLineWidth(3.0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			float f = 0.002F;
			int i = mc.theWorld.getBlockId(I2.blockX, I2.blockY, I2.blockZ);

			if (i > 0) {
				Block.blocksList[i].setBlockBoundsBasedOnState(mc.theWorld, I2.blockX, I2.blockY, I2.blockZ);
				double d = I1.lastTickPosX + (I1.posX - I1.lastTickPosX) * (double)I5;
				double d1 = I1.lastTickPosY + (I1.posY - I1.lastTickPosY) * (double)I5;
				double d2 = I1.lastTickPosZ + (I1.posZ - I1.lastTickPosZ) * (double)I5;
				drawOutlinedBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(mc.theWorld, I2.blockX, I2.blockY, I2.blockZ).expand(f, f, f).getOffsetBoundingBox(-d, -d1, -d2));
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.11F);
				drawBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(mc.theWorld, I2.blockX, I2.blockY, I2.blockZ).expand(f, f, f).getOffsetBoundingBox(-d, -d1, -d2));
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	public static void renderBuildPrev(EntityPlayer I1, MovingObjectPosition I2, int I3, ItemStack I4, float I5) {
		if (I3 == 0 && I2.typeOfHit == EnumMovingObjectType.TILE) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glLineWidth(3.0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			float f = 0.002F;
			int i = mc.theWorld.getBlockId(I2.blockX, I2.blockY, I2.blockZ);

			if (i > 0) {
				Block.blocksList[i].setBlockBoundsBasedOnState(mc.theWorld, I2.blockX, I2.blockY, I2.blockZ);
				double d = I1.lastTickPosX + (I1.posX - I1.lastTickPosX) * (double)I5;
				double d1 = I1.lastTickPosY + (I1.posY - I1.lastTickPosY) * (double)I5;
				double d2 = I1.lastTickPosZ + (I1.posZ - I1.lastTickPosZ) * (double)I5;
				drawOutlinedBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(mc.theWorld, I2.blockX, I2.blockY + 1, I2.blockZ).expand(f, f, f).getOffsetBoundingBox(-d, -d1, -d2));
				drawOutlinedBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(mc.theWorld, I2.blockX + 1, I2.blockY + 1, I2.blockZ).expand(f, f, f).getOffsetBoundingBox(-d, -d1, -d2));
				drawOutlinedBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(mc.theWorld, I2.blockX, I2.blockY + 1, I2.blockZ + 1).expand(f, f, f).getOffsetBoundingBox(-d, -d1, -d2));
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.11F);
				drawBoundingBox(Block.blocksList[i].getSelectedBoundingBoxFromPool(mc.theWorld, I2.blockX, I2.blockY + 1, I2.blockZ).expand(f, f, f).getOffsetBoundingBox(-d, -d1, -d2));
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	public static void renderWaypoint(String s, double x, double y, double z) {
		if (mc == null) {
			return;
		}
		espRefreshTimer--;
		if (espRefreshTimer == 0) {espRefreshTimer=40;}

		double playerX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double)mc.timer.renderPartialTicks;
		double playerY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)mc.timer.renderPartialTicks;
		double playerZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double)mc.timer.renderPartialTicks;
		int id = mc.theWorld.getBlockId((int)x, (int)y, (int)z);
		double d = (int)x - playerX;
		double d1 = (int)y - playerY;
		double d2 = (int)z - playerZ;
		{waypointESP(d, d1 - 4D, d2, 1F, 255D, 255D, 255D);}

		mc.entityRenderer.disableLightmap(0D);
		RenderManager renderManager = RenderLiving.renderManager;
		float f3 = (float)(mc.thePlayer.posX - x);
		float f1 = (float)(mc.thePlayer.posY - y);
		float f2 = (float)(mc.thePlayer.posZ - z);
		float f =  MathHelper.sqrt_float(f3 * f3 + f1 * f1 + f2 * f2);

		mc.entityRenderer.disableLightmap(0D);
		d += 0.5D;
		d2 += 0.5D;
		FontRenderer fontrenderer = mc.fontRenderer;
		f1 = 1.6F;
		f2 = 0.01666667F * f1;
		int color = 0xFFFFFFFF;
		if (f > 10F) {
			f2 *= f / 10F;
		}     
		RenderManager renderManager1 = RenderLiving.renderManager;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d + 0.0F, (float)d1 + 2.5F, (float)d2 - 0.5F);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager1.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager1.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-f2, -f2, f2);
		GL11.glDisable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		Tessellator tessellator = Tessellator.instance;
		byte byte0 = 0;
		mc.comet.ingame.fr.drawStringWithShadow(s, -fontrenderer.getStringWidth(s) / 2, byte0, color);
		mc.comet.ingame.fr.drawStringWithShadow(s, -fontrenderer.getStringWidth(s) / 2, byte0, color);
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		mc.entityRenderer.enableLightmap(0D);
	}

	public static void renderWaypoint2(double d, double d1, double d2, float f) {
		mc.entityRenderer.disableLightmap(f);
		double posX = (d) - mc.thePlayer.posX;
		double posY = (d1) - mc.thePlayer.posY;
		double posZ = (d2) - mc.thePlayer.posZ;

		GL11.glPushMatrix();
		GL11.glLineWidth(2F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glPushMatrix();
		GL11.glColor3d(1, 0, 1);//Solid
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(0D, 0D);
		GL11.glVertex3d(posX, posY - 1.7, posZ);
		GL11.glEnd();
		drawOutlinedBoundingBox(new AxisAlignedBB(posX - 0.5, posY - 1.7, posZ - 0.5, posX + 0.5, posY + 0.5, posZ + 0.5));
		GL11.glColor4d(1, 0, 1, 0.4);//Transparent
		drawBoundingBox(new AxisAlignedBB(posX - 0.5, posY - 1.7, posZ - 0.5, posX + 0.5,  posY + 0.5, posZ + 0.5));
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}

	public static void ESP(double d, double d1, double d2, float r, float g, float b) {
		double d3 = d-mc.thePlayer.posX;
		double d4 = d1-mc.thePlayer.posY;
		double d5 = d2- mc.thePlayer.posZ;
		GL11.glPushMatrix();
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		GL11.glDisable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(false);
		GL11.glColor4f(r,g, b, 0.15F);
		GL11.glLineWidth(6F);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848 /*GL_LINE_SMOOTH*/);

		GL11.glLineWidth(2F); 
		drawBoundingBox(new AxisAlignedBB(d3 , d4 , d5 , d3 + 1,  d4  + 1, d5 + 1));
		GL11.glColor4f(r,g, b, 0.6F);
		drawOutlinedBoundingBox(new AxisAlignedBB(d3 , d4, d5, d3 + 1,   d4  + 1 , d5 + 1));
		GL11.glColor4f(r,g, b, 0.4F);

		GL11.glBegin(GL11.GL_LINES); //starts lines
		GL11.glVertex3d(d3, d4+1, d5); //first point
		GL11.glVertex3d(d3, d4, d5+1); //second point
		GL11.glEnd(); //ends lines
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(d3+1, d4+1, d5);
		GL11.glVertex3d(d3+1, d4, d5+1);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(d3, d4+1, d5);
		GL11.glVertex3d(d3+1, d4+1, d5+1);
		GL11.glEnd();
		GL11.glDisable(2848 /*GL_LINE_SMOOTH*/);
		GL11.glDepthMask(true);
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glPopMatrix();
	}

	public Vec3D getCustomLook(int pitch, int yaw) {
		float f1 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
		float f3 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
		float f5 = -MathHelper.cos(-pitch * 0.01745329F);
		float f7 = MathHelper.sin(-pitch * 0.01745329F);
		return Vec3D.createVector(f3 * f5, f7, f1 * f5);
	}

	public MovingObjectPosition rayTraceCustom(double d, float f,int pitch, int yaw) {
		Vec3D vec3d = mc.thePlayer.getPosition(f);
		Vec3D vec3d1 = getCustomLook(pitch,yaw);
		Vec3D vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
		return mc.theWorld.rayTraceBlocks(vec3d, vec3d2);
	}

	public static void playerESP(double d, double d1, double d2, float F, double R, double B, double G){
		mc.entityRenderer.disableLightmap(F);
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

	public static void waypointESP(double d, double d1, double d2, float F, double R, double B, double G){
		mc.entityRenderer.disableLightmap(F);
		double posX = (d) - mc.thePlayer.posX;
		double posY = (d1) - mc.thePlayer.posY;
		double posZ = (d2) - mc.thePlayer.posZ;
		
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		GL11.glLineWidth(2.0F);
		GL11.glColor4d(R, B, G, 0.15F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);  
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(false);
		
		drawBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.1, d2 - 0.5, d + 0.5, d1 + 1.0, d2 + 0.5));
		GL11.glColor4d(R, B, G, 1.0F);
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 0.1, d2 - 0.5, d + 0.5, d1 + 1.0, d2 + 0.5));
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(0D, 0D);
		GL11.glVertex3d(posX, posY, posZ);
		GL11.glEnd();
		
		/*drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1 + 1.0, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));
		/*drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2, d + 0.5, d1 + 2.0, d2 + 0.5));
		drawOutlinedBoundingBox(new AxisAlignedBB(d - 0.5, d1, d2 - 0.5, d + 0.5, d1 + 2.0, d2 + 0.5));*/
		GL11.glEnable(GL11.GL_TEXTURE_2D); 
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(true);
		GL11.glDisable(3042 /*GL_BLEND*/);
	}

	public static void colorESPPlayer(double d, double d1, double d2, float f, double R, double B, double G, EntityPlayer entity) {
		mc.entityRenderer.disableLightmap(f);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_TEXTURE_2D); 
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(false);

		GL11.glPushMatrix();
		GL11.glTranslated(d+0.5,d1,d2+0.5);
		GL11.glRotatef(-entity.rotationYaw, 0,1,0);
		GL11.glTranslated(-d-0.5,-d1,-d2-0.5);

		GL11.glColor4d(R, B, G, 0.11F);
		drawBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 2.0, d2 + 1.0));
		GL11.glColor4d(R, B, G, 1F);
		chestLines(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 2.0, d2 + 1.0));
		GL11.glLineWidth(2.0F); //sets line width to two

		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D); 
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(true);
		GL11.glDisable(3042 /*GL_BLEND*/);
	}

	public static void colorESP(double d, double d1, double d2, float f, double R, double B, double G)  {
		mc.entityRenderer.disableLightmap(f);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_TEXTURE_2D); 
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(false);
		GL11.glColor4d(R, B, G, 0.15F);
		drawBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
		GL11.glColor4d(R, B, G, 1F);
		chestLines(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
		GL11.glLineWidth(2.0F); //sets line width to two
		GL11.glEnable(GL11.GL_TEXTURE_2D); 
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(true);
		GL11.glDisable(3042 /*GL_BLEND*/);
	}

	public static void chestESP(double d, double d1, double d2, float f) {
		mc.entityRenderer.disableLightmap(f);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_TEXTURE_2D); 
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(false);
		GL11.glColor4d(255, 120, 0, 0.15F);
		drawBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
		GL11.glColor4d(255, 120, 0, 1F);
		chestLines(new AxisAlignedBB(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
		GL11.glLineWidth(2.0F); //sets line width to two
		GL11.glEnable(GL11.GL_TEXTURE_2D); 
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(true);
		GL11.glDisable(3042 /*GL_BLEND*/);
	}

	public static void chestLines(AxisAlignedBB ax) {
		GL11.glLineWidth(1F);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.minZ);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glEnd();
	}

	public static void playerStuff(AxisAlignedBB ax) {
		GL11.glLineWidth(1F);
		GL11.glBegin(GL11.GL_QUAD_STRIP);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glEnd();
	}


	public static void blockLines(AxisAlignedBB ax) {
		GL11.glLineWidth(1F);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.minY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.minX, ax.maxY, ax.minZ);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.maxZ);
		GL11.glVertex3d(ax.maxX, ax.maxY, ax.minZ);
		GL11.glEnd();
	}

	public static void drawBoundingBox(AxisAlignedBB axisalignedbb) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		tessellator.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		tessellator.draw();
	}

	public static void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		tessellator.startDrawing(3);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		tessellator.draw();
		tessellator.startDrawing(3);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		tessellator.draw();
		tessellator.startDrawing(1);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		tessellator.draw();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

}
