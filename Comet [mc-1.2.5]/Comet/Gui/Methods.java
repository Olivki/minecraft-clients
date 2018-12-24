package Comet.Gui;

import java.nio.IntBuffer;

import net.minecraft.src.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import Comet.Comet;


public class Methods
{
	public static float zLevel;

	public Methods()
	{
		zLevel = 0.0F;
	}

	public static void drawHorizontalLine(int par1, int par2, int par3, int par4)
	{
		if (par2 < par1)
		{
			int i = par1;
			par1 = par2;
			par2 = i;
		}

		drawRect(par1, par3, par2 + 1, par3 + 1, par4);
	}

	public static void drawVerticalLine(int par1, int par2, int par3, int par4)
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
	public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6) {
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
	public static void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
	{
		par1FontRenderer.drawStringWithShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
	}
	
	public static void drawSmallCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
	{
		drawTinyString(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
	}
	
	public static void drawCenteredStringS(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
	{
		par1FontRenderer.drawString(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
	}
	
	public static void drawLargeCenteredStringS(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
	{
		drawLargeStringWithoutShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
	}

	public static void drawCenteredStringWithOutline(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
	{
		drawStringWithOutline(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
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
	public void drawTexturedModalRect(double d, double e, int par3, int par4, int par5, int par6)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(d + 0, e + par6, zLevel, (float)(par3 + 0) * f, (float)(par4 + par6) * f1);
		tessellator.addVertexWithUV(d + par5, e + par6, zLevel, (float)(par3 + par5) * f, (float)(par4 + par6) * f1);
		tessellator.addVertexWithUV(d + par5, e + 0, zLevel, (float)(par3 + par5) * f, (float)(par4 + 0) * f1);
		tessellator.addVertexWithUV(d + 0, e + 0, zLevel, (float)(par3 + 0) * f, (float)(par4 + 0) * f1);
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
	
	public static void drawWDaFuqBorderedRect(int x, int y, int x1, int y1, int CO1, int CO2) {
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
	}
	
	public static void drawWBorderedBarRect(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2);
		drawRect(x + 1, y + 1, x1 - 1, y1 - 6, CO3);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);
	}
	
	public static void drawWHollowBorderedRect(int x, int y, int x1, int y1, int CO1) {
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);
	}
	
	public static void drawBarMethod(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
		drawSmallWBorderedBarRect(x, y, x1, y1, CO1, CO2, CO3);
	}
	
	public static void drawSmallWBorderedBarRect(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawWBorderedBarRect(x * 2, y * 2, x1 * 2, y1 * 2, CO1, CO2, CO3);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallHorizontalLine(int x, int y, int x1, int CO1) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawHorizontalLine(x * 2, y * 2, x1 * 2, CO1);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallVerticalLine(int x, int y, int y1, int CO1) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawHorizontalLine(y * 2, y1 * 2, x * 2, CO1);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallWBorderedRect(int x, int y, int x1, int y1, int CO1, int CO2) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawWBorderedRect(x * 2, y * 2, x1 * 2, y1 * 2, CO1, CO2);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallWDaFuqBorderedRect(int x, int y, int x1, int y1, int CO1, int CO2) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawWDaFuqBorderedRect(x * 2, y * 2, x1 * 2, y1 * 2, CO1, CO2);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallBorderedRect(int x, int y, int x1, int y1, int CO1, int CO2) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawBorderedRect(x * 2, y * 2, x1 * 2, y1 * 2, CO1, CO2);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallRect(int x, int y, int x1, int y1, int CO1) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawRect(x * 2, y * 2, x1 * 2, y1 * 2, CO1);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallWHollowBorderedRect(int x, int y, int x1, int y1, int CO1) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawWHollowBorderedRect(x * 2, y * 2, x1 * 2, y1 * 2, CO1);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallWBorderedGradientRect(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawWBorderedGradientRect(x * 2, y * 2, x1 * 2, y1 * 2, CO1, CO2, CO3);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallBorderedGradientRect(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawBorderedGradientRect(x * 2, y * 2, x1 * 2, y1 * 2, CO1, CO2, CO3);
        GL11.glPopMatrix();
	}
	
	public static void drawSmallWBorderedSideGradientRect(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        drawWBorderedSideGradientRect(x * 2, y * 2, x1 * 2, y1 * 2, CO1, CO2, CO3);
        GL11.glPopMatrix();
	}

	public static void drawWBorderedGradientRect(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
		drawGradientRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2, CO3);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);
	}
	
	public static void drawWBorderedSideGradientRect(int x, int y, int x1, int y1, int CO1, int CO2, int CO3) {
		drawSideGradientRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2, CO3);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);
	}

	public static void drawWBorderedRectWithString(String s, int x, int y, int x1, int y1, int CO1, int CO2, int FO1) {      
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);

		int S1 = ((x1 - x) / 2);
		int S2 = ((x1 - S1) - Comet.ingame.fr.getStringWidth(s) / 2);
		int S3 = ((y1 - y) / 2);
		int S4 = (y1 - S3);

		Comet.ingame.fr.drawString(s, S2, S4 - 4, FO1);
	}

	public static void drawWBorderedRectWithTinyString(String s, int x, int y, int x1, int y1, int CO1, int CO2, int FO1) {      
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);

		int S1 = ((x1 - x) / 2);
		int S2 = ((x1 - S1) - Comet.ingame.fr.getStringWidth(s) / 2);
		int S3 = ((y1 - y) / 2);
		int S4 = (y1 - S3);

		drawTinyString(s, S2, S4 - 4, FO1);
	}

	public static void drawWBorderedRectWithStringWithOutline(String s, int x, int y, int x1, int y1, int CO1, int CO2, int FO1) {      
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);

		int S1 = ((x1 - x) / 2);
		int S2 = ((x1 - S1) - Comet.ingame.fr.getStringWidth(s) / 2);
		int S3 = ((y1 - y) / 2);
		int S4 = (y1 - S3);

		drawStringWithOutline(s, S2, S4 - 4, FO1);
	}

	public static void drawWBorderedRectWithTinyStringWithOutline(String s, int x, int y, int x1, int y1, int CO1, int CO2, int FO1) {      
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);

		int S1 = ((x1 - x) / 2);
		int S2 = ((x1 - S1) - Comet.ingame.fr.getStringWidth(s) / 2);
		int S3 = ((y1 - y) / 2);
		int S4 = (y1 - S3);

		drawTinyStringWithOutline(s, S2, S4 - 4, FO1);
	}

	public static void drawBorderedRectWithTinyStringWithOutline(String s, int x, int y, int x1, int y1, int CO1, int CO2, int FO1) {      
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);

		int S1 = ((x1 - x) / 2);
		int S2 = ((x1 - S1) - Comet.ingame.fr.getStringWidth(s) / 2);
		int S3 = ((y1 - y) / 2);
		int S4 = (y1 - S3);

		drawTinyStringWithOutline(s, S2, S4 - 4, FO1);
	}

	public static void drawWBorderedGradientRectWithString(String s, int x, int y, int x1, int y1, int CO1, int CO2, int CO3, int CO4) {
		drawGradientRect(x + 1, y + 1, x1 - 1, y1 - 1, CO2, CO3);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y, CO1);
		drawHorizontalLine(x + 1, x1 - 2, y1 -1, CO1);

		Comet.mc.fontRenderer.drawString(s, x + 3, y + 3, CO4);
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
		Comet.mc.fontRenderer.renderString(s, I1 + 1, I2, I3, true);
		Comet.mc.fontRenderer.renderString(s, I1, I2 + 1, I3, true);
		Comet.mc.fontRenderer.renderString(s, I1 - 1, I2, I3, true);
		Comet.mc.fontRenderer.renderString(s, I1, I2 - 1, I3, true);
		Comet.mc.fontRenderer.drawString(s, I1, I2, I3);
	} 

	public void draw4Gradient(int x, int y, int x1, int y1, int c1, int c2, int c3, int c4) { 
		int l = y1 - y;
		int l1 = l / 4;
		int l11 = l1 * 2;
		int l111 = l1*3;

		drawGradientRect(x, l1, x1, l11, c1, c2);
		drawGradientRect(x, l11, x1, l111, c2, c3);
		drawGradientRect(x, l111, x1, y1, c3, c4);
	}

	public static void drawBRect(int x, int y, int x2, int y2, int color1, int color2){
		drawRect(x + 1, y, x2 - 1, y + 1, color1);
		drawRect(x, y + 1, x + 1, y2 - 1, color1);
		drawRect(x + 1, y2, x2 - 1, y2 - 1, color1);
		drawRect(x2, y + 1, x2 - 1, y2 - 1, color1);
		drawRect(x + 1, y + 1, x + 2, y + 2, color1);
		drawRect(x2 - 1, y + 1, x2 - 2, y + 2, color1);
		drawRect(x + 1, y2 - 1, x + 2, y2 - 2, color1);
		drawRect(x2 - 1, y2 - 1, x2 - 2, y2 - 2, color1);
		drawRect(x + 2, y + 1, x2 - 2, y + 2, color1);
		drawRect(x + 1, y + 2, x + 2, y2 - 2, color1);
		drawRect(x + 2, y2 - 1, x2 - 2, y2 - 2, color1);
		drawRect(x2 - 1, y + 2, x2 - 2, y2 - 2, color1);
		drawRect(x + 2, y + 2, x2 - 2, y2 - 2, color2);
	}

	public static void drawTinyString(String s, int x, int y, int co) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		Comet.ingame.fr.drawStringWithShadow(s, x * 2, y * 2, co);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public static void drawLargeString(String s, int x, int y, int co) {
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		Comet.ingame.fr.drawStringWithShadow(s, x * 2, y * 2, co);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
	}
	
	public static void drawLargeStringWithoutShadow(String s, int x, int y, int co) {
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		Comet.ingame.fr.drawString(s, x * 2, y * 2, co);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
	}

	public static void drawTinyStringWithOutline(String s, int x, int y, int co) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawStringWithOutline(s, x * 2, y * 2, co);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public static void drawLargeStringWithOutline(String s, int x, int y, int co) {
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		drawStringWithOutline(s, x * 2, y * 2, co);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
	}

	public static void drawCenteredTinyString(String s, int x, int y, int co) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawCenteredString(Comet.ingame.fr, s, x * 2, y * 2, co);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public static void drawCenteredLargeString(String s, int x, int y, int co) {
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		drawCenteredString(Comet.ingame.fr, s, x * 2, y * 2, co);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
	}

	public static void drawCenteredTinyStringWithOutline(String s, int x, int y, int co) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawCenteredStringWithOutline(Comet.ingame.fr, s, x * 2, y * 2, co);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public static void drawCenteredLargeStringWithOutline(String s, int x, int y, int co) {
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		drawCenteredStringWithOutline(Comet.ingame.fr, s, x * 2, y * 2, co);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
	}

	public static void dr2(double d, double d1, double d2, double d3, int i) {
		if (d < d2)
		{
			double d4 = d;
			d = d2;
			d2 = d4;
		}

		if (d1 < d3)
		{
			double d5 = d1;
			d1 = d3;
			d3 = d5;
		}

		float f = (float)(i >> 24 & 0xff) / 255F;
		float f1 = (float)(i >> 16 & 0xff) / 255F;
		float f2 = (float)(i >> 8 & 0xff) / 255F;
		float f3 = (float)(i & 0xff) / 255F;
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(f1, f2, f3, f);
		tessellator.startDrawingQuads();
		tessellator.addVertex(d, d3, 0.0D);
		tessellator.addVertex(d2, d3, 0.0D);
		tessellator.addVertex(d2, d1, 0.0D);
		tessellator.addVertex(d, d1, 0.0D);
		int j = 0;
		j = MathHelper.floor_double(360);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void dr(double d, double d1, double d2, double d3, int i) {
		if (d < d2)
		{
			double d4 = d;
			d = d2;
			d2 = d4;
		}

		if (d1 < d3)
		{
			double d5 = d1;
			d1 = d3;
			d3 = d5;
		}

		float f = (float)(i >> 24 & 0xff) / 255F;
		float f1 = (float)(i >> 16 & 0xff) / 255F;
		float f2 = (float)(i >> 8 & 0xff) / 255F;
		float f3 = (float)(i & 0xff) / 255F;
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(f1, f2, f3, f);
		tessellator.startDrawingQuads();
		tessellator.addVertex(d, d3, 0.0D);
		tessellator.addVertex(d2, d3, 0.0D);
		tessellator.addVertex(d2, d1, 0.0D);
		tessellator.addVertex(d, d1, 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void DrawCircle(float f, float f1, float f2, float f3, int i, int j) {
		float f4 = (float)(j >> 24 & 0xff) / 255F;
		float f5 = (float)(j >> 16 & 0xff) / 255F;
		float f6 = (float)(j >> 8 & 0xff) / 255F;
		float f7 = (float)(j & 0xff) / 255F;
		float f8 = (float)((Math.PI * 2D) / (double)i);
		float f9 = (float)Math.cos(f8);
		float f10 = (float)Math.sin(f8);
		GL11.glColor4f(f5, f6, f7, f4);
		float f12 = f3;
		float f13 = 0.0F;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glLineWidth(f);
		GL11.glBegin(GL11.GL_LINE_LOOP);

		for (int k = 0; k < i; k++)
		{
			GL11.glVertex2f(f12 + f1, f13 + f2);
			float f11 = f12;
			f12 = f9 * f12 - f10 * f13;
			f13 = f10 * f11 + f9 * f13;
		}

		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public static void DrawFullCircle(double d, double d1, double d2, int i) {
		float f = (float)(i >> 24 & 0xff) / 255F;
		float f1 = (float)(i >> 16 & 0xff) / 255F;
		float f2 = (float)(i >> 8 & 0xff) / 255F;
		float f3 = (float)(i & 0xff) / 255F;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);

		for (int j = 0; j <= 360; j++)
		{
			double d3 = Math.sin(((double)j * Math.PI) / 180D) * d2;
			double d4 = Math.cos(((double)j * Math.PI) / 180D) * d2;
			GL11.glVertex2d(d + d3, d1 + d4);
		}

		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public static void drawTri(int i, int j, int k) {
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		float f = (float)(k >> 24 & 0xff) / 255F;
		float f1 = (float)(k >> 16 & 0xff) / 255F;
		float f2 = (float)(k >> 8 & 0xff) / 255F;
		float f3 = (float)(k & 0xff) / 255F;
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		GL11.glVertex2d(i, j + 2);
		GL11.glVertex2d(i + 2, j - 2);
		GL11.glVertex2d(i - 2, j - 2);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glRotatef(-180F, 0.0F, 0.0F, 1.0F);
	}

	public static void drawTriangle(Entity entity, double d, double d1, int i, String s) {
		GL11.glPushMatrix();
		GL11.glTranslated(d, d1, 0.0D);
		GL11.glRotatef(-entity.rotationYaw, 0.0F, 0.0F, 1.0F);
		float f = (float)(i >> 24 & 0xff) / 255F;
		float f1 = (float)(i >> 16 & 0xff) / 255F;
		float f2 = (float)(i >> 8 & 0xff) / 255F;
		float f3 = (float)(i & 0xff) / 255F;

		GL11.glColor4f(f1, f2, f3, f);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2d(0.0D, 6D);
		GL11.glVertex2d(3D, -2D);
		GL11.glVertex2d(-3D, -2D);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glRotatef(entity.rotationYaw, 0.0F, 0.0F, 1.0F);
		GL11.glPopMatrix();
	}
	
	public static void renderCompass() {
		ScaledResolution scaledresolution = new ScaledResolution(Comet.mc.gameSettings, Comet.mc.displayWidth, Comet.mc.displayHeight);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		FontRenderer fontrenderer = Comet.mc.fontRenderer;
		drawSmallWBorderedRect(i / 2 - 94, 2, i / 2 + 94, 13, 0xFF000000, 0x500E0E0E);
		int k = 0;

		if (Comet.mc.thePlayer.rotationYaw < 0.0F) {k = -MathHelper.floor_double(Comet.mc.thePlayer.rotationYaw % 360F);
		} else {k = MathHelper.floor_double(Comet.mc.thePlayer.rotationYaw % 360F);}

		boolean flag = k > 0 && k < 180;
		boolean flag1 = k <= 270 && k >= 90;
		boolean flag2 = k <= 180 && k >= 0;
		boolean flag3 = Comet.mc.thePlayer.rotationYaw < 0.0F;

		if (k == 0) {
			drawCenteredString(fontrenderer, "S", i / 2, 4, 0xFFFFFFFF);
			drawCenteredString(fontrenderer, "E", i / 2 - 90, 4, 0xFFFFFFFF);
			drawCenteredString(fontrenderer, "W", i / 2 + 90, 4, 0xFFFFFFFF);
		} else if (!flag3) {
			drawCenteredString(fontrenderer, flag1 ? "N" : "", (i / 2 - k) + 180, 4, 0xFFFFFFFF);

			if (!flag) {k -= 360;}

			drawCenteredString(fontrenderer, flag1 ? "" : "S", i / 2 - k, 4, 0xFFFFFFFF);
			drawCenteredString(fontrenderer, flag2 ? "" : "E", i / 2 - k - 90, 4, 0xFFFFFFFF);
			drawCenteredString(fontrenderer, flag2 ? "W" : "", (i / 2 - k) + 90, 4, 0xFFFFFFFF);
		} else if (flag3){
			drawCenteredString(fontrenderer, flag1 ? "N" : "", (i / 2 + k) - 180, 4, 0xFFFFFFFF);

			if (!flag) {k -= 360;}

			drawCenteredString(fontrenderer, flag1 ? "" : "S", i / 2 + k, 4, 0xFFFFFFFF);
			drawCenteredString(fontrenderer, flag2 ? "" : "W", i / 2 + k + 90, 4, 0xFFFFFFFF);
			drawCenteredString(fontrenderer, flag2 ? "E" : "", (i / 2 + k) - 90, 4, 0xFFFFFFFF);
		}
	}
	
	public static void drawSideGradientRect(int i, int j, int k, int l, int i1, int j1) {
        float f = (float)(i1 >> 24 & 0xff) / 255F;
        float f1 = (float)(i1 >> 16 & 0xff) / 255F;
        float f2 = (float)(i1 >> 8 & 0xff) / 255F;
        float f3 = (float)(i1 & 0xff) / 255F;
        float f4 = (float)(j1 >> 24 & 0xff) / 255F;
        float f5 = (float)(j1 >> 16 & 0xff) / 255F;
        float f6 = (float)(j1 >> 8 & 0xff) / 255F;
        float f7 = (float)(j1 & 0xff) / 255F;
        GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425 /*GL_SMOOTH*/);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex(k, j, zLevel);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex(i, j, zLevel);
        tessellator.addVertex(i, l, zLevel);
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex(k, l, zLevel);
        tessellator.draw();
        GL11.glShadeModel(7424 /*GL_FLAT*/);
        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
        GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
    }
}

