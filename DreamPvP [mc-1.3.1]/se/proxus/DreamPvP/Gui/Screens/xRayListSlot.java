package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Mods.Base_Mod;
import se.proxus.DreamPvP.Utils.Placeholders.Key;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class xRayListSlot extends GuiSlot {

	private xRayList mList;
	private int selected;
	private int updateCounter, modKey, herp;
	private boolean keyTyped;

	public xRayListSlot(xRayList mList) {
		super(DreamPvP.mc, mList.width, mList.height, 32, mList.height - 59, 24);
		this.mList = mList;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return DreamPvP.settings.xrayArray.size();
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 24;
	}
	
	@Override
	protected void drawBackground() {
		mList.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		updateCounter++;
		herp = i;
		int var1 = DreamPvP.settings.xrayArray.get(i);
		
		DreamPvP.ingame.drawItem(j - 1, k + 1, var1);
		mList.drawString(DreamPvP.mc.fontRenderer, StringTranslate.getInstance().translateNamedKey(Item.itemsList[var1].getItemName()), j + 18, k + 1, 0xFFFFFFFF);
		mList.drawString(DreamPvP.mc.fontRenderer, "Block ID : " + var1, j + 18, k + 12, 0x80808080);
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
	
	protected int getCurId() {
		return DreamPvP.settings.xrayArray.get(getSelected());
	}
}