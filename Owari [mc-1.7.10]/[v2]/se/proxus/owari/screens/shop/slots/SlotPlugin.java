package se.proxus.owari.screens.shop.slots;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;

import org.lwjgl.opengl.GL11;

import se.proxus.owari.Client;
import se.proxus.owari.io.Download;
import se.proxus.owari.plugins.PluginManager;
import se.proxus.owari.screens.shop.ScreenPluginShop;

public class SlotPlugin extends GuiSlot {

	private final ScreenPluginShop guiScreen;
	private int selected;

	public SlotPlugin(final ScreenPluginShop guiScreen) {
		super(Minecraft.getMinecraft(), guiScreen.width, guiScreen.height, 32,
				guiScreen.height - 59, 54);
		this.guiScreen = guiScreen;
		selected = 0;
	}

	@Override
	protected int getSize() {
		return guiScreen.getPluginOnline().getPlugins().size();
	}

	@Override
	protected void drawBackground() {
		guiScreen.drawDefaultBackground();
	}

	@Override
	protected boolean isSelected(final int var0) {
		return selected == var0;
	}

	public int getSelected() {
		return selected;
	}

	@Override
	protected void elementClicked(final int p_148144_1_, final boolean p_148144_2_,
			final int p_148144_3_, final int p_148144_4_) {
		selected = p_148144_1_;
	}

	@Override
	protected void drawSlot(int selected, int var1, int var2, int var3, int p_180791_5_,
			int p_180791_6_) {
		FontRenderer font = Client.getInstance().getMinecraft().fontRendererObj;
		String[] pluginInfo = { "The Final Solution", "Hitler", "19,50", "Third Reich" };
		pluginInfo = guiScreen.getPluginOnline().getPluginInfo(selected);
		guiScreen.drawCenteredString(font, pluginInfo[0].replace("_", " "), guiScreen.width / 2,
				var2 + 1, 0xFFFFAA00);
		guiScreen.drawString(font, "Author(s): " + pluginInfo[1], var1 + 1, var2 + 11, 0xFF808080);
		guiScreen.drawString(font, "Version: " + pluginInfo[2], var1 + 1, var2 + 21, 0xFF808080);
		guiScreen.drawString(font, "Type: " + pluginInfo[3], var1 + 1, var2 + 31, 0xFF808080);
		String progress = "";
		int progressInteger = 0;
		try {
			Download download = guiScreen.getPluginDownloads().getDownload(
					pluginInfo[4].replace("_", "-"));
			if (PluginManager.getInstance().getPlugin(pluginInfo[0]) == null) {
				progress = "0%";
				if (!guiScreen.getPluginDownloads().getDownloads().isEmpty()) {
					if (download != null) {
						progress = "" + (int) download.getProgress() + "%";
					}
				}
			} else {
				progress = "100%";
			}
			progressInteger = download == null ? 0 : (int) download.getProgress();
		} catch (Exception exception) {

		}
		drawProgressBar(var1 + 2, var2 + 42, var1 + getListWidth() - 6, var2 + 48, progressInteger,
				font);
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

	@Override
	public int getListWidth() {
		return super.getListWidth() - 40;
	}
}