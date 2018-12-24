package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Utils.Placeholders.Key;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class MacroListSlot extends GuiSlot {

	private MacroList mList;
	private int selected;
	private int updateCounter, modKey, herp;
	private boolean keyTyped;

	public MacroListSlot(MacroList mList) {
		super(DreamPvP.mc, mList.width, mList.height, 32, mList.height - 59, 36);
		this.mList = mList;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return DreamPvP.settings.macroArray.size();
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 36;
	}

	@Override
	protected void drawBackground() {
		mList.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		updateCounter++;
		herp = i;
		Key key = DreamPvP.settings.macroArray.get(i);

		mList.drawString(DreamPvP.mc.fontRenderer, key.getName(), j, k + 1, 0xFFFFFFFF);
		mList.drawString(DreamPvP.mc.fontRenderer, "Command : " + key.getCommand(), j, k + 12, 0x80808080);
		mList.drawString(DreamPvP.mc.fontRenderer, "Key : " + Keyboard.getKeyName(key.getKey()), j, k + 22, 0x30303030);
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
}