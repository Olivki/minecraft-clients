package se.proxus.screens.list.slots;

import org.lwjgl.input.Keyboard;

import se.proxus.Pendrum;
import se.proxus.screens.list.screens.*;
import se.proxus.utils.placeholders.*;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class ScreenSlotAlt extends GuiSlot {

	private ScreenAltManager guiScreen = null;

	private int selected = 0;

	public ScreenSlotAlt(ScreenAltManager guiScreen) {
		super(Pendrum.wrapper.getMinecraft(), guiScreen.width, guiScreen.height, 32, guiScreen.height - 59, 32);
		this.guiScreen = guiScreen;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return Pendrum.sett.altArray.size();
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 32;
	}

	@Override
	protected void drawBackground() {
		this.guiScreen.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var0, int var1, int var2, int var3, Tessellator var4) {
		PlaceholderAlt var5 = Pendrum.sett.altArray.get(var0);
		
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), var5.username, var1 + 26, var2 + 1, 0xFFFFFFFF);
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), Pendrum.utils.appendText(var5.password, "*"), var1 + 26, var2 + 11, 0xFF808080);	
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), "" + var5.offline, var1 + 26, var2 + (var5.offline ? 11 : 20), 0xFF606060);
		
		Pendrum.methods.drawPlayerHead(var1 + 1, var2 + 2.5F, var5.username);
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

	public String getNameAndPass() {
		PlaceholderAlt var0 = Pendrum.sett.altArray.get(this.getSelected());

		return var0.username + ":" + var0.password;
	}

	public String getName() {
		PlaceholderAlt var0 = Pendrum.sett.altArray.get(this.getSelected());

		return var0.username;
	}

	public String getPass() {
		PlaceholderAlt alt = Pendrum.sett.altArray.get(getSelected());

		return alt.password;
	}
}