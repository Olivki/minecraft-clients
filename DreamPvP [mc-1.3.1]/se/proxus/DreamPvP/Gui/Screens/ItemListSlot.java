package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Mods.Base_Mod;
import se.proxus.DreamPvP.Utils.Placeholders.Key;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class ItemListSlot extends GuiSlot {

	private ItemList mList;
	private int selected;
	private int updateCounter, modKey, herp;
	private boolean keyTyped;

	public ItemListSlot(ItemList mList) {
		super(DreamPvP.mc, mList.width, mList.height, 32, mList.height - 59, 24);
		this.mList = mList;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return DreamPvP.settings.itemArray.size();
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

		Block block = null;
		Item item = null;

		if(DreamPvP.settings.itemArray.get(i) instanceof Block) {
			block = (Block)DreamPvP.settings.itemArray.get(i);
		}

		if(DreamPvP.settings.itemArray.get(i) instanceof Item) {
			item = (Item)DreamPvP.settings.itemArray.get(i);
		}
		
		int var3 = 1;
		
		if(DreamPvP.settings.itemArray.get(i) instanceof Block) {
			var3 = block.blockID;
		}
		
		if(DreamPvP.settings.itemArray.get(i) instanceof Item) {
			var3 = item.shiftedIndex;
		}
		
		String var2 = "";

		if(DreamPvP.settings.itemArray.get(i) instanceof Block) {
			var2 = StringTranslate.getInstance().translateNamedKey(Item.itemsList[var3].getItemName());
		}
		
		if(DreamPvP.settings.itemArray.get(i) instanceof Item) {
			var2 = StringTranslate.getInstance().translateNamedKey(Item.itemsList[var3].getItemName());
		}

		DreamPvP.ingame.drawItem(j - 1, k + 1, var3);
		mList.drawString(DreamPvP.mc.fontRenderer, var2, j + 18, k + 1, 0xFFFFFFFF);
		mList.drawString(DreamPvP.mc.fontRenderer, "Item ID : " + var3, j + 18, k + 12, 0x80808080);
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
	
	protected int getCurID() {
		Block block = null;
		Item item = null;
		int var1 = 1;
		
		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Block) {
			block = (Block)DreamPvP.settings.itemArray.get(getSelected());
		}

		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Item) {
			item = (Item)DreamPvP.settings.itemArray.get(getSelected());
		}
		
		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Block) {
			var1 = block.blockID;
		}
		
		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Item) {
			var1 = item.shiftedIndex;
		}
		
		return var1;
	}
	
	protected String getCurName() {
		Block block = null;
		Item item = null;
		String var1 = "";
		
		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Block) {
			block = (Block)DreamPvP.settings.itemArray.get(getSelected());
		}

		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Item) {
			item = (Item)DreamPvP.settings.itemArray.get(getSelected());
		}
		
		int var3 = 1;
		
		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Block) {
			var3 = block.blockID;
		}
		
		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Item) {
			var3 = item.shiftedIndex;
		}
		
		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Block) {
			var1 = StringTranslate.getInstance().translateNamedKey(Item.itemsList[var3].getItemName());
		}
		
		if(DreamPvP.settings.itemArray.get(getSelected()) instanceof Item) {
			var1 = StringTranslate.getInstance().translateNamedKey(Item.itemsList[var3].getItemName());
		}
		
		return var1;
	}
}