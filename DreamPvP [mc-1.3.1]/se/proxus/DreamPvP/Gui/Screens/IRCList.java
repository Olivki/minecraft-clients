package se.proxus.DreamPvP.Gui.Screens;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;
import net.minecraft.src.*;

public class IRCList extends GuiScreen {

	private IRCListSlot mSlot;
	
	public boolean deletePressed = false, focused = false;
	
	public GuiButton join, chat, friend;

	@Override
	public void initGui() {
		join = new GuiButton(0, width / 2 - 100, height - 48, 48, 20, "Join");
		chat = new GuiButton(1, width / 2 - 49, height - 48, 48, 20, "Chat");
		friend = new GuiButton(2, width / 2 + 2, height - 48, 98, 20, "Add as friend.");
		
		mSlot = new IRCListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);
		
		join.enabled = mc.dp.utils.isFriend(mSlot.getCurName());
		
		controlList.clear();
		controlList.add(join);
		controlList.add(chat);
		controlList.add(friend);
		
		controlList.add(new GuiButton(3, width / 2 - 100, height - 26, "Done."));
	}
	
	@Override
	public void updateScreen() {
		controlList.clear();
		/*join.enabled = mc.dp.utils.isIRCFriend(mSlot.getCurName());
		chat.enabled = !mSlot.getCurName().equalsIgnoreCase(mc.session.username);
		friend.enabled = !mSlot.getCurName().equalsIgnoreCase(mc.session.username);*/
		
		join.enabled = false;
		chat.enabled = false;
		friend.enabled = false;
		
		controlList.add(join);
		controlList.add(chat);
		controlList.add(friend);
		
		controlList.add(new GuiButton(3, width / 2 - 100, height - 26, "Done."));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				
			} if(button.id == 1) {
				mc.displayGuiScreen(new Base_IRCChat(mSlot.getCurName()));
				mc.dp.settings.curIRCCHatName = mSlot.getCurName();
			} if(button.id == 2) {
				
			} if(button.id == 3) {
				mc.displayGuiScreen(new Main());
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "IRC list. [§e" + mc.dp.settings.ircArray.size() + "§r]", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
}