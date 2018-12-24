package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import net.minecraft.src.*;

public class ModList extends GuiScreen {

	private ModListSlot mSlot;

	@Override
	public void initGui() {	
		mSlot = new ModListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);
		
		controlList.clear();
		controlList.add(new GuiButton(2, width / 2 - 100, height - 48, "Toggle " + mSlot.getCurName() + "."));
		controlList.add(new GuiButton(3, width / 2 - 100, height - 26, "Done."));
	}
	
	@Override
	public void updateScreen() {
		controlList.clear();
		controlList.add(new GuiButton(2, width / 2 - 100, height - 48, "Toggle " + mSlot.getCurName() + "."));
		controlList.add(new GuiButton(3, width / 2 - 100, height - 26, "Done."));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				
			} if(button.id == 1) {
				
			} if(button.id == 2) {
				mSlot.getCurMod().toggle();
			} if(button.id == 3) {
				mc.displayGuiScreen(new Main_2());
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "Mod list. [§e" + mc.dp.bModList.modArray.size() + "§r]", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
}