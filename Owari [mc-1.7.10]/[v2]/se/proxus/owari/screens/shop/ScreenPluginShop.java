package se.proxus.owari.screens.shop;

import java.util.concurrent.TimeUnit;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import se.proxus.owari.Client;
import se.proxus.owari.io.Download;
import se.proxus.owari.io.DownloadManager;
import se.proxus.owari.mods.ModManager;
import se.proxus.owari.plugins.PluginManager;
import se.proxus.owari.plugins.PluginOnline;
import se.proxus.owari.screens.shop.slots.SlotPlugin;

public class ScreenPluginShop extends GuiScreen {

	private SlotPlugin guiSlot;
	private final GuiScreen parentScreen;
	private GuiButton buttonDownload;
	private GuiButton buttonDone;
	private PluginOnline pluginOnline;
	private DownloadManager pluginDownloads;

	public ScreenPluginShop(final GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
		setPluginDownloads(new DownloadManager(5));
		setPluginOnline(new PluginOnline("http://www.proxus.se/"
				+ getClient().getName().toLowerCase() + "/plugins/"));
		Thread pluginOnline = new Thread(getPluginOnline(), "Plugin Fetcher Thread");
		pluginOnline.start();
	}

	@Override
	public void initGui() {
		guiSlot = new SlotPlugin(this);
		guiSlot.registerScrollButtons(7, 8);
		buttonList.add(buttonDownload = new GuiButton(1, width / 2 - 100, height - 48, "Download"));
		buttonList.add(buttonDone = new GuiButton(2, width / 2 - 100, height - 26, "Done"));
		PluginManager.getInstance().getLoader().close();
	}

	@Override
	public void updateScreen() {
		buttonList.clear();
		buttonList.add(buttonDownload = new GuiButton(1, width / 2 - 100, height - 48, "Download"));
		buttonList.add(buttonDone = new GuiButton(2, width / 2 - 100, height - 26, "Done"));
		try {
			String[] info = getPluginOnline().getPluginInfo(guiSlot.getSelected());
			boolean isEmpty = getPluginDownloads().getDownloads().isEmpty();
			boolean isDownloading = isEmpty ? false : getPluginDownloads().getDownload(
					info[4].replace("_", "-")).getProgress() >= 100;
			buttonDownload.enabled = !isDownloading;
			boolean isAllDownloadsDone = getPluginDownloads().getCompletedDownloads().size() >= getPluginDownloads()
					.getDownloads().size();
			buttonDone.enabled = isEmpty ? true : isAllDownloadsDone;
		} catch (Exception e) {

		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		if (button.enabled) {
			ModManager mods = ModManager.getInstance();
			switch (button.id) {
				case 1:
					String[] info = getPluginOnline().getPluginInfo(guiSlot.getSelected());
					try {
						Download download = new Download(
								info[4],
								info[0],
								info[0].replace("_", " ") + " " + info[3] + " v" + info[2] + ".jar",
								getClient().getPluginDirectory(), getPluginDownloads());
						getPluginDownloads().registerDownload(download);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case 2:
					reloadPlugins();
					mc.displayGuiScreen(parentScreen);
					break;
			}
		}

		guiSlot.actionPerformed(button);
	}

	@Override
	public void drawScreen(final int x, final int y, final float ticks) {
		FontRenderer font = Client.getInstance().getMinecraft().fontRendererObj;
		guiSlot.drawScreen(x, y, ticks);
		if (getPluginOnline().getPlugins().isEmpty()) {
			drawCenteredString(font, "You've downloaded all of the plugins!", width / 2,
					height / 2 - 25, 0xFF808080);
			drawCenteredString(font, "Please check back later.", width / 2, height / 2 - 15,
					0xFF808080);
			getButtonDownload().enabled = false;
		}
		drawCenteredString(fontRendererObj, "Plugin Shop", width / 2, 10, 0xFFFFFFFF);
		super.drawScreen(x, y, ticks);
	}

	@Override
	public void keyTyped(final char keyChar, final int keyID) {
		try {
			if (Keyboard.getKeyName(keyID).equalsIgnoreCase("ESCAPE")) {
				boolean isEmpty = getPluginDownloads().getDownloads().isEmpty();
				boolean isAllDownloadsDone = getPluginDownloads().getCompletedDownloads().size() >= getPluginDownloads()
						.getDownloads().size();
				if (isEmpty ? true : isAllDownloadsDone) {
					reloadPlugins();
					mc.displayGuiScreen(parentScreen);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reloadPlugins() {
		getPluginDownloads().getDownloadPool().shutdown();
		try {
			getPluginDownloads().getDownloadPool().awaitTermination(Long.MAX_VALUE,
					TimeUnit.MILLISECONDS);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		PluginManager.getInstance().loadPlugins(getClient().getPluginDirectory());
		setPluginOnline(new PluginOnline("http://www.proxus.se/"
				+ getClient().getName().toLowerCase() + "/plugins/"));
		Thread pluginOnline = new Thread(getPluginOnline(), "Plugin Fetcher Thread");
		pluginOnline.start();
	}

	public PluginOnline getPluginOnline() {
		return pluginOnline;
	}

	public void setPluginOnline(final PluginOnline pluginOnline) {
		this.pluginOnline = pluginOnline;
	}

	public Client getClient() {
		return Client.getInstance();
	}

	public GuiButton getButtonDownload() {
		return buttonDownload;
	}

	public void setButtonDownload(final GuiButton buttonDownload) {
		this.buttonDownload = buttonDownload;
	}

	public DownloadManager getPluginDownloads() {
		return pluginDownloads;
	}

	public void setPluginDownloads(final DownloadManager pluginDownloads) {
		this.pluginDownloads = pluginDownloads;
	}
}