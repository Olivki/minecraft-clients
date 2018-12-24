package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Mods.Base_Mod;
import se.proxus.DreamPvP.Utils.Placeholders.Key;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class FriendListSlot extends GuiSlot {

	private FriendList mList;
	private int selected;
	private int updateCounter, modKey, herp;
	private boolean keyTyped;

	public FriendListSlot(FriendList mList) {
		super(DreamPvP.mc, mList.width, mList.height, 32, mList.height - 59, 14);
		this.mList = mList;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return DreamPvP.settings.friendArray.size();
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 14;
	}

	@Override
	protected void drawBackground() {
		mList.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		updateCounter++;
		herp = i;
		String s = (String)DreamPvP.settings.friendArray.get(i);

		mList.drawString(DreamPvP.mc.fontRenderer, "§f" + s + "§r", j, k + 1, 0xFFFFFFFF);
	}

	@Override
	protected void elementClicked(int i, boolean flag) {
		selected = i;
		keyTyped = false;
	}

	@Override
	protected boolean isSelected(int i) {
		return selected == i;
	}

	protected int getSelected() {
		return selected;
	}
	
	protected String getCurName() {
		String s = (String)DreamPvP.settings.friendArray.get(getSelected());
		
		return s;
	}
}