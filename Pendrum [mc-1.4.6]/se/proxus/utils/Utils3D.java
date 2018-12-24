package se.proxus.utils;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

import se.proxus.*;

public class Utils3D extends Pendrum {

	public static void renderOutlinedBox(AxisAlignedBB var0) {
		Tessellator var1 = Tessellator.instance;

		var1.startDrawing(3);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.draw();

		var1.startDrawing(3);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.draw();

		var1.startDrawing(1);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.draw();
	}

	public static void renderCrossedBox(AxisAlignedBB var0) {
		Tessellator var1 = Tessellator.instance;

		var1.startDrawing(3);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.draw();

		var1.startDrawing(3);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.draw();

		var1.startDrawing(1);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.draw();
	}

	public static void renderBox(AxisAlignedBB var0) {
		Tessellator var1 = Tessellator.instance;

		var1.startDrawingQuads();
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.draw();

		var1.startDrawingQuads();
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.draw();

		var1.startDrawingQuads();
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.draw();

		var1.startDrawingQuads();
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.draw();

		var1.startDrawingQuads();
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.draw();

		var1.startDrawingQuads();
		var1.addVertex(var0.minX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.minX, var0.minY, var0.maxZ);
		var1.addVertex(var0.minX, var0.maxY, var0.minZ);
		var1.addVertex(var0.minX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.minZ);
		var1.addVertex(var0.maxX, var0.minY, var0.minZ);
		var1.addVertex(var0.maxX, var0.maxY, var0.maxZ);
		var1.addVertex(var0.maxX, var0.minY, var0.maxZ);
		var1.draw();
	}

	public void drawTracerTo(double x, double y, double z, double r, double g, double b, float lWidth, EntityPlayer e) {
		double d3 = x - this.utils3d.getFixedPos(e)[0];
		double d4 = y - this.utils3d.getFixedPos(e)[1];
		double d5 = z - this.utils3d.getFixedPos(e)[2];

		this.enableDefaults();
		GL11.glColor4d(r, g, b, 1.0D);
		GL11.glLineWidth(lWidth);

		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(0.0D, 0.0D);
		GL11.glVertex3d(d3, d4, d5);
		GL11.glEnd();
		this.disableDefaults();
	}

	public void renderLabel(double var0, double var2, double var4, String var6, int var7) {
		FontRenderer var8 = this.wrapper.getFontRenderer();

		float var9 = 0.016666668F * 4;

		GL11.glPushMatrix();
		GL11.glTranslatef((float)var0 + 0.0F, (float)var2 + 2.3F, (float)var4);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-var9, -var9, var9);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator var10 = Tessellator.instance;
		byte var11 = 0;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		var10.startDrawingQuads();
		int var12 = var8.getStringWidth(var6) / 2;
		var10.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		var10.addVertex((double)(-var12 - 1), (double)(-2 + var11), 0.0D);
		var10.addVertex((double)(-var12 - 1), (double)(8 + var11), 0.0D);
		var10.addVertex((double)(var12 + 2), (double)(8 + var11), 0.0D);
		var10.addVertex((double)(var12 + 2), (double)(-2 + var11), 0.0D);
		var10.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		var8.drawStringWithShadow(var6, -var8.getStringWidth(var6) / 2, var11 - 1, 0xFFFFFFFF);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		var8.drawStringWithShadow(var6, -var8.getStringWidth(var6) / 2, var11 - 1, 0xFFFFFFFF);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	public void enableDefaults() {
		this.wrapper.getMinecraft().entityRenderer.disableLightmap(1.0D);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		GL11.glDisable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848 /*GL_LINE_SMOOTH*/);
		GL11.glPushMatrix();
	}

	public void disableDefaults() {
		GL11.glPopMatrix();
		GL11.glDisable(2848 /*GL_LINE_SMOOTH*/);
		GL11.glDepthMask(true);
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(3042 /*GL_BLEND*/);
		this.wrapper.getMinecraft().entityRenderer.enableLightmap(1.0D);
	}

	public double[] getFixedPos(EntityPlayer var0) {
		return (new double[] {
				this.wrapper.getPlayer().lastTickPosX + (this.wrapper.getPlayer().posX - this.wrapper.getPlayer().lastTickPosX) * (double)this.wrapper.getMinecraft().getTimer().renderPartialTicks,
				this.wrapper.getPlayer().lastTickPosY + (this.wrapper.getPlayer().posY - this.wrapper.getPlayer().lastTickPosY) * (double)this.wrapper.getMinecraft().getTimer().renderPartialTicks, 
				this.wrapper.getPlayer().lastTickPosZ + (this.wrapper.getPlayer().posZ - this.wrapper.getPlayer().lastTickPosZ) * (double)this.wrapper.getMinecraft().getTimer().renderPartialTicks
		});
	}

	public void glColor4Hex(int var0) {
		GL11.glColor4f(this.utils.getRGBA(var0)[0], this.utils.getRGBA(var0)[1], this.utils.getRGBA(var0)[2], this.utils.getRGBA(var0)[3]);
	}
}