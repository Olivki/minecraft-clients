package ab.Bytes.Gui.Menus;

import net.minecraft.src.*;
import ab.Bytes.*;
import ab.Bytes.Gui.*;

public class Base_Button extends Methods {
	
	public Bytes bt = new Bytes();
	
	public int id, positionX, positionY;

	public String buttonString;
	
	public boolean enabled;

	public Base_Button(int ids, String buttonStrings, int positionXs, int positionYs, boolean enableds) {
		id = ids;
		buttonString = buttonStrings;
		positionX = positionXs;
		positionY = positionYs;
		enabled = enableds;
	}

	protected void drawButton(int I1, int I2) {
		FontRenderer fr = bt.mc.fontRenderer;
		boolean I3 = I1 >= positionX && I2 >= positionY && I1 < positionX + 90 && I2 < positionY + 14 && bs.mc.currentScreen instanceof Base_MenuList;
		int I4 = getHover(I3);
		mouseDragged(I1, I2);
		
		if(I3) {
			drawBorderedRect(positionX, positionY, positionX + 90, positionY + 14, 0xFFAFAFAF, enabled ? 0x40960028 : 0x40007143);
			drawCenteredString(fr, buttonString, positionX + 44, positionY + 3, 0xFFFFFFA0);
		} else {
			drawBorderedRect(positionX, positionY, positionX + 90, positionY + 14, 0xFFAFAFAF, enabled ? 0xFF007143 : 0xFF960028);
			drawCenteredString(fr, buttonString, positionX + 44, positionY + 3, 0xFFFFFFFF);
		}
	}

	protected int getHover(boolean I1) {
		byte I2 = 1;
		if(I1) {
			I2 = 2;
		}
		return I2;
	}
	
	protected boolean mousePressed(int I1, int I2) {
		return I1 >= positionX && I2 >= positionY && I1 < positionX + 90 && I2 < positionY + 14;
	}
	
	protected void mouseDragged(int I1, int I2) {
		
	}
	
	protected void mouseReleased(int I1, int I2) {
		
	}
}