package se.proxus.DreamPvP.Gui.Screens;

import se.proxus.DreamPvP.*;
import se.proxus.DreamPvP.Gui.Screens.Sliders.*;
import net.minecraft.src.*;

public class Settings_2 extends GuiScreen {
	
    private int i;
    private int j;

	@Override
	public void initGui() {
        i = width / 2 - 175;
        j = 0;
        
		controlList.add(new GuiButton(0, width / 2 - 100, height - 44, 98, 20, "<--"));
		controlList.add(new GuiButton(1, width / 2 + 2, height - 44, 98, 20, "-->"));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 23, "Done."));
		/*controlList.add(new s_espCheckSize(400, width / 2 - 100, height / 6 + 24 * ((j + 3) / 2), mc.dp.settings.espCheckSize / 256F));
		controlList.add(new s_FlySpeed(401, width / 2 - 100, height / 6 + 47 * ((j + 3) / 2), (float)mc.dp.settings.flySpeed / 20.0F));
		controlList.add(new s_EspMode(402, width / 2 - 100, height / 6 + 69 * ((j + 3) / 2), mc.dp.settings.espMode / 1));
		controlList.add(new s_BlockCol2(403, width / 2 - 100, height / 6 + 91 * ((j + 3) / 2), DreamPvP.settings.bC2 / 1.0F));
		controlList.add(new s_BlockCol3(404, width / 2 - 100, height / 6 + 113 * ((j + 3) / 2), DreamPvP.settings.bC3 / 1.0F));
		controlList.add(new s_MineSpeed(405, width / 2 - 100, height / 6 + 135 * ((j + 3) / 2), DreamPvP.bModList.miner.mineSpeed / 1.0F));*/
	}

	@Override
	public void actionPerformed(GuiButton button) {
		switch(button.id) {
		case 0 :
			mc.displayGuiScreen(new Settings_1());
			break;
			
		case 1 :
			mc.displayGuiScreen(new Settings_1());
			break;
			
		case 2 :
			mc.displayGuiScreen(new Main());
			break;
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		drawDefaultBackground();
		
		drawCenteredString(fontRenderer, "Settings. [2/2].", width / 2, 50, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
}