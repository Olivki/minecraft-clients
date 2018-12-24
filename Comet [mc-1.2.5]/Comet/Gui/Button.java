package Comet.Gui;

import Comet.Utils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class Button extends GuiButton {

	public Button(int i, int j, int k, String s) {
		this(i, j, k, 120, 250, s);
	}

	public Button(int i, int j, int k, int l, int i1, String s) {
		super(i, j, k, l, i1, s);
	}

	protected int getHoverState(boolean flag) {
		byte byte0 = 1;
		if (!enabled)
		{
			byte0 = 0;
		}
		else if (flag)
		{
			byte0 = 2;
		}
		return byte0;
	}

	public void drawButton(Minecraft mc, int mx, int my) {
		FontRenderer fontrenderer = mc.fontRenderer;
		Methods meth = new Methods();
		
		boolean flag = mx >= xPosition && my >= yPosition && mx < xPosition + xPosition && my < yPosition + yPosition;
		if (flag)
		{
			meth.drawSmallWBorderedRect(xPosition, yPosition, xPosition + xPosition, yPosition + yPosition, 0xFF000000, 0x500E0E0E);
			drawCenteredString(fontrenderer, displayString, xPosition + xPosition / 2, yPosition + (yPosition - 8) / 2, 0xFFFFA0);
		} else {
			meth.drawSmallWBorderedRect(xPosition, yPosition, xPosition + xPosition, yPosition + yPosition, 0xFF000000, 0x500E0E0E);
			drawCenteredString(fontrenderer, displayString, xPosition + xPosition / 2, yPosition + (yPosition - 8) / 2, 0xFFFFFFFF);
		}
	}

}