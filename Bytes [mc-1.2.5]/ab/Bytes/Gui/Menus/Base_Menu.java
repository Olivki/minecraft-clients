package ab.Bytes.Gui.Menus;

import ab.Bytes.*;
import net.minecraft.src.*;

public abstract class Base_Menu extends GuiScreen {
	
	public static Bytes bs = new Bytes();
	
	protected static boolean
	enabled = false,
	pinned = false,
	dragged = false;

	public static int
	dragX = 2,
	dragY = 0,
	startX,
	startY;	
	
	public abstract void drawScreen(int I1, int I2, float I3);

	public abstract void mouseClicked(int I1, int I2, int I3);

	public abstract void mouseMovedOrUp(int I1, int I2, int I3);
	
	public abstract void mouseDragged(int I1, int I2);
	
	public abstract boolean enabled();
	
	public abstract boolean pinned();
	
	public static void playSound() {
		bs.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
	}
	
	public boolean isInMenuArray(Base_Menu menu) {
		return Base_MenuList.menuArray.contains(menu);
	}
	
}