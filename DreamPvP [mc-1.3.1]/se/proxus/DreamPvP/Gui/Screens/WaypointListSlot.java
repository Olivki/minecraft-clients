package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Utils.Placeholders.Key;
import se.proxus.DreamPvP.Utils.Placeholders.u_Waypoint;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class WaypointListSlot extends GuiSlot {

	private WaypointList mList;
	private int selected;
	private int updateCounter, modKey, herp;
	private boolean keyTyped;

	public WaypointListSlot(WaypointList mList) {
		super(DreamPvP.mc, mList.width, mList.height, 32, mList.height - 59, 44);
		this.mList = mList;
		this.selected = 0;
	}

	@Override
	protected int getSize() {
		return DreamPvP.settings.waypointArray.size();
	}

	@Override
	protected int getContentHeight() {
		return getSize() * 44;
	}

	@Override
	protected void drawBackground() {
		mList.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		updateCounter++;
		herp = i;
		u_Waypoint wt = DreamPvP.settings.waypointArray.get(i);
		
		mList.drawString(DreamPvP.mc.fontRenderer, wt.name, j, k + 1, 0xFFFFFFFF);
		mList.drawString(DreamPvP.mc.fontRenderer, "Pos : " + wt.getPos(), j, k + 12, 0x80808080);
		mList.drawString(DreamPvP.mc.fontRenderer, "RGB : " + wt.getRGB(), j, k + 22, 0x50505050);
		mList.drawString(DreamPvP.mc.fontRenderer, "IP : " + wt.ip, j, k + 32, 0x20202020);
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