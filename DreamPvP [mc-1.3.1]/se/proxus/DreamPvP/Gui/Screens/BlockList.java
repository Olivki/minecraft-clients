package se.proxus.DreamPvP.Gui.Screens;

import java.io.File;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiYesNo;
import net.minecraft.src.Item;
import net.minecraft.src.StringTranslate;

public class BlockList extends GuiScreen {

	private BlockListSlot mSlot;

	public boolean deletePressed = false, focused = false;

	public GuiButton xrayAdd, chat, friend;

	@Override
	public void initGui() {
		mSlot = new BlockListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);

		//int var1 = mc.dp.settings.xrayArray.get(mSlot.getSelected());
		xrayAdd = new GuiButton(0, width / 2 - 100, height - 48, "Add to xRay");

		xrayAdd.enabled = !mSlot.xrayContains();

		controlList.add(xrayAdd);
		//controlList.add(new GuiButton(1, width / 2 + 2, height - 48, 98, 20, "Delete block"));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Cancel."));
	}

	@Override
	public void updateScreen() {
		controlList.clear();
		//int var1 = mc.dp.settings.xrayArray.get(mSlot.getSelected());
		xrayAdd = new GuiButton(0, width / 2 - 100, height - 48, "Add to xRay");

		xrayAdd.enabled = !mSlot.xrayContains();

		controlList.add(xrayAdd);
		//controlList.add(new GuiButton(1, width / 2 + 2, height - 48, 98, 20, "Delete block"));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Cancel."));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				if(!mSlot.xrayContains()) {
					//mc.dp.settings.xrayArray.add(mSlot.getCurBlock().blockID);
					//mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "xRay.txt"), mc.dp.settings.xrayArray, false);
					mc.dp.utils.sendChat(".xray add " + mSlot.getCurBlock().blockID);
					mc.displayGuiScreen(new xRayList());
				}
			} if(button.id == 1) {
				/*int var1 = mc.dp.settings.xrayArray.get(mSlot.getSelected());
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
				}*/
			} if(button.id == 2) {
				mc.displayGuiScreen(new xRayList());
				mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "xRay.txt"), mc.dp.settings.xrayArray, false);
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "Block list. [§e" + mc.dp.settings.blocksArray.size() + "§r]", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
}