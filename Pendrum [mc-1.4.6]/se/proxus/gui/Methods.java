package se.proxus.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import se.proxus.*;
import se.proxus.mods.*;
import se.proxus.panels.BasePanel;
import se.proxus.panels.PanelHandler;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class Methods extends Gui {

	public static Pendrum pm = new Pendrum();

	public static int scaledWidth;
	public static int scaledHeight;
	public static int scaleFactor;
	public static double scaledWidthD;
	public static double scaledHeightD;

	private static RenderItem itemRenderer = new RenderItem();	

	public void drawMain(ArrayList<String> var1, String[] var2, FontRenderer var3, String var4) {
		ScaledResolution var5 = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		
		var3.drawStringWithShadow("§fPendrum (v" + this.pm.sett.curVersion + ") ", 2, 2, 0xFFFFFFFF);
		
		for(int var6 = 0; var6 < this.pm.sett.guiSettingsArray.size(); var6++) {
			String var7 = this.pm.sett.guiSettingsArray.get(var6);
			
			var7 = this.pm.utils.getSyntaxedString(var7);
			
			var3.drawStringWithShadow(var7, 2, 12 + var6 * 10, 0xFFD3D3D3);
		}

		var3.drawStringWithShadow(var4, 2, var5.getScaledHeight() - 10, 0xFFFFFFFF);

		drawArray(var1, var3);

		for(BasePanel var7 : this.pm.panels.panelArray) {
			if(var7.getInfo().isPinned() && !(this.pm.wrapper.getMinecraft().currentScreen instanceof PanelHandler)) {
				var7.draw(0, 0, this.getPendrum().ttf);
			}
		}
	}	

	public static Pendrum getPendrum() {
		return pm;
	}

	public static void setPm(Pendrum pm) {
		Methods.pm = pm;
	}

	public void drawArray(ArrayList<String> var1, FontRenderer var2) {
		try {
			/** A local instance of the class ScaledResolution.java **/
			ScaledResolution var4 = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);

			if(!(var1.isEmpty())) {
				//this.drawSideGradientRect(var4.getScaledWidth() - 80, 0, var4.getScaledWidth(), var1.size() * 10 + 1, 0x90FFFFFF, 0x20FFFFFF);
			}

			for(int var3 = 0; var3 < var1.size(); var3++) {
				/** The local variables for the positions. **/
				int var5 = var4.getScaledWidth() - var2.getStringWidth((String)var1.get(var3)) + -1, var6 = var3 * 10 + 2;

				/** The local instance of the Base_Toggle class. **/
				BaseMod var7 = (BaseMod)Minecraft.getMinecraft().pm.mods.modArray.get(var3);

				/** Drawing the string of the hacks name. **/
				var2.drawStringWithShadow((String)var1.get(var3), var5, var6, 0xFFFFFFFF);
			}
		} catch (Exception e) {

		}
	}

	public void drawSideGradientRect(float x, float y, float width, float height, int hex1, int hex2) {
		float f = (float)(hex1 >> 24 & 0xff) / 255F;
		float f1 = (float)(hex1 >> 16 & 0xff) / 255F;
		float f2 = (float)(hex1 >> 8 & 0xff) / 255F;
		float f3 = (float)(hex1 & 0xff) / 255F;
		float f4 = (float)(hex2 >> 24 & 0xff) / 255F;
		float f5 = (float)(hex2 >> 16 & 0xff) / 255F;
		float f6 = (float)(hex2 >> 8 & 0xff) / 255F;
		float f7 = (float)(hex2 & 0xff) / 255F;
		GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
		GL11.glBlendFunc(770, 771);
		GL11.glShadeModel(7425 /*GL_SMOOTH*/);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(f1, f2, f3, f);
		tessellator.addVertex(width, y, zLevel);
		tessellator.setColorRGBA_F(f5, f6, f7, f4);
		tessellator.addVertex(x, y, zLevel);
		tessellator.addVertex(x, height, zLevel);
		tessellator.setColorRGBA_F(f1, f2, f3, f);
		tessellator.addVertex(width, height, zLevel);
		tessellator.draw();
		GL11.glShadeModel(7424 /*GL_FLAT*/);
		GL11.glDisable(3042 /*GL_BLEND*/);
		GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
		GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
	}

	public void drawCenteredStringWithoutShadow(FontRenderer par1FontRenderer, String par2Str, int par3, float f, int par5) {
		par1FontRenderer.drawString(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, f, par5);
	}

	public void setScale() {
		this.scaledWidth = this.pm.wrapper.getMinecraft().displayWidth;
		this.scaledHeight = this.pm.wrapper.getMinecraft().displayHeight;
		this.scaleFactor = 1;
		int k = this.pm.wrapper.getMinecraft().gameSettings.guiScale;

		if(k == 0) {
			k = 1000;
		}

		for(; this.scaleFactor < k && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240; this.scaleFactor++) {}

		this.scaledWidthD = (double)this.scaledWidth / (double)this.scaleFactor;
		this.scaledHeightD = (double)this.scaledHeight / (double)this.scaleFactor;
		this.scaledWidth = (int)Math.ceil(this.scaledWidthD);
		this.scaledHeight = (int)Math.ceil(this.scaledHeightD);
	}

	public void drawBaseBorderedGradientRect(float x, float y, float x2, float y2, int col1, int col2, int col3) {
		drawGradientRect(x + 1, y + 1, x2 - 1, y2 - 1, col2, col3);
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 -1, col1);
	}

	public void drawBaseHollowRect(float x, float y, float x2, float y2, int col1) {
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 -1, col1);
	}

	public void drawBorderedGradientRect(float x, float y, float x2, float y2, int col1, int col2, int col3) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawBaseBorderedGradientRect(x * 2, y * 2, x2 * 2, y2 * 2, col1, col2, col3);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public void drawHollowRect(float x, float y, float x2, float y2, int col1) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawBaseHollowRect(x * 2, y * 2, x2 * 2, y2 * 2, col1);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public void drawPlayerHead(int x, float y, String username) {
		Minecraft mc = this.pm.wrapper.getMinecraft();
		float zLevel = 0;
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.obtainImageData("http://s3.amazonaws.com/MinecraftSkins/" + StringUtils.stripControlCodes(username) + ".png", new ImageBufferDownload());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTextureForDownloadableImage("http://s3.amazonaws.com/MinecraftSkins/" + StringUtils.stripControlCodes(username) + ".png", "/mob/char.png"));
		float f1 = 3f;
		int f = 3;
		int x2 = x + 184;
		GL11.glScalef(f1, f1, f1);
		drawTexturedModalRectZ(x/f, y/f, 8, 8, 8, 8, zLevel);
		drawTexturedModalRectZ(x/f, y/f, 40, 8, 8, 8, zLevel);
		GL11.glPopMatrix();
	}

	public void drawTexturedModalRectZ(int i, float y, int k, int i1, int j1, int k1, float zLevel) {
		float f = 0.015625F;
		float f1 = 0.03125F;
		Tessellator tesselator = Tessellator.instance;
		tesselator.startDrawingQuads();
		tesselator.addVertexWithUV((double)(i + 0), (double)(y + k1), (double)zLevel, (double)((float)(k + 0) * f), (double)((float)(i1 + k1) * f1));
		tesselator.addVertexWithUV((double)(i + j1), (double)(y + k1), (double)zLevel, (double)((float)(k + j1) * f), (double)((float)(i1 + k1) * f1));
		tesselator.addVertexWithUV((double)(i + j1), (double)(y + 0), (double)zLevel, (double)((float)(k + j1) * f), (double)((float)(i1 + 0) * f1));
		tesselator.addVertexWithUV((double)(i + 0), (double)(y + 0), (double)zLevel, (double)((float)(k + 0) * f), (double)((float)(i1 + 0) * f1));
		tesselator.draw();
	}

	public static void startCut(int x, int y, int x2, int y2) {
		GL11.glScissor(x * scaleFactor, (scaledHeight - y2) * scaleFactor, (x2 - x) * scaleFactor, (y2 - y) * scaleFactor);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	public static void stopCut() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	public void drawItem(int x, int y, int id) {
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		itemRenderer.renderItemIntoGUI(this.pm.wrapper.getFontRenderer(), this.pm.wrapper.getRenderEngine(), new ItemStack(Block.chest), 999, 999);
		itemRenderer.drawItemIntoGui(this.pm.wrapper.getFontRenderer(), this.pm.wrapper.getRenderEngine(), id, 0, Item.itemsList[id].getIconFromDamage(0), x + 1, y + 1);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
	}
	
	public void drawItemTag(int x, int y, ItemStack item) {        
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		itemRenderer.renderItemIntoGUI(this.pm.wrapper.getFontRenderer(), this.pm.wrapper.getRenderEngine(), item, x, y);
		itemRenderer.renderItemOverlayIntoGUI(this.pm.wrapper.getFontRenderer(), this.pm.wrapper.getRenderEngine(), item, x, y);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.disableStandardItemLighting();
	}

	public void drawPanelText(String var0, float var1, float var2) {
		this.pm.ttf.drawString(var0, var1, var2, 0xFF606060);
		this.pm.ttf.drawString(var0, var1, var2 + 0.5F, 0xFFFFFFFF);
	}

	public void drawTinyString(String var0, int var1, int var2, int var3) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.pm.wrapper.getFontRenderer().drawStringWithShadow(var0, var1 * 2, var2 * 2, var3);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public void drawTexturedRect(int x, int y, int x1, int y1, String imgLocation, int startX, int startY, int endX, int endY) {
		GL11.glEnable(GL11.GL_BLEND); 
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.pm.wrapper.getRenderEngine().getTexture(imgLocation));
		GL11.glColor4f(1F, 1F, 1F, 1F);
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + y1, 0, (float)(startX + 0) * f, (float)(startY + endY) * f1);
		tessellator.addVertexWithUV(x + x1, y + y1, 0, (float)(startX + endX) * f, (float)(startY + endY) * f1);
		tessellator.addVertexWithUV(x + x1, y + 0, 0, (float)(startX + endX) * f, (float)(startY + 0) * f1);
		tessellator.addVertexWithUV(x + 0, y + 0, 0, (float)(startX + 0) * f, (float)(startY + 0) * f1);
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND); 
	}

	public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
		drawRect(x, y, x2, y2, col2);

		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public static void drawHollowBorderedRect(int x, int y, int x2, int y2, float l1, int col1) {
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	/**
	 * @author ekG4me
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 * @param maxSize
	 * @param padding
	 * @param colBack
	 * @param colMid
	 * @param colShade
	 */
	 private void drawWaffle(int x, int y, int x2, int y2, int maxSize, int padding, int colBack, int colMid, int colShade){int wCount = (int)((x2 - x)/maxSize);int hCount = (int)((y2 - y)/maxSize);int recWidth  = (int)((x2 - x)/wCount);int recHeight = (int)((y2 - y)/hCount);drawRect(x, y, x2, y2, colBack);for (int yy = 0; yy < hCount; yy++){for (int xx = 0; xx < wCount; xx++){int x0 = x + xx * recWidth + padding;int y0 = y + yy * recHeight + padding;int w0 = recWidth - padding * 2;int h0 = recHeight - padding * 2;drawRect(x0-1, y0-1, x0 + w0+1, y0 + h0+1, colShade);drawRect(x0, y0, x0 + w0, y0 + h0, colMid);}}}
}