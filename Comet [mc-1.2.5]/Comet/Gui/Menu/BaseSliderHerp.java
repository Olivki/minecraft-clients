package Comet.Gui.Menu;

import net.minecraft.src.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import Comet.Gui.Methods;

public class BaseSliderHerp extends GuiButton
{
	protected int width;
	protected int height;
	public int xPosition;
	public int yPosition;
	public String displayString;
	public int id;
	public boolean enabled;
	public boolean drawButton;

	public BaseSliderHerp(int i, int j, int k, String s) {
		this(i, j, k, 102, 16, s);
	}

	public BaseSliderHerp(int i, int j, int k, int l, int i1, String s) {
		super(i, j, k, l, i1, s);
		width = 102;
		height = 16;
		enabled = true;
		drawButton = true;
		id = i;
		xPosition = j;
		yPosition = k;
		width = l;
		height = i1;
		displayString = s;
	}

	protected int getHoverState(boolean flag)
	{
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

	public void drawButton(Minecraft minecraft, int i, int j) {
		if (!drawButton)
		{
			return;
		}
		FontRenderer fontrenderer = minecraft.fontRenderer;
		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, minecraft.renderEngine.getTexture("/gui/gui.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		boolean flag = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
		int k = getHoverState(flag);
		drawBorderedRect(xPosition, yPosition + 4, xPosition + width, yPosition + height - 4, 0xFF000000, 0x904D4D4D);
		drawCenteredSmallString(fontrenderer, xPosition + 8, yPosition + 22, displayString);
		mouseDragged(minecraft, i, j);
	}
	
	public void drawCenteredSmallString(FontRenderer font, int x, int y, String s) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawCenteredString(font, s, x * 2, y * 2, 0xFFFFFFFF);
		GL11.glScalef(2F, 2F, 2F);
	}

	protected void mouseDragged(Minecraft minecraft, int i, int j) {
	}

	public void mouseReleased(int i, int j)
	{
	}

	public boolean mousePressed(Minecraft minecraft, int i, int j)
	{
		return enabled && drawButton && i >= xPosition  && j >= yPosition + 1 && i < xPosition + width && j < yPosition + 15;
	}    


	/** Drawing functions */
	public void drawBorderedRect(int x, int y, int x1, int y1, int inside, int border)
	{
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		Methods.drawWBorderedRect(x * 2, y * 2, x1 * 2, y1 * 2, inside, border);
		GL11.glScalef(2f, 2f, 2f);
	}
}