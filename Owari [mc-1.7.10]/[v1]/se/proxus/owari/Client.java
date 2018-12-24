package se.proxus.owari;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import se.proxus.owari.commands.CommandManager;
import se.proxus.owari.events.EventHandler;
import se.proxus.owari.events.EventListener;
import se.proxus.owari.events.list.client.EventDisplayGuiScreen;
import se.proxus.owari.frames.FrameManager;
import se.proxus.owari.hooks.IngameMenu;
import se.proxus.owari.hooks.MainMenu;
import se.proxus.owari.mods.ModManager;
import se.proxus.owari.plugins.PluginManager;

public class Client implements EventListener {

	private static Client instance;
	private File directory;
	private File pluginDirectory;

	public void onStartup() throws Exception {
		Display.setTitle(toString());
		getLogger().info(toString() + " has been started up.");
		FileUtils.forceMkdir(getDirectory());
		FileUtils.forceMkdir(getPluginDirectory());
		FileUtils.forceMkdir(new File(getDirectory(), "ui"));
		CommandManager.getInstance().init();
		ModManager.getInstance().init();
		FrameManager.getInstance().init();
	}

	public void load() throws Exception {
		PluginManager.getInstance().loadPlugins(getPluginDirectory());
		PluginManager.getInstance().load();
		ModManager.getInstance().loadMods();
		CommandManager.getInstance().loadCommands();
	}

	@EventHandler
	public void onGuiScreenDisplayed(final EventDisplayGuiScreen event) {
		GuiScreen screen = event.getScreen();

		if (screen instanceof GuiMainMenu) {
			if (getDirectory() == null) {
				setDirectory(new File(getMinecraft().mcDataDir, getName().toLowerCase()));
			}
			if (getPluginDirectory() == null) {
				setPluginDirectory(new File(getDirectory(), "plugins"));
			}
			event.setScreen(new MainMenu());
		}

		if (screen instanceof GuiIngameMenu) {
			event.setScreen(new IngameMenu());
		}
	}

	public String getName() {
		return "Owari";
	}

	public double getVersion() {
		return 1.0D;
	}

	public Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}

	public String getMinecraftVersion() {
		return "1.7.10";
	}

	@Override
	public String toString() {
		return getName() + " v" + getVersion() + " (" + getMinecraftVersion() + ")";
	}

	public static Client getInstance() {
		if (instance == null) {
			instance = new Client();
		}
		return instance;
	}

	public Logger getLogger() {
		return LogManager.getLogger();
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(final File directory) {
		this.directory = directory;
	}

	public File getPluginDirectory() {
		return pluginDirectory;
	}

	public void setPluginDirectory(final File pluginDirectory) {
		this.pluginDirectory = pluginDirectory;
	}
}