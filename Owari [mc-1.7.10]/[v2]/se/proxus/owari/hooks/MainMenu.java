package se.proxus.owari.hooks;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.I18n;
import se.proxus.owari.Client;
import se.proxus.owari.screens.ScreenInitial;
import se.proxus.owari.screens.shop.ScreenPluginShop;

public class MainMenu extends GuiMainMenu {

	private static boolean shouldSkipInitial;
	private static boolean shouldLoad;

	@Override
	public void initGui() {
		super.initGui();

		if (!getShouldSkipInitial()) {
			ScreenInitial screen = new ScreenInitial(this);
			mc.displayGuiScreen(screen);
		}

		if (getShouldLoad()) {
			try {
				getClient().load();
			} catch (Exception e) {
				e.printStackTrace();
			}
			setShouldLoad(false);
		}

		for (int buttons = 0; buttons < buttonList.size(); buttons++) {
			GuiButton button = (GuiButton) buttonList.get(buttons);

			if (button.displayString.equalsIgnoreCase(I18n.format("menu.online", new Object[0]))) {
				getClient().getLogger().info(
						"Minecraft Realms button has been found. [INDEX:" + buttons + "]");
				getClient().getLogger().info("Replacing it with the plugin shop button..");
				getClient().getLogger().info(
						"[" + button.displayString + ", " + button.id + "] -> "
								+ "[Plugin Shop, 100]");
				button.displayString = "Plugin Shop";
				button.id = 100;
				getClient()
						.getLogger()
						.info("Minecraft Realms button has successfully been replaced, because fuck realms.");
				break;
			}
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		try {
			super.actionPerformed(button);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (button.enabled) {
			switch (button.id) {
				case 100:
					mc.displayGuiScreen(new ScreenPluginShop(this));
					break;
			}
		}
	}

	public Client getClient() {
		return Client.getInstance();
	}

	public boolean getShouldSkipInitial() {
		return shouldSkipInitial;
	}

	public void setShouldSkipInitial(final boolean shouldDisplayInitial) {
		shouldSkipInitial = shouldDisplayInitial;
	}

	public boolean getShouldLoad() {
		return shouldLoad;
	}

	public void setShouldLoad(final boolean shouldLoad) {
		MainMenu.shouldLoad = shouldLoad;
	}
}