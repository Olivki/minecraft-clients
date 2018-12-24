package se.proxus.DreamPvP.Gui.Screens;

import net.minecraft.src.*;

public class Main extends GuiScreen {
	
	public GuiButton irc;
	public GuiScreen parentScreen;
	
	public Main(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}
	
	public Main() {
		
	}

	@Override
	public void initGui() {
		irc = new GuiButton(4, width / 2 - 100, height / 4 + 68, 98, 20, "IRC list.");
		
		irc.enabled = mc.dp.main.bot.isConnected();
		
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 24, 98, 20, "Macro list."));
		controlList.add(new GuiButton(1, width / 2 + 2, height / 4 + 24, 98, 20, "Settings"));
		controlList.add(new GuiButton(2, width / 2 - 100, height / 4 + 46, 98, 20, "Waypoint list."));
		controlList.add(new GuiButton(3, width / 2 + 2, height / 4 + 46, 98, 20, "Key list."));
		controlList.add(irc);
		controlList.add(new GuiButton(5, width / 2 + 2, height / 4 + 68, 98, 20, "Alt list."));
		controlList.add(new GuiButton(6, width / 2 - 100, height / 4 + 90, 98, 20, "Friend list."));
		controlList.add(new GuiButton(7, width / 2 + 2, height / 4 + 90, 98, 20, "Enemy list."));
		controlList.add(new GuiButton(8, width / 2 - 100, height / 4 + 112, 98, 20, "xRay list."));
		controlList.add(new GuiButton(9, width / 2 + 2, height / 4 + 112, 98, 20, "Draw board."));
		
		controlList.add(new GuiButton(90, width / 2 - 100, height - 47, 98, 20, "<--"));
		controlList.add(new GuiButton(91, width / 2 + 2, height - 47, 98, 20, "-->"));
		
		controlList.add(new GuiButton(100, width / 2 - 100, height - 26, "Done."));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		switch(button.id) {
		case 0 :
			mc.displayGuiScreen(new MacroList());
			break;
			
		case 1 :
			mc.displayGuiScreen(new Settings_1());
			break;
			
		case 2 :
			mc.displayGuiScreen(new WaypointList());
			break;
			
		case 3 :
			mc.displayGuiScreen(new KeyList());
			break;
			
		case 4 :
			mc.displayGuiScreen(new IRCList());
			break;
			
		case 5 :
			mc.displayGuiScreen(new AltList(this));
			break;
			
		case 6 :
			mc.displayGuiScreen(new FriendList());
			break;
			
		case 7 :
			mc.displayGuiScreen(new EnemyList());
			break;
			
		case 8 :
			mc.displayGuiScreen(new xRayList());
			break;
			
		case 9 :
			mc.displayGuiScreen(new DrawBoard());
			break;
			
		case 90 :
			mc.displayGuiScreen(new Main_2());
			break;
			
		case 91 :
			mc.displayGuiScreen(new Main_2());
			break;
			
		case 100 :
			mc.displayGuiScreen(parentScreen);
			break;
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		drawDefaultBackground();

		drawCenteredString(fontRenderer, "Dream PvP main. [1/2]", width / 2, 50, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
}