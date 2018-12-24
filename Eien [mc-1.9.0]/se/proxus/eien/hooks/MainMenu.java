package se.proxus.eien.hooks;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.I18n;
import se.proxus.eien.Eien;

public class MainMenu extends GuiMainMenu {

	private static boolean shouldSkipInitial;
	private static boolean shouldNotLoad;

	@Override
	public void initGui() {
		try {
			super.initGui();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * if (!getShouldSkipInitial()) { ScreenInitial screen = new
		 * ScreenInitial(this); mc.displayGuiScreen(screen); }
		 */

		if (!getShouldNotLoad()) {
			try {
				getClient().getLogger().info("More memes");
				getClient().load();
			} catch (Exception e) {
				e.printStackTrace();
			}
			setShouldLoad(true);
		}

		for (int buttons = 0; buttons < buttonList.size(); buttons++) {
			GuiButton button = buttonList.get(buttons);

			if (button.displayString.equalsIgnoreCase(I18n.format("menu.online", new Object[0]))) {
				getClient().getLogger().info(
						"Minecraft Realms button has been found. [INDEX:" + buttons + "]");
				button.id = 100;
				button.enabled = false;
				getClient().getLogger().info("Successfully disabled the Minecraft Realms button..");
				break;
			}
		}

	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
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
					// mc.displayGuiScreen(new ScreenPluginShop(this));
					break;
			}
		}
	}

	public Eien getClient() {
		return Eien.getInstance();
	}

	public boolean getShouldSkipInitial() {
		return shouldSkipInitial;
	}

	public void setShouldSkipInitial(final boolean shouldDisplayInitial) {
		shouldSkipInitial = shouldDisplayInitial;
	}

	public boolean getShouldNotLoad() {
		return shouldNotLoad;
	}

	public void setShouldLoad(final boolean shouldNotLoad) {
		MainMenu.shouldNotLoad = shouldNotLoad;
	}
}