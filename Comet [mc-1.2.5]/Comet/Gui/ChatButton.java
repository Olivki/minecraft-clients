package Comet.Gui;

import org.lwjgl.opengl.GL11;

import Comet.Utils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class ChatButton extends GuiButton {

	public static boolean upSideDown;
	public static int yPostion2;
	public static int xPostion2;

	public ChatButton(int i, int j, int k, int l, int i1, String s) {
		super(i, j, k, l, i1, s);
		yPostion2 = i1;
		xPostion2 = l;
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

		boolean flag = mx >= xPosition && my >= yPosition && mx < xPosition + xPostion2 && my < yPosition + yPostion2;
		if (flag)
		{
			meth.drawSmallWBorderedRect(xPosition, yPosition, xPosition + xPostion2, yPosition + yPostion2, 0xFF000000, 0x500E0E0E);
			meth.drawCenteredString(fontrenderer, displayString, xPosition + xPostion2 / 2, yPosition + (yPostion2 - 8) / 2, 0xFFFFA0);
		} else {
			meth.drawSmallWBorderedRect(xPosition, yPosition, xPosition + xPostion2, yPosition + yPostion2, 0xFF000000, 0x500E0E0E);
			meth.drawCenteredString(fontrenderer, displayString, xPosition + xPostion2 / 2, yPosition + (yPostion2 - 8) / 2, 0xFFFFFFFF);
		}
	}
}