package se.proxus.rori.hooks;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import se.proxus.rori.Rori;
import se.proxus.rori.screens.ScreenMain;

public class IngameMenu extends GuiIngameMenu {

	@Override
	public void initGui() {
		super.initGui();
		byte var1 = -16;
		buttonList.add(new GuiButton(98, width / 2 - 100, height / 4 + 72 + var1, getClient()
				.getName() + " Main"));
	}

	@Override
	public void actionPerformed(final GuiButton par1GuiButton) {
		try {
			super.actionPerformed(par1GuiButton);
		} catch (IOException e) {
			e.printStackTrace();
		}

		switch (par1GuiButton.id) {
			case 98:
				mc.displayGuiScreen(new ScreenMain(this));
				break;
		}
	}

	public Rori getClient() {
		return Rori.getInstance();
	}
}