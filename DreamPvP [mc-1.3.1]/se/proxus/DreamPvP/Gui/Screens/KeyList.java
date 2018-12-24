package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import net.minecraft.src.*;

public class KeyList extends GuiScreen {

	private KeyListSlot mSlot;
	
	public boolean deletePressed = false, focused = false;

	@Override
	public void initGui() {
		mSlot = new KeyListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);
		
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height - 48, 98, 20, focused ? ">>_<<" : mSlot.getKey()));
		controlList.add(new GuiButton(1, width / 2 + 2, height - 48, 98, 20, "Unbind key."));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Done."));
	}
	
	@Override
	public void updateScreen() {
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height - 48, 98, 20, focused ? ">>_<<" : mSlot.getKey()));
		controlList.add(new GuiButton(1, width / 2 + 2, height - 48, 98, 20, "Unbind key."));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Done."));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				focused = true;
			} else if(button.id < 2) {
				focused = false;
			} if(button.id == 1) {				
				mSlot.getSelectedKey().setKey(Keyboard.KEY_NONE);
				DreamPvP.files.saveKeys();
			} if(button.id == 2 && !focused) {
				DreamPvP.mc.displayGuiScreen(new Main());
				DreamPvP.files.saveKeys();
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "Key list.", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
	
	@Override
	public void keyTyped(char i1, int i2) {
		super.keyTyped(i1, i2);
		
		if(focused && i2 != Keyboard.KEY_ESCAPE) {
			mSlot.getSelectedKey().setKey(i2);
			focused = false;
			DreamPvP.files.saveKeys();
		}
	}
}