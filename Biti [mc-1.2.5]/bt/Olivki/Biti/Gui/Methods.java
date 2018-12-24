package bt.Olivki.Biti.Gui;

import net.minecraft.src.*;

import org.lwjgl.opengl.GL11;

import bt.Olivki.Biti.Biti;

public class Methods //You will get some errors, but Eclipse will help you with them, so don't worry.
{
	protected static float zLevel;

	public Methods()
	{
		zLevel = 0.0F;
	}

	protected static void drawHorizontalLine(int par1, int par2, int par3, int par4)
	{
		if (par2 < par1)
		{
			int i = par1;
			par1 = par2;
			par2 = i;
		}

		drawRect(par1, par3, par2 + 1, par3 + 1, par4);
	}

	protected static void drawVerticalLine(int par1, int par2, int par3, int par4)
	{
		if (par3 < par2)
		{
			int i = par2;
			par2 = par3;
			par3 = i;
		}

		drawRect(par1, par2 + 1, par1 + 1, par3, par4);
	}

	/**
	 * Draws a solid color rectangle with the specified coordinates and color.
	 */
	 public static void drawRect(int par1, int par2, int par3, int par4, int par5)
	 {
		 if (par1 < par3)
		 {
			 int i = par1;
			 par1 = par3;
			 par3 = i;
		 }

		 if (par2 < par4)
		 {
			 int j = par2;
			 par2 = par4;
			 par4 = j;
		 }

		 float f = (float)(par5 >> 24 & 0xff) / 255F;
		 float f1 = (float)(par5 >> 16 & 0xff) / 255F;
		 float f2 = (float)(par5 >> 8 & 0xff) / 255F;
		 float f3 = (float)(par5 & 0xff) / 255F;
		 Tessellator tessellator = Tessellator.instance;
		 GL11.glEnable(GL11.GL_BLEND);
		 GL11.glDisable(GL11.GL_TEXTURE_2D);
		 GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		 GL11.glColor4f(f1, f2, f3, f);
		 tessellator.startDrawingQuads();
		 tessellator.addVertex(par1, par4, 0.0D);
		 tessellator.addVertex(par3, par4, 0.0D);
		 tessellator.addVertex(par3, par2, 0.0D);
		 tessellator.addVertex(par1, par2, 0.0D);
		 tessellator.draw();
		 GL11.glEnable(GL11.GL_TEXTURE_2D);
		 GL11.glDisable(GL11.GL_BLEND);
	 }

	 /**
	  * Draws a rectangle with a vertical gradient between the specified colors.
	  */
	 protected static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
	 {
		 float f = (float)(par5 >> 24 & 0xff) / 255F;
		 float f1 = (float)(par5 >> 16 & 0xff) / 255F;
		 float f2 = (float)(par5 >> 8 & 0xff) / 255F;
		 float f3 = (float)(par5 & 0xff) / 255F;
		 float f4 = (float)(par6 >> 24 & 0xff) / 255F;
		 float f5 = (float)(par6 >> 16 & 0xff) / 255F;
		 float f6 = (float)(par6 >> 8 & 0xff) / 255F;
		 float f7 = (float)(par6 & 0xff) / 255F;
		 GL11.glDisable(GL11.GL_TEXTURE_2D);
		 GL11.glEnable(GL11.GL_BLEND);
		 GL11.glDisable(GL11.GL_ALPHA_TEST);
		 GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		 GL11.glShadeModel(GL11.GL_SMOOTH);
		 Tessellator tessellator = Tessellator.instance;
		 tessellator.startDrawingQuads();
		 tessellator.setColorRGBA_F(f1, f2, f3, f);
		 tessellator.addVertex(par3, par2, zLevel);
		 tessellator.addVertex(par1, par2, zLevel);
		 tessellator.setColorRGBA_F(f5, f6, f7, f4);
		 tessellator.addVertex(par1, par4, zLevel);
		 tessellator.addVertex(par3, par4, zLevel);
		 tessellator.draw();
		 GL11.glShadeModel(GL11.GL_FLAT);
		 GL11.glDisable(GL11.GL_BLEND);
		 GL11.glEnable(GL11.GL_ALPHA_TEST);
		 GL11.glEnable(GL11.GL_TEXTURE_2D);
	 }

