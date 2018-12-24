package se.proxus.DreamPvP.Gui;

import java.util.ArrayList;

import se.proxus.DreamPvP.Utils.Placeholders.u_Waypoint;

import net.minecraft.src.*;

public class InGame extends Methods {
	
	public static ArrayList<String> guiArray = new ArrayList<String>();
	
	public static int page = 1;
	
	public void drawOverlay(int i1, int i2, float i3) {
		FontRenderer fr = dp.mc.fontRenderer;
		
		drawMain(fr, "Dream PvP ", guiArray);
	}
}