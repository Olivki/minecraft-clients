package se.proxus.owari.hooks;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import se.proxus.owari.Client;
import se.proxus.owari.screens.ScreenMain;

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
		super.actionPerformed(par1GuiButton);

		switch (par1GuiButton.id) {
			case 98:
				mc.displayGuiScreen(new ScreenMain(this));
				break;
		}
	}

	public Client getClient() {
		return Client.getInstance();
	}
}