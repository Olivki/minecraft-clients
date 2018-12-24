package se.proxus.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import se.proxus.tools.Colours;

public class Button extends GuiButton {

    private final float[] rgbHolder = new float[3];

    public Button(final int par1, final int par2, final int par3,
	    final String par4Str) {
	super(par1, par2, par3, 200, 20, par4Str);
    }

    public Button(final int par1, final int par2, final int par3,
	    final int par4, final int par5, final String par6Str) {
	super(par1, par2, par3, par4, par5, par6Str);
    }

    @Override
    public void drawButton(final Minecraft par1Minecraft, final int par2,
	    final int par3) {
	FontRenderer var4 = par1Minecraft.fontRenderer;
	int colour = 0x40000000;
	this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition
		&& par2 < this.xPosition + this.width
		&& par3 < this.yPosition + this.height;
	float red = rgbHolder[0];
	float green = rgbHolder[1];
	float blue = rgbHolder[2];
	if (field_82253_i) {
	    if (red <= 1)
		rgbHolder[0] += 5.0F;
	    if (green <= 125)
		rgbHolder[1] += 5.0F;
	    if (blue <= 1)
		rgbHolder[2] += 5.0F;
	} else if (!field_82253_i) {
	    if (red >= 6)
		rgbHolder[0] -= 5.0F;
	    if (green >= 6)
		rgbHolder[1] -= 5.0F;
	    if (blue >= 6)
		rgbHolder[2] -= 5.0F;
	}
	colour = Colours.getHEX(red, green, blue, 0.4F);
	drawRect(xPosition, yPosition, xPosition + width, yPosition + height,
		colour);
	int var6 = 0xFFFFFFFF;
	Minecraft.getMinecraft().dp.getFontFactory().drawCenteredSizedString(
		displayString.toUpperCase(), xPosition + width / 2,
		yPosition + (height - 12.5F) / 2, var6, 4, 2.0F, true);
    }

    @Override
    public boolean mousePressed(final Minecraft par1Minecraft, final int par2,
	    final int par3) {
	return this.drawButton && par2 >= this.xPosition
		&& par3 >= this.yPosition && par2 < this.xPosition + this.width
		&& par3 < this.yPosition + this.height;
    }
}