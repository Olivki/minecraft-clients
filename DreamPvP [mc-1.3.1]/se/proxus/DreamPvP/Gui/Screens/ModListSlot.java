package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Mods.Base_Mod;
import se.proxus.DreamPvP.Utils.Placeholders.Key;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class ModListSlot extends GuiSlot {

	private ModList mList;
	private int selected;
	private int updateCounter, modKey, herp;
	private boolean keyTyped;

	public ModListSlot(ModList mList) {
		super(DreamPvP.mc, mList.width, mList.height, 32, mList.height - 59, 36);
		this.mList = mList;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return DreamPvP.bModList.modArray.size();
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
		String var1 = DreamPvP.bModList.modArray.get(i).getName();
		String var2 = DreamPvP.bModList.modArray.get(i).getDesc();
		String var3 = "Enabled : " + DreamPvP.bModList.modArray.get(i).getState();

		mList.drawString(DreamPvP.mc.fontRenderer, var1, j, k + 1, 0xFFFFFFFF);
		mList.drawString(DreamPvP.mc.fontRenderer, var2, j, k + 12, 0x80808080);
		mList.drawString(DreamPvP.mc.fontRenderer, var3, j, k + 22, 0x30303030);
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
		return DreamPvP.bModList.modArray.get(getSelected()).getName();
	}
	
	protected Base_Mod getCurMod() {
		return (Base_Mod)DreamPvP.bModList.modArray.get(getSelected());
	}
}