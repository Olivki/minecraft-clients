package ab.Bytes.Gui.Menus;

import java.util.ArrayList;

import net.minecraft.src.FontRenderer;

public class Base_Label extends Base_Button {

	protected boolean enabled;
	protected ArrayList buttonArray = new ArrayList();
	protected int rectSize;

	public Base_Label(int ids, String buttonStrings, int positionXs, int positionYs, boolean enableds, ArrayList buttonArrays, int rectSizes) {
		super(ids, buttonStrings, positionXs, positionYs, false);
		enabled = enableds;
		buttonArray = buttonArrays;
		rectSize = rectSizes;
	}
	
	@Override
	protected void drawButton(int I1, int I2) {
		FontRenderer fr = bt.mc.fontRenderer;
		
		drawBorderedRect(positionX - 2, positionY, positionX + 92, positionY + 14, 0xFFAFAFAF, 0xFF525252);
		fr.drawStringWithShadow(buttonString, positionX + 1, positionY + 3, 0xFFFFFFFF);
		
		if(enabled) {
			drawBorderedRect(positionX - 2, positionY + 13, positionX + 92, positionY + 11 + buttonArray.size() * 12 - rectSize, 0xFFAFAFAF, 0x80525252);
		}
	}

	@Override
	protected boolean mousePressed(int I1, int I2) {
		return I1 >= positionX - 2 && I2 >= positionY && I1 < positionX + 69 && I2 < positionY + 14;
	}
}