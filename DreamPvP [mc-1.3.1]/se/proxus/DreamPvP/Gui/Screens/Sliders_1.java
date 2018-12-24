package se.proxus.DreamPvP.Gui.Screens;

import se.proxus.DreamPvP.DreamPvP;
import se.proxus.DreamPvP.Gui.Screens.Sliders.s_AuraDelay;
import se.proxus.DreamPvP.Gui.Screens.Sliders.s_BlockCol1;
import se.proxus.DreamPvP.Gui.Screens.Sliders.s_BlockCol2;
import se.proxus.DreamPvP.Gui.Screens.Sliders.s_BlockCol3;
import se.proxus.DreamPvP.Gui.Screens.Sliders.s_MineSpeed;
import se.proxus.DreamPvP.Gui.Screens.Sliders.s_StepHeight;
import net.minecraft.src.*;

public class Sliders_1 extends GuiScreen {
	
    private int i;
    private int j;

	@Override
	public void initGui() {
        i = width / 2 - 175;
        j = 0;
        
		controlList.add(new GuiButton(0, width / 2 - 100, height - 44, 98, 20, "<--"));
		controlList.add(new GuiButton(1, width / 2 + 2, height - 44, 98, 20, "-->"));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 23, "Done."));
		controlList.add(new s_AuraDelay(400, width / 2 - 100, height / 6 + 24 * ((j + 3) / 2), mc.dp.bModList.playerAura.auraDelay / 40.0F));
		controlList.add(new s_StepHeight(401, width / 2 - 100, height / 6 + 47 * ((j + 3) / 2), mc.dp.mc.thePlayer.stepHeight / 9.0F));
		controlList.add(new s_BlockCol1(402, width / 2 - 100, height / 6 + 69 * ((j + 3) / 2), mc.dp.settings.bC1 / 1.0F));
		controlList.add(new s_BlockCol2(403, width / 2 - 100, height / 6 + 91 * ((j + 3) / 2), mc.dp.settings.bC2 / 1.0F));
		controlList.add(new s_BlockCol3(404, width / 2 - 100, height / 6 + 113 * ((j + 3) / 2), mc.dp.settings.bC3 / 1.0F));
		controlList.add(new s_MineSpeed(405, width / 2 - 100, height / 6 + 135 * ((j + 3) / 2), mc.dp.bModList.miner.mineSpeed / 1.0F));
		//controlList.add(new s_espCheckSize(406, width / 2 - 100, height / 6 + 157 * ((j + 3) / 2), DreamPvP.settings.espCheckSize / 256));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		switch(button.id) {
		case 0 :
			mc.displayGuiScreen(new Sliders_3());
			break;
			
		case 1 :
			mc.displayGuiScreen(new Sliders_2());
			break;
			
		case 2 :
			mc.displayGuiScreen(new Main());
			break;
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		drawDefaultBackground();
		
		drawCenteredString(fontRenderer, "Sliders. [1/3].", width / 2, 50, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
}