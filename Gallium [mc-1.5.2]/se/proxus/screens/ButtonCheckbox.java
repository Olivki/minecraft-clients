package se.proxus.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

public class ButtonCheckbox extends GuiButton {

    public ButtonCheckbox(final int par1, final int par2, final int par3,
	    final String par4Str) {
	super(par1, par2, par3, 10, 11, par4Str);
    }

    public ButtonCheckbox(final int par1, final int par2, final int par3,
	    final int par4, final int par5, final String par6Str) {
	super(par1, par2, par3, par4, par5, par6Str);
    }

    @Override
    public void drawButton(final Minecraft par1Minecraft, final int par2,
	    final int par3) {
	FontRenderer var4 = par1Minecraft.fontRenderer;
	drawRect(xPosition, yPosition, xPosition + width, yPosition + height,
		-6250336);
	drawRect(xPosition + 1, yPosition + 1, xPosition + width - 1, yPosition
		+ height - 1, -16777216);
	if (enabled) {
	    GL11.glPushMatrix();
	    GL11.glScaled(2.0D, 2.0D, 2.0D);
	    var4.drawStringWithShadow("\u2713", xPosition / 2 + (width - 13)
		    / 4, yPosition / 2 + (height - 24) / 4, 0xFFFFFFFF);
	    GL11.glPopMatrix();
	}
	var4.drawStringWithShadow(displayString, xPosition + width + 2,
		yPosition + 2, 0xFFFFFFFF);
    }

    @Override
    public boolean mousePressed(final Minecraft par1Minecraft, final int par2,
	    final int par3) {
	return this.drawButton && par2 >= this.xPosition
		&& par3 >= this.yPosition && par2 < this.xPosition + this.width
		&& par3 < this.yPosition + this.height;
    }
}