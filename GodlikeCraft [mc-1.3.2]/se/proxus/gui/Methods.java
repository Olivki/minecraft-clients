package se.proxus.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.*;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MapData;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;

import se.proxus.*;
import se.proxus.panels.*;

public class Methods extends GodlikeCraft {

	private static RenderItem itemRenderer = new RenderItem();

	/** Main methods. **/
	/** The drawing of everything. **/
	public static void drawMain(String var1, ArrayList<String> var2, float var5, int var6, int var7) {
		/** A local instance of the class FontRenderer.java **/
		FontRenderer var3 = mc.fontRenderer;

		/** Drawing the name of the client. **/
		//var3.drawStringWithShadow(var1, 2, 2, 0xFFFFFFFF);
		Base_Panels.ttf.drawStringS(var1, 1, 0);

		/** Drawing the users current kill count. **/
		//var3.drawStringWithShadow("Kills : " + vars.killCount, 2, 12, 0xFFFFFFFF);

		/** Drawing of the players current position. **/
		/*var3.drawStringWithShadow("X:" + (int)mc.thePlayer.posX, 2, 12, 0xFFFFFFFF);
		var3.drawStringWithShadow("Y:" + (int)mc.thePlayer.posY, 2, 22, 0xFFFFFFFF);
		var3.drawStringWithShadow("Z:" + (int)mc.thePlayer.posZ, 2, 32, 0xFFFFFFFF);
		vars.slick.drawStringWithShadow(vars.font, 1, 10, "X:" + (int)mc.thePlayer.posX);
		vars.slick.drawStringWithShadow(vars.font, 1, 20, "Y:" + (int)mc.thePlayer.posY);
		vars.slick.drawStringWithShadow(vars.font, 1, 30, "Z:" + (int)mc.thePlayer.posZ);*/
		Base_Panels.ttf.drawStringS("§fX:" + (int)mc.thePlayer.posX, 1, 10);
		Base_Panels.ttf.drawStringS("§fY:" + (int)mc.thePlayer.posY, 1, 20);
		Base_Panels.ttf.drawStringS("§fZ:" + (int)mc.thePlayer.posZ, 1, 30);
		
		/** Drawing the ArrayList for the hacks. (aVo style.) **/
		drawArray(var2, var3);

		/** The drawing of the pinned panels. **/
		for(Base_Panel uPanel : Base_Panels.panelArray) {
			if(uPanel.open && !(mc.currentScreen instanceof Base_Panels)) {
				uPanel.draw(var6, var7);
			}
		}
	}

