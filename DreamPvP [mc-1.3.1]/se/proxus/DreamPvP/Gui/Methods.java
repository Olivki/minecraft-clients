package se.proxus.DreamPvP.Gui;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL12;

import net.minecraft.src.*;
import se.proxus.DreamPvP.*;
import se.proxus.DreamPvP.Utils.Placeholders.u_Waypoint;

public class Methods {

	public static DreamPvP dp = new DreamPvP();
	private static RenderItem itemRenderer = new RenderItem();	

	public void drawMain(FontRenderer fr, String name, ArrayList<String> guiArray) {	
		ScaledResolution sc = new ScaledResolution(dp.mc.gameSettings, dp.mc.displayWidth, dp.mc.displayHeight);
		//String biome = dp.utils.getBiomeName(), worldTime = dp.utils.getWorldTime(), pos = dp.utils.getPos();
		int width = fr.getStringWidth(name + "[" + dp.settings.curVersion + "]");

		fr.drawStringWithShadow(name + "[§e" + dp.settings.curVersion + "§r]", 2, 2, 0xFFFFFFFF);
		drawItem(width + 2, 0, Item.pocketSundial.shiftedIndex);
		drawItem(width + 18, -1, Item.compass.shiftedIndex);
		//fr.drawStringWithShadow(pos + ".", 2, 12, 0xFFFFFFFF);
		drawArray(fr, guiArray);
		drawInfo(fr);

		if(dp.bModList.miniMap.getState()) {
			if(dp.settings.radarMode == 1) {
				drawMinimap(sc.getScaledWidth() - 108, -14, sc.getScaledWidth() - 108, -13);
			} else if(dp.settings.radarMode == 2) {
				drawRadar(sc.getScaledWidth(), 2, fr);
			}
		}
	}

	public static void drawStringWithOutline(String var1, float var2, float var3, int var4) {
		FontRenderer fr = dp.mc.fontRenderer;
		fr.renderString(var1, var2 + 0.5F, var3, var4, true);
		fr.renderString(var1, var2, var3 + 0.5F, var4, true);
		fr.renderString(var1, var2 - 0.5F, var3, var4, true);
		fr.renderString(var1, var2, var3 - 0.5F, var4, true);
		fr.drawString(var1, var2, var3, var4);
	}

	public void drawInfo(FontRenderer fr) {
		ScaledResolution sc = new ScaledResolution(dp.mc.gameSettings, dp.mc.displayWidth, dp.mc.displayHeight);
		DateFormat DTEformat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String date = DTEformat.format(cal.getTime());

		drawSmallString(fr, date, sc.getScaledWidth() - fr.getStringWidth(date) + 50, sc.getScaledHeight() - 5, 0xFFFFFFFF);
	}

	public static void drawArray(FontRenderer fr, ArrayList<String> guiArray) {
		for(int I1 = 0; I1 < guiArray.size(); I1++) {
			String s = (String) guiArray.get(I1);
			int I2 = I1 * 11;
			int I3 = I2 + 12;
			int I4 = I1 + 3;

			fr.drawStringWithShadow(s, 2, I3, 0xFFCCCCCC);
		}
	}

	public static void drawRadar(int x, int y, FontRenderer fr) {
		try {
			for(int var1 = 0; var1 < dp.mc.theWorld.loadedEntityList.size(); var1++) {
				EntityPlayer e = (EntityPlayer)dp.mc.theWorld.playerEntities.get(var1);

				if(e != dp.mc.thePlayer && e != null) {
					int var2 = x - fr.getStringWidth(dp.utils.getDistance(e.username, e, dp.mc.thePlayer)) - 2, var3 = var1 * 10 - 1;
					
					fr.drawStringWithShadow(dp.utils.getDistance(e.username, e, dp.mc.thePlayer), var2, var3, 0xFFFFFFFF);
				}
			}
		} catch (Exception e) {
			//dp.utils.log(e.getMessage());
		}
	}

	public static void drawCenteredString(FontRenderer fr, String s, int x, int y, int col) {
		fr.drawStringWithShadow(s, x - fr.getStringWidth(s) / 2, y, col);
	}

