package se.proxus.screens.list.slots;

import org.lwjgl.input.Keyboard;

import se.proxus.Pendrum;
import se.proxus.screens.list.screens.*;
import se.proxus.utils.ArrayHelper;
import se.proxus.utils.placeholders.*;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class ScreenSlotCredits extends GuiSlot {

	private ScreenCredits guiScreen = null;

	private int selected = 0;

	public ScreenSlotCredits(ScreenCredits guiScreen) {
		super(Pendrum.wrapper.getMinecraft(), guiScreen.width, guiScreen.height, 32, guiScreen.height - 59, 24);
		this.guiScreen = guiScreen;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return Pendrum.sett.creditsArray.size();
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 24;
	}

	@Override
	protected void drawBackground() {
		this.guiScreen.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var0, int var1, int var2, int var3, Tessellator var4) {
		ArrayHelper<String, String> var5 = Pendrum.sett.creditsArray.get(var0);

		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), (String)var5.obj0, var1 + 2, var2 + 1, 0xFFFFFFFF);
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), (String)var5.obj1, var1 + 2, var2 + 11, 0xFF808080);	
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