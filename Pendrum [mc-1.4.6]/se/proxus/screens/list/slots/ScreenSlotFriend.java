package se.proxus.screens.list.slots;

import org.lwjgl.input.Keyboard;

import se.proxus.Pendrum;
import se.proxus.screens.list.screens.*;
import se.proxus.utils.placeholders.*;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class ScreenSlotFriend extends GuiSlot {

	private ScreenFriendManager guiScreen = null;

	private int selected = 0;

	public ScreenSlotFriend(ScreenFriendManager guiScreen) {
		super(Pendrum.wrapper.getMinecraft(), guiScreen.width, guiScreen.height, 32, guiScreen.height - 59, 28);
		this.guiScreen = guiScreen;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return Pendrum.sett.friendArray.size();
	}

	@Override
	protected int getContentHeight() {
		return this.getSize() * 28;
	}

	@Override
	protected void drawBackground() {
		this.guiScreen.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int var0, int var1, int var2, int var3, Tessellator var4) {
		String var5 = Pendrum.sett.friendArray.get(var0);
		
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), var5, var1 + 26, var2 + 4, 0xFFFFFFFF);
		this.guiScreen.drawString(Pendrum.wrapper.getFontRenderer(), "Same server: " + Pendrum.utils.isPlayerOnSameServer(var5), var1 + 26, var2 + 14, 0xFF808080);	
		
		Pendrum.methods.drawPlayerHead(var1 + 1, var2, var5);
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