	public static void drawCenteredStringRGB(FontRenderer fr, String s, int x, int y, float r, float g, float b) {
		fr.drawStringRGB(s, x - fr.getStringWidth(s) / 2, y, r, g, b);
	}

	public static void drawSmallString(FontRenderer fr, String s, int x, int y, int col) {
		glPushMatrix();
		glScaled(0.5D, 0.5D, 0.5D);
		fr.drawStringWithShadow(s, x * 2, y * 2, col);
		glPopMatrix();
	}

	public static void drawIRCBox(int xPos, int yPos, int width, int height) {
		drawRect(xPos - 1, yPos - 1, xPos + width + 1, yPos + height + 1, 0xFFA0A0A0);
		drawRect(xPos, yPos, xPos + width, yPos + height, 0xFF000000);
	}

	public static void drawHorizontalLine(int x, int x2, int y, int col) {
		if (x2 < x) {
			int i = x;
			x = x2;
			x2 = i;
		}

		drawRect(x, y, x2 + 1, y + 1, col);
	}

	public static void drawVerticalLine(int x, int y, int y2, int col) {
		if (y2 < y) {
			int i = y;
			y = y2;
			y2 = i;
		}

		drawRect(x, y + 1, x + 1, y2, col);
	}

	public static void drawBorderedRect(int x, int y, int x2, int y2, int col1, int col2) {
		drawRect(x, y, x2, y2, col2);
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 - 1, col1);
	}

	public static void drawHollowBorderedRect(int x, int y, int x2, int y2, int col1) {
		drawVerticalLine(x, y, y2 - 1, col1);
		drawVerticalLine(x2 - 1, y, y2 - 1, col1);
		drawHorizontalLine(x, x2 - 1, y, col1);
		drawHorizontalLine(x, x2 - 1, y2 - 1, col1);
	}

	public static void drawRect(float x, float y, float x2, float y2, int col1) {
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
		glColor4f(f1, f2, f3, f);
		glBegin(GL_QUADS);
		glVertex2d(x2, y);
		glVertex2d(x, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}
	
	public static void drawRectRGB(float x, float y, float x2, float y2, float r, float g, float b) {
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
		glColor4f(r, g, b, 0.4F);
		glBegin(GL_QUADS);
		glVertex2d(x2, y);
		glVertex2d(x, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}

	public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6) {
		float f = (float)(par5 >> 24 & 0xff) / 255F;
		float f1 = (float)(par5 >> 16 & 0xff) / 255F;
		float f2 = (float)(par5 >> 8 & 0xff) / 255F;
		float f3 = (float)(par5 & 0xff) / 255F;
		float f4 = (float)(par6 >> 24 & 0xff) / 255F;
		float f5 = (float)(par6 >> 16 & 0xff) / 255F;
		float f6 = (float)(par6 >> 8 & 0xff) / 255F;
		float f7 = (float)(par6 & 0xff) / 255F;
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_ALPHA_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glShadeModel(GL_SMOOTH);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(f1, f2, f3, f);
		tessellator.addVertex(par3, par2, 0);
		tessellator.addVertex(par1, par2, 0);
		tessellator.setColorRGBA_F(f5, f6, f7, f4);
		tessellator.addVertex(par1, par4, 0);
		tessellator.addVertex(par3, par4, 0);
		tessellator.draw();
		glShadeModel(GL_FLAT);
		glDisable(GL_BLEND);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_TEXTURE_2D);
	}

	public static void drawSideGradientRect(float x, float y, float x2, float y2, int col1, int col2) {
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		float f4 = (float)(col2 >> 24 & 0xFF) / 255F;
		float f5 = (float)(col2 >> 16 & 0xFF) / 255F;
		float f6 = (float)(col2 >> 8 & 0xFF) / 255F;
		float f7 = (float)(col2 & 0xFF) / 255F;

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);
		glShadeModel(GL_SMOOTH);

		glPushMatrix();
		glBegin(GL_QUADS);
		glColor4f(f1, f2, f3, f);
		glVertex2d(x2, y);

		glColor4f(f5, f6, f7, f4);
		glVertex2d(x, y);
		glVertex2d(x, y2);

		glColor4f(f1, f2, f3, f);
		glVertex2d(x2, y2);
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
		glShadeModel(GL_FLAT);
	}

	/*public static void drawBorderedRect(int x, int y, int x2, int y2, float l1, int col1, int col2) {
		drawRect(x, y, x2, y2, col2);

		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
		glColor4f(f1, f2, f3, f);
		glLineWidth(l1);
		glBegin(GL_LINES);
		glVertex2d(x, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glVertex2d(x2, y);
		glVertex2d(x, y);
		glVertex2d(x2, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}*/

	public static void drawHollowBorderedRect(int x, int y, int x2, int y2, float l1, int col1) {
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
		glColor4f(f1, f2, f3, f);
		glLineWidth(l1);
		glBegin(GL_LINES);
		glVertex2d(x, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glVertex2d(x2, y);
		glVertex2d(x, y);
		glVertex2d(x2, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}

	public static void drawGradientBorderedRect(int x, int y, int x2, int y2, float l1, int col1, int col2, int col3) {
		drawGradientRect(x, y, x2, y2, col2, col3);

		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
		glColor4f(f1, f2, f3, f);
		glLineWidth(l1);
		glBegin(GL_LINES);
		glVertex2d(x, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glVertex2d(x2, y);
		glVertex2d(x, y);
		glVertex2d(x2, y);
		glVertex2d(x, y2);
		glVertex2d(x2, y2);
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}

	public static void drawVerticalLine(int x, int y, int y2, float l1, int col1) {
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
		glColor4f(f1, f2, f3, f);
		glLineWidth(l1);
		glBegin(GL_LINES);
		glVertex2d(x, y);
		glVertex2d(x, y2);
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}

	public static void drawHorizontalLine(int x, int x2, int y, float l1, int col1) {
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
		glColor4f(f1, f2, f3, f);
		glLineWidth(l1);
		glBegin(GL_LINES);
		glVertex2d(x, y);
		glVertex2d(x2, y);
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}

	public static void drawDiagonalLine(int x, int x2, int y, float l1, int col1) {
		float f = (float)(col1 >> 24 & 0xFF) / 255F;
		float f1 = (float)(col1 >> 16 & 0xFF) / 255F;
		float f2 = (float)(col1 >> 8 & 0xFF) / 255F;
		float f3 = (float)(col1 & 0xFF) / 255F;

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);

		glPushMatrix();
		glColor4f(f1, f2, f3, f);
		glLineWidth(l1);
		glBegin(GL_LINES);
		glVertex2d(x, y);
		glVertex2d(y, x2);
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}

	public static void drawCircle(int xx, int yy, int radius, int col) {
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
		glEnable(GL_LINE_SMOOTH);
		glShadeModel(GL_SMOOTH);
		glBegin(GL_LINE_LOOP);

		for(int i = 0; i < sections; i++) {
			x = (float)(radius * Math.cos(i * dAngle));
			y = (float)(radius * Math.sin(i * dAngle));

			glColor4f(f1, f2, f3, f);
			glVertex2f(xx + x, yy + y);
		}

		glEnd();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
		glPopMatrix();
	}

	public static void drawCircleRGB(int xx, int yy, int radius, float r, float g, float b) {
		int sections = 70;
		double dAngle = 2 * Math.PI / sections;
		float x, y;

		glPushMatrix();
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);
		glShadeModel(GL_SMOOTH);
		glBegin(GL_LINE_LOOP);

		for(int i = 0; i < sections; i++) {
			x = (float)(radius * Math.cos(i * dAngle));
			y = (float)(radius * Math.sin(i * dAngle));

			glColor4f(r, g, b, 1.0F);
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
		glEnable(GL_LINE_SMOOTH);
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

	public static void drawFilledCircleRGB(int xx, int yy, float radius, float r, float g, float b) {
		int sections = 50;
		double dAngle = 2 * Math.PI / sections;
		float x, y;

		glPushMatrix();
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);
		glBegin(GL_TRIANGLE_FAN);

		for(int i = 0; i < sections; i++) {
			x = (float)(radius * Math.sin((i * dAngle)));
			y = (float)(radius * Math.cos((i * dAngle)));

			glColor4f(r, g, b, 1.0F);
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
		glEnable(GL_LINE_SMOOTH);

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

	public static void drawPlayerSkin(String username, int x, int y) {
		EntityOtherPlayerMP player = new EntityOtherPlayerMP(dp.mc.theWorld, username);
		RenderManager render = RenderManager.instance;
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int j = x + 14;
		int k = y + 56;
		glPushMatrix();
		glEnable(GL_DEPTH_TEST);
		glTranslatef(j, k, 0F);
		float f = 30F;
		glScalef(-f, f, f);
		glRotatef(180f, 0f, 0f, 1f);
		RenderHelper.disableStandardItemLighting();
		player.rotationYawHead = 0;
		render.renderEntityWithPosYaw(player, 0.0D, 0.0D, 0.0D, 0.0F, 98.0F);
		glDisable(GL_DEPTH_TEST);
		glDisable(GL12.GL_RESCALE_NORMAL);
		glPopMatrix();
	}

	public void drawItem(int x, int y, int id) {
		RenderHelper.enableGUIStandardItemLighting();
		glPushMatrix();
		glDisable(GL_DEPTH_TEST);
		itemRenderer.renderItemIntoGUI(dp.mc.fontRenderer, dp.mc.renderEngine, new ItemStack(Block.chest), 999, 999);
		itemRenderer.drawItemIntoGui(dp.mc.fontRenderer, dp.mc.renderEngine, id, 0, Item.itemsList[id].getIconFromDamage(0), x + 1, y + 1);
		glEnable(GL_DEPTH_TEST);
		glPopMatrix();
		RenderHelper.disableStandardItemLighting();
	}

	public void drawItemTag(int x, int y, ItemStack item) {        
		glPushMatrix();
		glDisable(GL_DEPTH_TEST);
		glRotatef(180F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		glPopMatrix();
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		itemRenderer.renderItemIntoGUI(dp.mc.fontRenderer, dp.mc.renderEngine, item, x, y);
		itemRenderer.renderItemOverlayIntoGUI(dp.mc.fontRenderer, dp.mc.renderEngine, item, x, y);
		RenderHelper.disableStandardItemLighting();
		glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		glEnable(GL_DEPTH_TEST);
		RenderHelper.disableStandardItemLighting();
	}

	public static void drawMinimap(int posXs, int posYs, int rectX, int rectY){
		FontRenderer fr = dp.mc.fontRenderer;
		ScaledResolution scaledresolution = new ScaledResolution(dp.mc.gameSettings, dp.mc.displayWidth, dp.mc.displayHeight);
		glPushMatrix();
		glDepthMask(false);
		//drawBorderedRect(rectX,  rectY + 14, rectX + 107, rectY + 110, 0xFFA1A1A1, 0x80525252);
		glTranslatef(posXs + 120 - 68, posYs + 66, 0.0F);
		dp.mc.renderEngine.bindTexture(dp.mc.renderEngine.getTexture("/terrain.png"));
		for(int posX = -120/2 + 7; posX < 120/2 - 8; posX++) {
			for(int posZ = -120/2 + 9; posZ < 120/2 - 9; posZ++) {

				int blockX = (int) (dp.mc.thePlayer.posX + posX);
				int blockZ = (int) (dp.mc.thePlayer.posZ + posZ);            
				int height = dp.mc.theWorld.getHeightValue(blockX, blockZ);
				int blockY = height - 1;

				int distanceX = (int) (2*(dp.mc.thePlayer.posX - blockX));
				int distanceZ = (int) (2*(dp.mc.thePlayer.posZ - blockZ));
				int blockId = dp.mc.theWorld.getBlockId(blockX, blockY, blockZ);

				if(250 > distanceX + distanceZ && blockId != 0) {

					int blockIndex = Block.blocksList[blockId].blockIndexInTexture;
					if(blockId == 18) {
						float f = (float) (0x990DFF00 >> 24 & 0xff) / 255F;
						float f1 = (float) (0x0DFF00 >> 16 & 0xff) / 255F;
						float f2 = (float) (0x0DFF00) / 255F;
						float f3 = (float) (0x0DFF00 & 0xff) / 255F;
						glColor4f(f1, f2, f3, f);
					} else {
						glColor4f(256 - blockY, 256 - blockY, 256 - blockY, 1);
					}

					dp.mc.ingameGUI.drawTexturedModalRect(distanceX / 2 - 1, distanceZ / 2 - 1, blockIndex % 16 << 4, (blockIndex >> 4) << 4, 1, 1);
				}
			}
		}

		glRotatef(dp.mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
		drawTriangle(0, 0 - 2, 0x80FFFFFF);
		drawFilledCircle(0, 0, 2, 0xFFFFFFFF);
		glRotatef(-dp.mc.thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);

		for(u_Waypoint wt : dp.settings.waypointArray) {
			int j = (int) Math.round(dp.mc.thePlayer.posX);
			int k = (int) Math.round(dp.mc.thePlayer.posZ);
			int l = (int) Math.round(wt.x);
			int i1 = (int) Math.round(wt.z);
			int j1 = j - l;
			int k1 = k - i1;

			if(j1 > 102 - 56 || k1 > posYs + 64 || j1 < -102 + 56 || k1 < posYs - 64 || !dp.bModList.waypoint.getState()) {
				continue;
			}

			int xDif = (int)(dp.mc.thePlayer.posX - wt.x);
			int zDif = (int)(dp.mc.thePlayer.posZ - wt.z);
			int width = dp.mc.fontRenderer.getStringWidth((wt.name));		

			glTranslatef(xDif, zDif, 0.0F);

			drawFilledCircleRGB(0, 0, 2, wt.r, wt.g, wt.b);

			if(dp.settings.curServerIP.equalsIgnoreCase(wt.ip)) {
				glScalef(0.5F, 0.5F, 0.5F);
				drawRect(0 * 2, 0 * 2, width + 3 * 2, -10 * 2, 0x90000000);
				dp.mc.fontRenderer.drawStringWithShadow((dp.utils.getDistance(wt.name, dp.mc.thePlayer, wt.x, wt.z)), 2 * 2, -9 * 2, 0xFFFFFFFF);
				glScalef(2.0F, 2.0F, 2.0F);
			}
		}

		for (int i = 0; i < dp.mc.theWorld.playerEntities.size(); i++) {
			Entity entity = (Entity)dp.mc.theWorld.playerEntities.get(i);

			int j = (int) Math.round(dp.mc.thePlayer.posX);
			int k = (int) Math.round(dp.mc.thePlayer.posZ);
			int l = (int) Math.round(entity.posX);
			int i1 = (int) Math.round(entity.posZ);
			int j1 = j - l;
			int k1 = k - i1;

			if(j1 > 102 - 56 || k1 > posYs + 64 || j1 < -102 + 56 || k1 < posYs - 64) {
				continue;
			}

			if(entity != dp.mc.thePlayer) {
				int xDif = (int)(dp.mc.thePlayer.posX - ((EntityPlayer)entity).posX);
				int zDif = (int)(dp.mc.thePlayer.posZ - ((EntityPlayer)entity).posZ);
				int width = dp.mc.fontRenderer.getStringWidth(((EntityPlayer)entity).username);		
				double x = entity.posX, z = entity.posZ;
				String name = ((EntityPlayer)entity).username;	

				glTranslatef(xDif, zDif, 0.0F);

				glRotatef(entity.rotationYaw, 0.0F, 0.0F, 1.0F);
				drawTriangle(0, 0 - 2, 0x80FFFFFF);
				drawFilledCircle(0, 0, 2, 0xFFFFFFFF);
				glRotatef(-entity.rotationYaw, 0.0F, 0.0F, 1.0F);

				glScalef(0.5F, 0.5F, 0.5F);
				drawRect(0, 0, width + 3 * 2, -10 * 2, 0x90000000);
				dp.mc.fontRenderer.drawStringWithShadow((dp.utils.getDistance(name, dp.mc.thePlayer, x, z)), 2 * 2, -9 * 2, 0xFFFFFFFF);
				glScalef(2.0F, 2.0F, 2.0F);
			}
		}

		glDepthMask(true);
		glPopMatrix();
	}
}