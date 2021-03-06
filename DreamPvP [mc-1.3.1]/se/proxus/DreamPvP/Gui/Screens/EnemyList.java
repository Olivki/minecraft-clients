package se.proxus.DreamPvP.Gui.Screens;

import java.io.File;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import net.minecraft.src.*;

public class EnemyList extends GuiScreen {

	private EnemyListSlot mSlot;
	
	public boolean deletePressed = false, focused = false;
	
	public GuiButton join, chat, friend;

	@Override
	public void initGui() {
		controlList.add(new GuiButton(0, width / 2 - 100, height - 48, 98, 20, "Add enemy"));
		controlList.add(new GuiButton(1, width / 2 + 2, height - 48, 98, 20, "Delete enemy"));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 26, "Done."));
		
		mSlot = new EnemyListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				mc.displayGuiScreen(new EnemyListAdd());
				mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "Enemy.txt"), mc.dp.settings.enemyArray, true);
			} if(button.id == 1) {
				String s = mc.dp.settings.friendArray.get(mSlot.getSelected());

				if (s != null) {
					deletePressed = true;
					StringTranslate stringtranslate = StringTranslate.getInstance();
					String s1 = "Are you sure you want to delete this enemy?";
					String s2 = "\"" + s + "\" will be lost forever! (A long time!)";
					String s3 = "Delete.";
					String s4 = "Cancel.";
					GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, mSlot.getSelected());
					mc.displayGuiScreen(guiyesno);
					mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "Enemy.txt"), mc.dp.settings.enemyArray, true);
				}
			} if(button.id == 2) {
				mc.displayGuiScreen(new Main());
				mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "Enemy.txt"), mc.dp.settings.enemyArray, true);
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "Enemy list. [�e" + mc.dp.settings.enemyArray.size() + "�r]", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
	
	@Override
	public void confirmClicked(boolean flag, int i1) {
		if(deletePressed) {
			deletePressed = false;

			if(flag) {
				mc.dp.settings.friendArray.remove(i1);
				mc.dp.files.saveBaseFile(new File(mc.dp.files.baseFolder, "Enemy.txt"), mc.dp.settings.enemyArray, true);
			}

			mc.displayGuiScreen(this);
		}
	}
}