package se.proxus.gui;

import java.util.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.src.*;

import se.proxus.*;
import se.proxus.hacks.*;
import se.proxus.panels.Base_Panel;
import se.proxus.panels.Base_Panels;

import static org.lwjgl.opengl.GL11.*;

public class Methods extends Main {

	public void drawMain(ArrayList<String> var1, String var2, FontRenderer var3, int var4, int var5) {
		var3.drawStringWithShadow(var2, 2, 2, 0xFFFFFFFF);

		drawArray(var1, var3);

		for(Base_Panel var6 : Base_Panels.panelArray) {
			if(var6.pinned && !(mc.currentScreen instanceof Base_Panels)) {
				var6.draw(var4, var5, true);
			}
		}
	}	

	public void drawArray(ArrayList<String> var1, FontRenderer var2) {
		try {
			for(int var3 = 0; var3 < var1.size(); var3++) {
				/** A local instance of the class ScaledResolution.java **/
				ScaledResolution var4 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);

				/** The local variables for the positions. **/
				int var5 = var4.getScaledWidth() - var2.getStringWidth((String)var1.get(var3)) - 2, var6 = var3 * 10 + 2;

				/** The local instance of the Base_Toggle class. **/
				Base_Hack var7 = (Base_Hack)this.hacks.hacks.hackArray.get(var3);

				/** Drawing the string of the hacks name. **/
				var2.drawStringWithShadow((String)var1.get(var3), var5, var6, 0xFFFFFFFF);
			}
		} catch (Exception e) {

		}
	}

	public static void drawSmallString(String var1, int var2, int var3, int var4, boolean var5) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);

		if(var5) {
			mc.fontRenderer.drawString(var1, var2 * 2, var3 * 2, var4);
		} else {
			mc.fontRenderer.drawStringWithShadow(var1, var2 * 2, var3 * 2, var4);
		}

		glPopMatrix();
	}

	public static void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, float f, int par5, boolean par6) {
		if(par6) {
			par1FontRenderer.drawStringWithShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, (int)f, par5);
		} else {
			par1FontRenderer.drawString(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, f, par5);
		}
	}

	public static void enableDefaults() {
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
	}

	public static void disableDefaults() {
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}

	public static void drawRect(float x, float y, float x2, float y2, int col1) {
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		enableDefaults();
		glColor4f(f1, f2, f3, f);
		glBegin(GL_QUADS);
		glVertex2d(x2, y);
		glVertex2d(x, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glEnd();
		disableDefaults();
	}

	public static void drawGradientRect(float par1, float par2, float par3, float par4, int par5, int par6) {
		float var7 = (float)(par5 >> 24 & 255) / 255.0F;
		float var8 = (float)(par5 >> 16 & 255) / 255.0F;
		float var9 = (float)(par5 >> 8 & 255) / 255.0F;
		float var10 = (float)(par5 & 255) / 255.0F;
		float var11 = (float)(par6 >> 24 & 255) / 255.0F;
		float var12 = (float)(par6 >> 16 & 255) / 255.0F;
		float var13 = (float)(par6 >> 8 & 255) / 255.0F;
		float var14 = (float)(par6 & 255) / 255.0F;
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_ALPHA_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glShadeModel(GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, var7);
		var15.addVertex((double)par3, (double)par2, (double)0);
		var15.addVertex((double)par1, (double)par2, (double)0);
		var15.setColorRGBA_F(var12, var13, var14, var11);
		var15.addVertex((double)par1, (double)par4, (double)0);
		var15.addVertex((double)par3, (double)par4, (double)0);
		var15.draw();
		glShadeModel(GL_FLAT);
		glDisable(GL_BLEND);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_TEXTURE_2D);
	}

	public static void drawHorizontalLine(float x, float y, float x2, int hex) {
		if(y < x) {
			float var5 = x;
			x = y;
			y = var5;
		}

		drawRect(x, x2, y + 1, x2 + 1, hex);
	}

	public static void drawVerticalLine(float x, float y, float y2, int hex) {
		if(y2 < y) {
			float var5 = y;
			y = y2;
			y2 = var5;
		}

		drawRect(x, y + 1, x + 1, y2, hex);
	}

	public static void drawBaseBorderedRect(float x, float y, float x2, float y2, int col1, int col2) {
		drawRect(x + 1, y + 1, x2 - 1, y2 - 1, col2);
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 -1, col1);
	}

	public static void drawBorderedRect(float x, float y, float x2, float y2, int col1, int col2) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawBaseBorderedRect(x * 2, y * 2, x2 * 2, y2 * 2, col1, col2);
		glPopMatrix();
	}
}