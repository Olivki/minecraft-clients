package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Mods.Base_Mod;
import se.proxus.DreamPvP.Utils.Placeholders.Key;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class BlockListSlot extends GuiSlot {

	private BlockList mList;
	private int selected;
	private int updateCounter, modKey, herp;
	private boolean keyTyped;

	public BlockListSlot(BlockList mList) {
		super(DreamPvP.mc, mList.width, mList.height, 32, mList.height - 59, 24);
		this.mList = mList;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return DreamPvP.settings.blocksArray.size();
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

		Block block = (Block)DreamPvP.settings.blocksArray.get(i);
		
		String var2 = StringTranslate.getInstance().translateNamedKey(Item.itemsList[block.blockID].getItemName());
		//boolean var3 = DreamPvP.settings.xrayArray.contains(var1);

		DreamPvP.ingame.drawItem(j - 1, k + 1, block.blockID);
		mList.drawString(DreamPvP.mc.fontRenderer, var2, j + 18, k + 1, 0xFFFFFFFF);
		mList.drawString(DreamPvP.mc.fontRenderer, "Block ID : " + block.blockID, j + 18, k + 12, 0x80808080);
	}

	@Override
	protected void elementClicked(int i, boolean flag) {
		selected = i;
	}

	@Override
	protected boolean isSelected(int i) {
		return selected == i;
	}

	protected int getSelected() {
		return selected;
	}

	protected Block getCurBlock() {
		return DreamPvP.settings.blocksArray.get(getSelected());
	}
	
	protected boolean xrayContains() {
		return DreamPvP.settings.xrayArray.contains(getCurBlock().blockID);
	}
}