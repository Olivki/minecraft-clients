package se.proxus.screens.list.slots;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import se.proxus.Pendrum;
import se.proxus.mods.BaseMod;
import se.proxus.screens.list.screens.*;
import se.proxus.utils.ArrayHelper;
import se.proxus.utils.placeholders.*;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class ScreenSlotMod extends GuiSlot {

	private ScreenModManager guiScreen = null;

	private int selected = 0;

	public ScreenSlotMod(ScreenModManager guiScreen) {
		super(Pendrum.wrapper.getMinecraft(), guiScreen.width, guiScreen.height, 32, guiScreen.height - 59, 54);
		this.guiScreen = guiScreen;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return Pendrum.mods.modArray.size();
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 54;
	}

	@Override
	protected void drawBackground() {
		this.guiScreen.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var0, int var1, int var2, int var3, Tessellator var4) {
		BaseMod var5 = Pendrum.mods.modArray.get(var0);

		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), var5.getName().replace("_", " "), var1 + 2, var2 + 1, 0xFFFFFFFF);
		
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), "Key: " + var5.getInfo().getKey(), var1 + 2, var2 + 11, 0xFF808080);
		
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), "State: " + var5.getState(), var1 + 2, var2 + 21, 0xFF606060);
		
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), "Type: " + var5.getType().getName(), var1 + 2, var2 + 31, 0xFF404040);
		
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), var5.getType().getColor() + "Colour", var1 + 2, var2 + 41, 0xFF202020);

		if(this.mouseX >= var1 && this.mouseY >= var2 - 3 && this.mouseX <= var1 + 215 && this.mouseY <= var2 + 50) {
			GL11.glPushMatrix();
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			Pendrum.methods.drawRect(this.mouseX * 2 + 12, this.mouseY * 2 + 3, 
					this.mouseX * 2 + 13 + Pendrum.wrapper.getFontRenderer().getStringWidth(var5.getInfo().getDescription()) + 2, 
					this.mouseY * 2 + 15, 0x60000000);
			this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), var5.getInfo().getDescription(), this.mouseX * 2 + 14, this.mouseY * 2 + 5, 0xFFFFFFFF);	
			GL11.glPopMatrix();
		}
	}

	@Override
	protected void elementClicked(int var0, boolean var1) {
		this.selected = var0;
	}

	@Override
	protected boolean isSelected(int var0) {
		return this.selected == var0;
	}

	public int getSelected() {
		return this.selected;
	}
}