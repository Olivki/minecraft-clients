package se.proxus.DreamPvP.Gui.Screens;

import se.proxus.DreamPvP.*;
import se.proxus.DreamPvP.Gui.Screens.Sliders.*;
import net.minecraft.src.*;

public class Sliders_2 extends GuiScreen {
	
    private int i;
    private int j;

	@Override
	public void initGui() {
        i = width / 2 - 175;
        j = 0;
        
		controlList.add(new GuiButton(0, width / 2 - 100, height - 44, 98, 20, "<--"));
		controlList.add(new GuiButton(1, width / 2 + 2, height - 44, 98, 20, "-->"));
		controlList.add(new GuiButton(2, width / 2 - 100, height - 23, "Done."));
		controlList.add(new s_espCheckSize(400, width / 2 - 100, height / 6 + 24 * ((j + 3) / 2), mc.dp.settings.espCheckSize / 256F));
		controlList.add(new s_FlySpeed(401, width / 2 - 100, height / 6 + 47 * ((j + 3) / 2), (float)mc.dp.settings.flySpeed / 20.0F));
		controlList.add(new s_ChamR1(402, width / 2 - 100, height / 6 + 69 * ((j + 3) / 2), mc.dp.settings.cR1 / 1.0F));
		controlList.add(new s_ChamG1(403, width / 2 - 100, height / 6 + 91 * ((j + 3) / 2), mc.dp.settings.cG1 / 1.0F));
		controlList.add(new s_ChamB1(404, width / 2 - 100, height / 6 + 113 * ((j + 3) / 2), mc.dp.settings.cB1 / 1.0F));
		controlList.add(new s_ChamR2(405, width / 2 - 100, height / 6 + 135 * ((j + 3) / 2), mc.dp.settings.cR2 / 1.0F));
	}

	@Override
	public void actionPerformed(GuiButton button) {
		switch(button.id) {
		case 0 :
			mc.displayGuiScreen(new Sliders_1());
			break;
			
		case 1 :
			mc.displayGuiScreen(new Sliders_3());
			break;
			
		case 2 :
			mc.displayGuiScreen(new Main());
			break;
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		drawDefaultBackground();
		
		drawCenteredString(fontRenderer, "Sliders. [2/3].", width / 2, 50, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}
}