package se.proxus.screens.list.slots;

import org.lwjgl.input.Keyboard;

import se.proxus.Pendrum;
import se.proxus.screens.list.screens.*;
import se.proxus.utils.placeholders.*;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class ScreenSlotBlocksList extends GuiSlot {

	private ScreenBlocksList guiScreen = null;

	private int selected = 0;

	public ScreenSlotBlocksList(ScreenBlocksList guiScreen) {
		super(Pendrum.wrapper.getMinecraft(), guiScreen.width, guiScreen.height, 32, guiScreen.height - 59, 24);
		this.guiScreen = guiScreen;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return Pendrum.sett.defBlockArray.size();
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
		int var5 = 146;

		try {
			var5 = Pendrum.sett.defSearchBlockArray.get(var0).blockID;
		} catch(Exception e) {

		}

		try {
			if(var5 != 146) {
				this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), Pendrum.utils.blockIDToName(var5), var1 + 18, var2 + 1, 0xFFFFFFFF);
				this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), "" + var5, var1 + 18, var2 + 11, 0xFF808080);	
				Pendrum.methods.drawItem(var1 - 1, var2 + 1, var5);
			}
		} catch(Exception e) {

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