	/** The drawing of the ArrayList for the hacks. (aVo style.) **/
	public static void drawArray(ArrayList<String> var1, FontRenderer var2) {
		for(int var3 = 0; var3 < var1.size(); var3++) {
			/** A local instance of the class ScaledResolution.java **/
			ScaledResolution var4 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);

			/** The local variables for the positions. **/
			int var5 = var4.getScaledWidth() - vars.font.getWidth((String)var1.get(var3)) / 2 + 6, var6 = var3 * 10;

			/** Drawing the string of the hacks name. **/
			//var2.drawStringWithShadow((String)var1.get(var3), var5, var6, 0xFFFFFFFF);
			//vars.slick.drawStringWithShadow(vars.font, var5, var6, (String)var1.get(var3));
			Base_Panels.ttf.drawStringS((String)var1.get(var3), var5, var6);
		}
	}

	/** The 2D drawing methods. **/
	public static void drawCenteredString(String var1, int var2, int var3, int var4){
		FontRenderer fr = mc.fontRenderer;

		fr.drawString(var1, var2 - fr.getStringWidth(var1) / 2, var3, var4);
	}

	public static void drawSmallCenteredString(String var1, int var2, int var3, int var4) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawCenteredString(var1, var2 * 2, var3 * 2, var4);
		glPopMatrix();
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

	public static void drawCircle(int xx, int yy, float g, int col) {
		float f = (float)(col >> 24 & 0xFF) / 255F;
		float f1 = (float)(col >> 16 & 0xFF) / 255F;
		float f2 = (float)(col >> 8 & 0xFF) / 255F;
		float f3 = (float)(col & 0xFF) / 255F;

		int sections = 70;
		double dAngle = 2 * Math.PI / sections;
		float x, y;

		glPushMatrix();
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glShadeModel(GL_SMOOTH);
		glBegin(GL_LINE_LOOP);

		for(int i = 0; i < sections; i++) {
			x = (float)(g * Math.cos(i * dAngle));
			y = (float)(g * Math.sin(i * dAngle));

			glColor4f(f1, f2, f3, f);
			glVertex2f(xx + x, yy + y);
		}

		glEnd();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
		glPopMatrix();
	}

	public static void drawFilledCircle(int xx, int yy, float radius, int col) {
		float f = (float)(col >> 24 & 0xFF) / 255F;
		float f1 = (float)(col >> 16 & 0xFF) / 255F;
		float f2 = (float)(col >> 8 & 0xFF) / 255F;
		float f3 = (float)(col & 0xFF) / 255F;

		int sections = 50;
		double dAngle = 2 * Math.PI / sections;
		float x, y;

		glPushMatrix();
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glBegin(GL_TRIANGLE_FAN);

		for(int i = 0; i < sections; i++) {
			x = (float)(radius * Math.sin((i * dAngle)));
			y = (float)(radius * Math.cos((i * dAngle)));

			glColor4f(f1, f2, f3, f);
			glVertex2f(xx + x, yy + y);
		}

		glEnd();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
		glPopMatrix();
	}

	public static void drawTriangle(int x, int y, int col) {
		float f = (float)(col >> 24 & 0xFF) / 255F;
		float f1 = (float)(col >> 16 & 0xFF) / 255F;
		float f2 = (float)(col >> 8 & 0xFF) / 255F;
		float f3 = (float)(col & 0xFF) / 255F;

		glPushMatrix();
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glBegin(GL_TRIANGLES);
		glColor4f(f1, f2, f3, f);
		glVertex2f(x, y + 2);
		glVertex2f(x + 2, y - 2);
		glVertex2f(x - 2, y - 2);
		glEnd();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
		glPopMatrix();
	}

	public static void drawHorizontalLine(float x, float y, float x2, int hex) {
		if (y < x) {
			float var5 = x;
			x = y;
			y = var5;
		}

		drawRect(x, x2, y + 1, x2 + 1, hex);
	}

	public static void drawVerticalLine(float x, float y, float y2, int hex) {
		if (y2 < y) {
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

	public static void drawBaseHollowBorderedRect(float x, float y, float x2, float y2, int col1) {
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 -1, col1);
	}

	public static void drawBaseHollowBorderedRectCustom(float x, float y, float x2, float y2, int col1, boolean var1) {
		drawVerticalLine(x, y, y2 + (var1 ? 0 : 1), col1);
		drawVerticalLine(x2 - 1, y - (var1 ? 2 : 0), y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 -1, col1);
	}

	public static void drawBaseGradientBorderedRect(float x, float y, float x2, float y2, int col1, int col2, int col3) {
		drawGradientRect(x + 1, y + 1, x2 - 1, y2 - 1, col2, col3);
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

	public static void drawHollowBorderedRect(float x, float y, float x2, float y2, int col1) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawBaseHollowBorderedRect(x * 2, y * 2, x2 * 2, y2 * 2, col1);
		glPopMatrix();
	}

	public static void drawHollowBorderedRectCustom(float x, float y, float x2, float y2, int col1, boolean var1) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawBaseHollowBorderedRectCustom(x * 2, y * 2, x2 * 2, y2 * 2, col1, var1);
		glPopMatrix();
	}

	public static void drawGradientBorderedRect(float x, float y, float x2, float y2, int col1, int col2, int col3) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);
		drawBaseGradientBorderedRect(x * 2, y * 2, x2 * 2, y2 * 2, col1, col2, col3);
		glPopMatrix();
	}

	public void drawButtonRect(float x, float y, float x2, float y2, boolean var1) {
		drawHollowBorderedRect(x, y, x2, y2, 0xFF13161D);
		drawHollowBorderedRectCustom(x + 0.5F, y + 0.5F, x2 - 1, y2 - 1, var1 ? 0xFF1A587E : 0xFF4C4C4C, false);
		drawHollowBorderedRectCustom(x + 1, y + 1, x2 - 0.5F, y2 - 0.5F, var1 ? 0xFF093B5B : 0xFF2E2E2E, true);
		drawGradientRect(x + 1, y + 1, x2 - 1, y2 - 1, var1 ? 0xFF014C7D : 0xFF3F3F3F, var1 ? 0xFF013150 : 0xFF292929);
	}

	public void drawItemTag(int x, int y, ItemStack item) {        
		glPushMatrix();
		glDisable(GL_DEPTH_TEST);
		glRotatef(180F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		glPopMatrix();
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, item, x, y);
		itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, item, x, y);
		RenderHelper.disableStandardItemLighting();
		glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		glEnable(GL_DEPTH_TEST);
		RenderHelper.disableStandardItemLighting();
	}

	public static void drawBase(int x, int y, int x1, int y1, float width, boolean shadow, int color) {
		glPushMatrix();
		float f1 = (float)(color >> 24 & 255) / 255.0F;
		float f2 = (float)(color >> 16 & 255) / 255.0F;
		float f3 = (float)(color >> 8 & 255) / 255.0F;
		float f4 = (float)(color & 255) / 255.0F;
		glTranslatef(x, y, 0);
		glColor4f(f2, f3, f4, f1);
		glLineWidth(width);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_LINE_SMOOTH);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glEnable(GL13.GL_MULTISAMPLE);
		glBegin(GL_LINES);
		glVertex2i(x, y);
		glVertex2i(x1, y1);
		glEnd();
		if(shadow)
		{
			glColor4f(0f, 0f, 0f, f1*0.75f);
			glBegin(GL_LINES);
			glVertex2f(x- width/2, y);
			glVertex2f(x1- width/2, y1);
			glEnd();
		}
		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_LINE_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glDisable(GL13.GL_MULTISAMPLE);
		glPopMatrix();
	}


	public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color) {
		glPushMatrix();
		float f1 = (float)(color >> 24 & 255) / 255.0F;
		float f2 = (float)(color >> 16 & 255) / 255.0F;
		float f3 = (float)(color >> 8 & 255) / 255.0F;
		float f4 = (float)(color & 255) / 255.0F;
		glTranslatef(x, y, 0);
		glColor4f(f2, f3, f4, f1);
		glLineWidth(width);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_LINE_SMOOTH);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_ALPHA_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		glEnable(GL13.GL_MULTISAMPLE);
		if(angle>0)
		{
			glBegin(GL_LINE_STRIP);
			for( int i=0; i<angle; i++ )
			{
				float a = (float) (i*(angle*Math.PI/points));
				float xc = (float) (Math.cos(a)*radius);
				float yc = (float) (Math.sin(a)*radius);
				glVertex2f(xc, yc);
			}
			glEnd();	
		}
		if(angle<0)
		{
			glBegin(GL_LINE_STRIP);
			for( int i=0; i>angle; i-- )
			{
				float a = (float) (i*(angle*Math.PI/points));
				float xc = (float) (Math.cos(a)*-radius);
				float yc = (float) (Math.sin(a)*-radius);
				glVertex2f(xc, yc);
			}
			glEnd();
		}
		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_LINE_SMOOTH);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_DEPTH_TEST);
		glDisable(GL13.GL_MULTISAMPLE);
		glDisable(GL_MAP1_VERTEX_3);
		glPopMatrix();
	}

	public static void drawCheck(int x, int y, float width, int color) {
		glPushMatrix();
		glScalef(0.5F, 0.5F, 0.5F);

		glPushMatrix();
		glRotatef(8F, x, y, 1);
		drawStrip(x * 2, y * 2, width, -18, 1000, 20, color);
		glPopMatrix();
		
		glPushMatrix();
		glRotatef(-95F, x, y, 1);
		drawStrip(x * 2 - 8, y * 2 + 6, width, -18, 1000, 20, color);
		glPopMatrix();
		glPopMatrix();
	}
}