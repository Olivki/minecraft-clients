package ab.Bytes.Gui.Menus;

import net.minecraft.src.*;

public class Base_SmallButton extends Base_Button {

	public Base_SmallButton(int ids, int positionXs, int positionYs, boolean enableds) {
		super(ids, "", positionXs, positionYs, enableds);
	}

	@Override
	protected void drawButton(int I1, int I2) {	
		boolean I3 = I1 >= positionX && I2 >= positionY && I1 < positionX + 10 && I2 < positionY + 10 && bs.mc.currentScreen instanceof Base_MenuList;
		int I4 = getHover(I3);
		
		if(I3) {
			drawBorderedRect(positionX, positionY, positionX + 10, positionY + 10, 0xFFAFAFAF, enabled ? 0x40960028 : 0x40007143);
		} else {
			drawBorderedRect(positionX, positionY, positionX + 10, positionY + 10, 0xFFAFAFAF, enabled ? 0xFF007143 : 0xFF960028);
		}
	}

	@Override
	protected boolean mousePressed(int I1, int I2) {
		return I1 >= positionX && I2 >= positionY && I1 < positionX + 10 && I2 < positionY + 10;
	}
}