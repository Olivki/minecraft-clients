package se.proxus.screens.list.slots;

import org.lwjgl.input.Keyboard;

import se.proxus.Pendrum;
import se.proxus.screens.list.screens.*;
import se.proxus.utils.placeholders.*;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class ScreenSlotBlocks extends GuiSlot {

	private ScreenBlocksManager guiScreen = null;

	private int selected = 0;

	public ScreenSlotBlocks(ScreenBlocksManager guiScreen) {
		super(Pendrum.wrapper.getMinecraft(), guiScreen.width, guiScreen.height, 32, guiScreen.height - 59, 24);
		this.guiScreen = guiScreen;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return Pendrum.sett.blockArray.size();
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
		int var5 = Pendrum.sett.blockArray.get(var0);
		
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), Pendrum.utils.blockIDToName(var5), var1 + 18, var2 + 1, 0xFFFFFFFF);
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), "" + var5, var1 + 18, var2 + 11, 0xFF808080);	
		Pendrum.methods.drawItem(var1 - 1, var2 + 1, var5);
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