package ab.Bytes.Gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import ab.Bytes.*;
import ab.Bytes.Gui.Menus.*;
import net.minecraft.src.*;

public class Methods extends Gui {

	public static Bytes bs = new Bytes();

	public static void drawMain(String name, FontRenderer fr) {
		String posX = " [X: " + (int)bs.mc.thePlayer.posX;
		String posY = " Y: " + (int)bs.mc.thePlayer.posY;
		String posZ = " Z: " + (int)bs.mc.thePlayer.posZ + "]";

		fr.drawStringWithShadow(name + posX + posY + posZ, 1, 1, 0xFFFFFFFF);
	}

	public static void drawArray(ArrayList array, FontRenderer fr) {
		ScaledResolution sc = new ScaledResolution(bs.mc.gameSettings, bs.mc.displayWidth, bs.mc.displayHeight);

		for(int I1 = 0; I1 < array.size(); I1++) {
			String s = (String) array.get(I1);
			int I2 = I1 * 10;
			int I3 = I2 + 2;
			int I4 = sc.getScaledWidth();
			int I5 = I4 - fr.getStringWidth(s) + 3;
			fr.drawStringWithShadow(s, I5, I3, 0xFFFFFFFF);
		}
	}

	public static void drawPin(int I1, int I2, float I3) {
		Base_MenuList mList = Base_MenuList.instance;

		if(!(bs.mc.currentScreen instanceof Base_MenuList)) {
			if(mList.player.pinned()) {
				mList.player.drawScreen(I1, I2, I3);
			}

			if(mList.world.pinned()) {
				mList.world.drawScreen(I1, I2, I3);
			}

			if(mList.nuker.pinned()) {
				mList.nuker.drawScreen(I1, I2, I3);
			}
		}
	}

	public static void drawBorderedRect(int x, int y, int x1, int y1, int CO1, int CO2) {
		drawRect(x, y, x1, y1, CO2);
		drawVerticalLine(x, y, y1 -1, CO1);
		drawVerticalLine(x1 - 1, y, y1 - 1, CO1);
		drawHorizontalLine(x, x1 - 1, y, CO1);
		drawHorizontalLine(x, x1 - 1, y1 -1, CO1);
	}

	public static void drawBaseMouse(int x, int y) {
		//drawBorderedRect(x, y, x + 5, y + 5, 0xFFAFAFAF, 0xFF525252);
		for(int I1 = 0; I1 < 7; I1++) {
			drawHorizontalLine(x - I1, x + I1, y + I1, 0xFF525252);
			drawVerticalLine(x + I1, y + I1, y + I1, 0xFFAFAFAF);
		}
		
		for(int I2 = 0; I2 < 7; I2++) {
			drawVerticalLine(x - I2, y + I2, y + I2, 0xFFAFAFAF);
		}
		
		for(int I3 = 0; I3 < 7; I3++) {
			drawHorizontalLine(x - 3, x + 3, y + 7 + I3, 0xFF525252);
		}
		
		drawHorizontalLine(x - 6, x - 3, y + 6, 0xFFAFAFAF);
		drawHorizontalLine(x + 6, x + 3, y + 6, 0xFFAFAFAF);
		
		drawVerticalLine(x - 3, y + 6, y + 13, 0xFFAFAFAF);
		drawVerticalLine(x + 3, y + 6, y + 13, 0xFFAFAFAF);
		
		drawHorizontalLine(x - 3, x + 3, y + 13, 0xFFAFAFAF);
	}

	public static void drawMouse(int x, int y) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawBaseMouse(x * 2, y * 2);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public void drawSmallCenteredString(FontRenderer fr, String s, int x, int y, int CO1) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawCenteredString(fr, s, x * 2, y * 2, CO1);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}
}
