package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Utils.Placeholders.Key;
import se.proxus.DreamPvP.Utils.Placeholders.u_Alt;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class AltListSlot extends GuiSlot {

	private AltList mList;
	private int selected;
	private int updateCounter, modKey, herp;
	private boolean keyTyped;

	public AltListSlot(AltList mList) {
		super(DreamPvP.mc, mList.width, mList.height, 32, mList.height - 59, 24);
		this.mList = mList;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return DreamPvP.settings.altArray.size();
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
		u_Alt alt = DreamPvP.settings.altArray.get(i);

		mList.drawString(DreamPvP.mc.fontRenderer, alt.name, j, k + 1, 0xFFFFFFFF);
		mList.drawString(DreamPvP.mc.fontRenderer, DreamPvP.utils.replaceStar(alt.password), j, k + 12, 0x80808080);
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
	
	protected String getNameAndPass() {
		u_Alt alt = DreamPvP.settings.altArray.get(getSelected());
		
		return alt.name + ":" + alt.password;
	}
	
	protected String getName() {
		u_Alt alt = DreamPvP.settings.altArray.get(getSelected());
		
		return alt.name;
	}
	
	protected String getPass() {
		u_Alt alt = DreamPvP.settings.altArray.get(getSelected());
		
		return alt.password;
	}
}