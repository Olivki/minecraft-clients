package se.proxus.screens.list.screens;

import java.io.File;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiYesNo;
import se.proxus.screens.list.slots.ScreenSlotFriend;
import se.proxus.threads.ThreadLogin;

public class ScreenFriendManager extends GuiScreen {

	private ScreenSlotFriend guiSlot = null;

	public GuiScreen parentScreen = null;

	public GuiButton deleteFriendButton = null;

	public boolean deletePressed = false;

	public ScreenFriendManager(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		this.guiSlot = new ScreenSlotFriend(this);
		this.guiSlot.registerScrollButtons(this.controlList, 7, 8);
		
		this.deleteFriendButton = new GuiButton(1, this.width / 2 + 1, this.height - 48, 98, 20, "Remove");
		this.deleteFriendButton.enabled = this.guiSlot.getSelected() >= 0 && this.guiSlot.getSelected() < this.mc.pm.sett.friendArray.size();
		
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height - 48, 98, 20, "Add"));
		this.controlList.add(this.deleteFriendButton);
		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height - 26, "Done"));
	}

	@Override
	protected void actionPerformed(GuiButton var0) {
		switch(var0.id) {
		case 0:
			this.mc.displayGuiScreen(new ScreenAltManagerAdd(this.parentScreen));
			break;

		case 1:
			String var1 = this.mc.pm.sett.friendArray.get(guiSlot.getSelected());

			if(var1 != null) {
				this.deletePressed = true;

				String var2 = "Are you sure you want to delete this friend?";

				String var3 = "\"" + var1 + "\" will be lost forever! (A long time!)";

				String var4 = "Delete.";

				String var5 = "Cancel.";

				GuiYesNo var6 = new GuiYesNo(this, var2, var3, var4, var5, this.guiSlot.getSelected());

				this.mc.displayGuiScreen(var6);

				this.mc.pm.sett.saveBaseFile(new File(this.mc.pm.mainFolder, "Friends.cfg"), this.mc.pm.sett.friendArray, true);
			}
			break;

		case 2:
			try {
				ThreadLogin var7 = new ThreadLogin(this.guiSlot.getName(), this.guiSlot.getPass());
				var7.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 100:
			this.mc.displayGuiScreen(this.parentScreen);
			this.mc.pm.sett.saveBaseFile(new File(this.mc.pm.mainFolder, "Friends.cfg"), this.mc.pm.sett.friendArray, true);
			break;
		}

		this.guiSlot.actionPerformed(var0);
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		this.guiSlot.drawScreen(I1, I2, I3);

		this.drawCenteredString(this.fontRenderer, "Friend manager [" + String.valueOf(this.guiSlot.getSelected() + 1) + "/" + this.mc.pm.sett.friendArray.size() + "]", this.width / 2, 10, 0xFFFFFFFF);

		super.drawScreen(I1, I2, I3);
	}

	@Override
	public void confirmClicked(boolean flag, int i1) {
		if(this.deletePressed) {
			this.deletePressed = false;

			if(flag) {
				this.mc.pm.sett.friendArray.remove(this.mc.pm.sett.friendArray.indexOf(i1));
				this.mc.pm.sett.saveBaseFile(new File(this.mc.pm.mainFolder, "Friends.cfg"), this.mc.pm.sett.friendArray, true);
			}

			this.mc.displayGuiScreen(this);
		}
	}
}