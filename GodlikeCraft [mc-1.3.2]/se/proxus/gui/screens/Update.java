package se.proxus.gui.screens;

import net.minecraft.src.*;

public class Update extends GuiScreen {

	public GuiButton irc;

	@Override
	public void initGui() {
		mc.dm.initAll();

		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 24, "Download newest .jar"));

		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 46, "Skip. (Not recommend)"));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		switch(button.id) {
		case 0 :
			mc.dm.utils.openSite("http://adf.ly/D5ANG");
			//mc.dm.utils.openSite("http://bit.ly/P0o3Yg");
			mc.shutdown();
			break;

		case 1 :
			mc.displayGuiScreen(new GuiMainMenu());
			break;
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		drawDefaultBackground();

		drawCenteredString(fontRenderer, "You are using a outdated version, please download the new one.", width / 2, 50, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
}