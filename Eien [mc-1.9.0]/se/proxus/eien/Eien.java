package se.proxus.eien;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import se.proxus.eien.commands.CommandManager;
import se.proxus.eien.events.EventHandler;
import se.proxus.eien.events.EventListener;
import se.proxus.eien.events.list.client.EventDisplayGuiScreen;
import se.proxus.eien.frames.FrameManager;
import se.proxus.eien.hooks.IngameMenu;
import se.proxus.eien.hooks.MainMenu;
import se.proxus.eien.mods.ModManager;

public class Eien implements EventListener {

	/** We back bois, now with a weeb name. **/
	private static Eien instance;
	private File directory;

	public void onStartup() throws Exception {
		Display.setTitle(toString());
		getLogger().info(toString() + " has been started up.");
		FileUtils.forceMkdir(getDirectory());
		FileUtils.forceMkdir(new File(getDirectory(), "ui"));
		CommandManager.getInstance().init();
		ModManager.getInstance().init();
		FrameManager.getInstance().init();
		ModManager.getInstance().loadMods();
		CommandManager.getInstance().loadCommands();
		// getLogger().info("meme 1");
	}

	public void load() throws Exception {
		/*
		 * getLogger().info("meme 2");
		 * getLogger().info("Loading mods and commands");
		 * ModManager.getInstance().loadMods();
		 * CommandManager.getInstance().loadCommands();
		 */
	}

	@EventHandler
	public void onGuiScreenDisplayed(final EventDisplayGuiScreen event) {
		GuiScreen screen = event.getScreen();

		if (screen instanceof GuiMainMenu) {
			if (getDirectory() == null) {
				setDirectory(new File(getMinecraft().mcDataDir, getName().toLowerCase()));
			}
			event.setScreen(new MainMenu());
		}

		if (screen instanceof GuiIngameMenu) {
			event.setScreen(new IngameMenu());
		}
	}

	public String getName() {
		return "Eien";
	}

	public double getVersion() {
		return 0.1D;
	}

	public Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}

	public String getMinecraftVersion() {
		return "1.9";
	}

	public Logger getLogger() {
		return LogManager.getLogger();
	}

	public File getDirectory() {
		return directory;
	}

	// Singleton up in this biatch
	public static Eien getInstance() {
		if (instance == null) {
			instance = new Eien();
		}
		return instance;
	}

	@Override
	public String toString() {
		return getName() + " " + getVersion();
	}

	public void setDirectory(final File directory) {
		this.directory = directory;
	}
}