package ab.Bytes.Gui;

import java.util.ArrayList;

import net.minecraft.src.*;

import ab.Bytes.*;

public class InGame extends Methods {
	
	public static ArrayList guiArray = new ArrayList();
	
	public static Bytes bs = new Bytes();
	
	public static void renderOverlay(int I1, int I2, float I3) {
		FontRenderer fr = bs.mc.fontRenderer;
		
		drawMain(bs.settings.info, fr);
		drawArray(guiArray, fr);
		drawPin(I1, I2, I3);
	}

}
