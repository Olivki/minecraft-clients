package se.proxus.DreamPvP.Gui.Screens;

import java.io.File;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import net.minecraft.src.*;

public class xRayList extends GuiScreen {

	private xRayListSlot mSlot;
	
	public boolean deletePressed = false, focused = false;
	
	public GuiButton join, chat, friend;

	@Override
	public void initGui() {
		controlList.add(new GuiButton(0, width / 2 - 100, height - 48, 98, 20, "Block list"));
		controlList.add(new GuiButton(1, width / 2 + 2, height - 48, 98, 20, "Delete block"));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Done."));
		
		mSlot = new xRayListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				mc.displayGuiScreen(new BlockList());
				mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "xRay.txt"), mc.dp.settings.xrayArray, false);
			} if(button.id == 1) {
				int var1 = mc.dp.settings.xrayArray.get(mSlot.getSelected());
				String id = StringTranslate.getInstance().translateNamedKey(Item.itemsList[var1].getItemName());

				if (id != null) {
					deletePressed = true;
					StringTranslate stringtranslate = StringTranslate.getInstance();
					String s1 = "Are you sure you want to delete this friend?";
					String s2 = "\"" + id + "\" will be lost forever! (A long time!)";
					String s3 = "Delete.";
					String s4 = "Cancel.";
					GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, mSlot.getSelected());
					mc.displayGuiScreen(guiyesno);
					mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "xRay.txt"), mc.dp.settings.xrayArray, false);
				}
			} if(button.id == 2) {
				mc.displayGuiScreen(new Main());
				mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "xRay.txt"), mc.dp.settings.xrayArray, false);
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "xRay list. [§e" + mc.dp.settings.xrayArray.size() + "§r]", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
	
	@Override
	public void confirmClicked(boolean flag, int i1) {
		if(deletePressed) {
			deletePressed = false;

			if(flag) {
				//mc.dp.settings.xrayArray.remove(mc.dp.settings.xrayArray.indexOf(i1));
				//mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "xRay.txt"), mc.dp.settings.xrayArray, false);
				mc.dp.utils.sendChat(".xray del " + mSlot.getCurId());
			}

			mc.displayGuiScreen(this);
		}
	}
}