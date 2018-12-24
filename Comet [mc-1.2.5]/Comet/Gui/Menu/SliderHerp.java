package Comet.Gui.Menu;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class SliderHerp extends BaseSliderHerp
{
	public float sliderValue;
	public boolean dragging;

	public SliderHerp(int i, int j, int k, String s, float f)
	{
		super(i, j, k, 102, 16, s);
		dragging = false;
		sliderValue = f;
		displayString = s;
	}

	protected int getHoverState(boolean flag)
	{
		return 0;
	}

	protected void mouseDragged(Minecraft minecraft, int i, int j)
	{
		if (!drawButton)
		{
			return;
		}
		if (dragging)
		{
			sliderValue = (float)(i - (xPosition + 1)) / (float)(width - 10);
			if (sliderValue < 0.0F)
			{
				sliderValue = 0.0F;
			}
			if (sliderValue > 1.0F)
			{
				sliderValue = 1.0F;
			}
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawBorderedRect(xPosition + (int)(sliderValue * (float)(width - 8)) + 1, yPosition + 1, xPosition + (int)(sliderValue * (float)(width - 8)) + 7, yPosition + 15, 0xFF000000, 0xFF4D4D4D);
	}

	public boolean mousePressed(Minecraft minecraft, int i, int j) {
		if (super.mousePressed(minecraft, i, j))
		{
			sliderValue = (float)(i - (xPosition + 4 )) / (float)(width - 10);
			if (sliderValue < 0.0F)
			{
				sliderValue = 0.0F;
			}
			if (sliderValue > 1.0F)
			{
				sliderValue = 1.0F;
			}
			dragging = true;
			return true;
		}
		else
		{
			return false;
		}
	}

	public void mouseReleased(int i, int j)
	{
		dragging = false;
	}
}