package Comet.Gui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import Comet.Gui.Menu.*;
import Comet.Utils.*;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class InGame extends Methods {

	public static ArrayList array = new ArrayList();

	public static Minecraft mc = Minecraft.theMinecraft;
	public static FontRenderer fr = mc.fontRenderer;
	public static ScaledResolution sc;

	public static int W;
	public static int H;

	public static float UpdateCounter;
	public static float UpdateCounter2;
	public static float f14;

	public static TTF ttfFont;
	
	public InGame() {
		if (ttfFont == null) {ttfFont = new TTF(mc, "Lucida Typewriter", 16);}
	}

	public static void renderGameOverlay(float par1, int par3, int par4) {
		String posX = "X: " + (int)mc.thePlayer.posX;
		String posY = "Y: " + (int)mc.thePlayer.posY;
		String posZ = "Z: " + (int)mc.thePlayer.posZ;
		//UpdateCounter++; if(UpdateCounter == 360) {UpdateCounter = 0;}
		
		fr.drawStringWithShadow(Color.AQUA + "Comet", 1, 1, 0xFFFFFFFF);
		fr.drawStringWithShadow(posX, 1, 11, 0xFFFFFFFF);
		fr.drawStringWithShadow(posY, 1, 21, 0xFFFFFFFF);
		fr.drawStringWithShadow(posZ, 1, 31, 0xFFFFFFFF);
		
		drawArrayList();
		renderCompass();

		if(mc.thePlayer.isDead){
			mc.comet.settings.D_X = mc.thePlayer.posX;
			mc.comet.settings.D_Y = mc.thePlayer.posY;
			mc.comet.settings.D_Z = mc.thePlayer.posZ;}
		
		m_Player player = new m_Player();
		m_World world = new m_World();
		m_Settings settings = new m_Settings();
		m_xRayGui xraygui = new m_xRayGui();

		if(!(mc.currentScreen instanceof Clickable)) {
			if(player.pinned){player.drawScreen(par3, par4, par1);}
			if(world.pinned){world.drawScreen(par3, par4, par1);}
			if(settings.pinned){settings.drawScreen(par3, par4, par1);}
			if(xraygui.pinned){xraygui.drawScreen(par3, par4, par1);}
		}
	}

	public static void drawArrayList() {
		Gui gui = new Gui();
		ScaledResolution sc = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);

		for (int I1 = 0; I1 < array.size(); I1++) {
			String s = (String) array.get(I1);
			int I2 = I1 * 10;
			int I3 = I2 + 2;
			int I4 = sc.getScaledWidth();
			int I5 = I4 - fr.getStringWidth(s) - 3;
			fr.drawStringWithShadow(Color.WHITE + s, I5, I3, 0xFFFFFF);
			/*int I5 = I4 * 2 - ttfFont.getStringWidth(s) - 4;
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			ttfFont.drawStringS(gui, Color.WHITE + s, I5, I3 + -2 * 2);
			GL11.glPopMatrix();*/
		}
	}

}