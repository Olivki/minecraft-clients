package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Utils.Utils;
import se.proxus.DreamPvP.Utils.WordWrapping;
import se.proxus.DreamPvP.Utils.Placeholders.Key;
import se.proxus.DreamPvP.Utils.Placeholders.u_Alt;
import se.proxus.DreamPvP.Utils.Placeholders.u_IRCMessage;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class IRCChatListSlot extends GuiSlot {

	private IRCChatList mList;
	private int selected;
	private int updateCounter, modKey, herp;
	private boolean keyTyped;

	public IRCChatListSlot(IRCChatList mList) {
		super(DreamPvP.mc, mList.width, mList.height, 32, mList.height - 59, 24);
		this.mList = mList;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return DreamPvP.settings.ircMessageArray.size();
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 26;
	}

	@Override
	protected void drawBackground() {
		mList.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		updateCounter++;
		herp = i;
		String var5 = "";
		u_IRCMessage msgs = DreamPvP.settings.ircMessageArray.get(i);
		
		mList.drawString(DreamPvP.mc.fontRenderer, msgs.getMessage(), 2, k, 0xFFFFFFFF);
	}

	@Override
	protected void elementClicked(int i, boolean flag) {
		selected = i;
		keyTyped = false;
	}

	@Override
	protected boolean isSelected(int i) {
		return false;
	}
	
	protected int getSelected() {
		return selected;
	}
}