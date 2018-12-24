package se.proxus.DreamPvP.Gui.Screens;

import java.io.File;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiYesNo;
import net.minecraft.src.Item;
import net.minecraft.src.StringTranslate;

public class ItemList extends GuiScreen {

	private GuiButton give;
	private ItemListSlot mSlot;

	public boolean deletePressed = false, focused = false;

	public GuiButton xrayAdd, chat, friend;

	@Override
	public void initGui() {
		mSlot = new ItemListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);
		
		give = new GuiButton(0, width / 2 - 100, height - 48, "Give yourself " + mSlot.getCurName() + " [" + mSlot.getCurID() + "].");
		
		controlList.clear();
		controlList.add(give);
		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Done."));
	}
	
	@Override
	public void updateScreen() {
		give = new GuiButton(0, width / 2 - 100, height - 48, "Give yourself " + mSlot.getCurName() + " [" + mSlot.getCurID() + "].");
		
		controlList.clear();
		controlList.add(give);
		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Done."));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				mc.dp.utils.sendChat("/give " + mc.session.username + " " + mSlot.getCurID() + " 64");
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
				mc.displayGuiScreen(new Main_2());
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "Items list. [§e" + mc.dp.settings.itemArray.size() + "§r]", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
}