package se.proxus.owari.screens;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

import se.proxus.owari.Client;
import se.proxus.owari.hooks.MainMenu;
import se.proxus.owari.io.Download;
import se.proxus.owari.io.DownloadManager;
import se.proxus.owari.tools.IOTools;

public class ScreenInitial extends GuiScreen {

	private final List<String> loadedLinks = new LinkedList<String>();
	private GuiScreen parentScreen;
	private DownloadManager initialDownloads;

	public ScreenInitial(final GuiScreen parentScreen) {
		setParentScreen(parentScreen);
		setDownloads(new DownloadManager(1));
	}

	@Override
	public void initGui() {
		Thread linkFetcher = new Thread(new LinkFetcher(this, "http://www.proxus.se/"
				+ getClient().getName().toLowerCase() + "/initial.txt"), "Link Fetcher Thread");
		linkFetcher.start();
	}

	public void downloadPlugins() {
		for (String link : getLoadedLinks()) {
			String[] info = link.substring(link.lastIndexOf("/") + 1).split("%20");
			File tempFile = new File(getClient().getPluginDirectory(), info[0].replace("_", " ")
					+ " " + info[3].substring(0, info[3].lastIndexOf(".")) + " v" + info[2]
					+ ".jar");
			if (!tempFile.exists()) {
				getDownloads().registerDownload(
						link,
						info[0],
						info[0].replace("_", " ") + " "
								+ info[3].substring(0, info[3].lastIndexOf(".")) + " v" + info[2]
								+ ".jar", getClient(), getClient().getPluginDirectory());
			}
		}
	}

	@Override
	public void updateScreen() {
		boolean isEmpty = getDownloads().getDownloads().isEmpty();
		boolean isAllDownloadsDone = getDownloads().getCompletedDownloads().size() >= getDownloads()
				.getDownloads().size();
		MainMenu mainMenu = (MainMenu) getParentScreen();

		if (isAllDownloadsDone && !isEmpty) {
			try {
				mainMenu.getClient().load();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			mainMenu.setShouldSkipInitial(true);
			mc.displayGuiScreen(getParentScreen());
		} else if (isEmpty) {
			mainMenu.setShouldSkipInitial(true);
			mainMenu.setShouldLoad(true);
			mc.displayGuiScreen(getParentScreen());
		}
	}

	@Override
	public void drawScreen(final int x, final int y, final float ticks) {
		drawDefaultBackground();
		FontRenderer font = Client.getInstance().getMinecraft().fontRendererObj;
		drawCenteredString(font, "Downloading the necessary plugins.", width / 2, height / 2 - 38,
				0xFFFFFFFF);
		Download currentDownload = getDownloads().getCurrentDownload();
		String fileName = currentDownload == null ? "-" : currentDownload.getFileName();
		drawCenteredString(font, currentDownload == null ? "-" : fileName, width / 2,
				height / 2 - 26, 0xFFFFFFFF);
		drawProgressBar(width / 2 - 60, height / 2 - 14, width / 2 + 60, height / 2 - 6,
				currentDownload == null ? 0 : (int) currentDownload.getProgress(), font);
	}

	public void drawProgressBar(final int x, final int y, final int width, final int height,
			final int progress, final FontRenderer font) {
		int w = width - x;
		float dx = (float) (w - 2) / (float) 100;
		int progressX = 0;
		if (progress == 100) {
			progressX = w;
		} else {
			progressX = (int) (dx * progress);
		}
		Gui.drawRect(x - 1, y - 1, width + 1, height + 1, -6250336);
		Gui.drawRect(x, y, width, height, -16777216);
		if (progress > 0) {
			Gui.drawRect(x, y, x + progressX, height, 0xFF6EB825);
		}
		GL11.glPushMatrix();
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		String text = "" + progress + "%";
		font.drawString(text, x + width - font.getStringWidth(text) / 2, y + height - 3, 0xFFFFFFFF);
		GL11.glPopMatrix();
	}

	public void addLink(final String link) {
		if (!getLoadedLinks().contains(link)) {
			getLoadedLinks().add(link);
			getClient().getLogger().info("Added link " + link);
		}
	}

	public GuiScreen getParentScreen() {
		return parentScreen;
	}

	public void setParentScreen(final GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	public DownloadManager getDownloads() {
		return initialDownloads;
	}

	public void setDownloads(final DownloadManager initialDownloads) {
		this.initialDownloads = initialDownloads;
	}

	public List<String> getLoadedLinks() {
		return loadedLinks;
	}

	public Client getClient() {
		return Client.getInstance();
	}

	class LinkFetcher implements Runnable {

		private ScreenInitial screen;
		private String url;

		public LinkFetcher(final ScreenInitial screen, final String url) {
			setScreen(screen);
			setUrl(url);
		}

		@Override
		public void run() {
			try {
				List<String> content = IOTools.getContentToList(new URL(getUrl()));
				for (String link : content) {
					getScreen().addLink(link);
				}
				getScreen().downloadPlugins();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public ScreenInitial getScreen() {
			return screen;
		}

		public void setScreen(final ScreenInitial screen) {
			this.screen = screen;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(final String url) {
			this.url = url;
		}
	}
}