	 /**
	  * Renders the specified text to the screen, center-aligned.
	  */
	 public void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
	 {
		 par1FontRenderer.drawStringWithShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
	 }

	 /**
	  * Renders the specified text to the screen.
	  */
	 public void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
	 {
		 par1FontRenderer.drawStringWithShadow(par2Str, par3, par4, par5);
	 }

	 /**
	  * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
	  */
	 public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
	 {
		 float f = 0.00390625F;
		 float f1 = 0.00390625F;
		 Tessellator tessellator = Tessellator.instance;
		 tessellator.startDrawingQuads();
		 tessellator.addVertexWithUV(par1 + 0, par2 + par6, zLevel, (float)(par3 + 0) * f, (float)(par4 + par6) * f1);
		 tessellator.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (float)(par3 + par5) * f, (float)(par4 + par6) * f1);
		 tessellator.addVertexWithUV(par1 + par5, par2 + 0, zLevel, (float)(par3 + par5) * f, (float)(par4 + 0) * f1);
		 tessellator.addVertexWithUV(par1 + 0, par2 + 0, zLevel, (float)(par3 + 0) * f, (float)(par4 + 0) * f1);
		 tessellator.draw();
	 }

	 public static void drawBorderedRect(int x, int y, int x1, int y1, int CO1, int CO2) {
		 drawRect(x, y, x1, y1, CO2);
		 drawVerticalLine(x, y, y1 -1, CO1);
		 drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		 drawHorizontalLine(x, x1 - 1, y, CO1);
		 drawHorizontalLine(x, x1 - 1, y1 -1, CO1);
	 }

	 public static void drawBorderedGradientRect(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
		 drawGradientRect(x, y, x1, y1, CO2, CO3);
		 drawVerticalLine(x, y, y1 -1, CO1);
		 drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		 drawHorizontalLine(x, x1 - 1, y, CO1);
		 drawHorizontalLine(x, x1 - 1, y1 -1, CO1);
	 }


	 public static void drawWBorderedRect(int x, int y, int x1, int y1, int CO1, int CO2) {
		 drawRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2);
		 drawVerticalLine(x, y, y1 -1, CO1);
		 drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		 drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		 drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);
	 }


	 public static void drawWBorderedGradientRect(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
		 drawGradientRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2, CO3);
		 drawVerticalLine(x, y, y1 -1, CO1);
		 drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		 drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		 drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);
	 }

	 public static void drawWBorderedGradientRectWithString(String s, int x, int y, int x1, int y1, int CO1, int CO2, int CO3, int CO4) {
		 drawGradientRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2, CO3);
		 drawVerticalLine(x, y, y1 -1, CO1);
		 drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		 drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		 drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);

		 Biti.mc.fontRenderer.drawString(s, x + 3, y + 3, CO4);
	 }

	 public static void drawWBorderedGradientRectWithOutlinedString(String s, int x, int y, int x1, int y1, int CO1, int CO2, int CO3, int CO4) {
		 drawGradientRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2, CO3);
		 drawVerticalLine(x, y, y1 -1, CO1);
		 drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		 drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		 drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);

		 drawStringWithOutline(s, x + 3, y + 3, CO4);
	 }

	 public static void drawStringWithOutline(String s, int I1, int I2, int I3){
		 Biti.mc.fontRenderer.renderString(s, I1 + 1, I2, I3, true);
		 Biti.mc.fontRenderer.renderString(s, I1, I2 + 1, I3, true);
		 Biti.mc.fontRenderer.renderString(s, I1 - 1, I2, I3, true);
		 Biti.mc.fontRenderer.renderString(s, I1, I2 - 1, I3, true);
		 Biti.mc.fontRenderer.drawString(s, I1, I2, I3);
	 } 
}

