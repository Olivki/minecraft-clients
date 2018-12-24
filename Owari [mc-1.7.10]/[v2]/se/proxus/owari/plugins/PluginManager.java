package se.proxus.owari.plugins;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import se.proxus.owari.Client;
import se.proxus.owari.commands.CommandManager;
import se.proxus.owari.frames.FrameManager;
import se.proxus.owari.mods.ModManager;
import se.proxus.owari.tools.Tools;

public class PluginManager {

	private static PluginManager instance;
	private final PluginLoader loader = new PluginLoader();
	private static final List<Plugin> REGISTERED_PLUGINS = new LinkedList<Plugin>();
	private File directory;

	public void register(final Plugin plugin) throws Exception {
		Plugin originalPlugin = getPlugin(plugin.getName());
		loadPlugin(plugin);
	}

	public void unregister(final Plugin plugin) {
		try {
			if (plugin.isLoaded()) {
				plugin.unload();
				plugin.setLoaded(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			getClient().getLogger().error(
					"Something went wrong whilst trying to unload the '" + plugin.getName()
							+ "' plugin.");
		}
	}

	public void load() throws Exception {
		for (Plugin plugin : getPlugins()) {
			try {
				plugin.load();
				plugin.setLoaded(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("resource")
	public void loadPlugins(final File directory) {
		if (!directory.exists() || !directory.isDirectory()) {
			directory.mkdirs();
			return;
		}

		setDirectory(directory);

		ModManager.getInstance().loadMods();
		CommandManager.getInstance().loadCommands();
		FrameManager.getInstance().init();

		for (File file : directory.listFiles()) {
			if (!file.getName().endsWith(".jar")) {
				continue;
			}

			JarFile jar = null;
			try {
				jar = new JarFile(file);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}

			getLoader().addURL(file);
			getClient().getLogger().info("Found the plugin '" + file.getName() + "'");

			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
					continue;
				}

				Class pluginClass;
				try {
					pluginClass = getLoader().loadClass(
							entry.getName().substring(0, entry.getName().length() - 6)
									.replace("/", "."));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					break;
				}

				if (!Plugin.class.isAssignableFrom(pluginClass)) {
					continue;
				}

				if (!pluginClass.isAnnotationPresent(PluginManifest.class)) {
					getClient()
							.getLogger()
							.info(String
									.format("Couldn't load plugin class %s, because it doesn't have any author(s)!",
											pluginClass.getName()));
					continue;
				}

				try {
					Constructor<Plugin> constructor = pluginClass.getDeclaredConstructor();
					constructor.setAccessible(true);
					PluginManifest manifest = (PluginManifest) pluginClass
							.getAnnotation(PluginManifest.class);
					Plugin plugin = constructor.newInstance();
					getClient().getLogger().info(
							"Version: " + manifest.version() + ", Author(s): "
									+ Tools.stringArrayToString(manifest.authors()) + ", Type: "
									+ manifest.type().getName());
					register(plugin);
				} catch (InvocationTargetException e) {
					if (!(e.getCause() instanceof NoClassDefFoundError)) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			}
		}
	}

	public Plugin loadPlugin(final Plugin plugin) {
		if (getPlugin(plugin.getName()) == null) {
			getPlugins().add(plugin);
		}
		return plugin;
	}

	public Plugin getPlugin(final String name) {
		Plugin tempPlugin = null;
		for (Plugin plugin : getPlugins()) {
			if (plugin.getName().equalsIgnoreCase(name)) {
				tempPlugin = plugin;
				break;
			}
		}
		return tempPlugin;
	}

	public static PluginManager getInstance() {
		if (instance == null) {
			instance = new PluginManager();
		}
		return instance;
	}

	public File getDirectory() {
		return directory;
	}

	public void setDirectory(final File directory) {
		this.directory = directory;
	}

	public Client getClient() {
		return Client.getInstance();
	}

	public PluginLoader getLoader() {
		return loader;
	}

	public List<Plugin> getPlugins() {
		return REGISTERED_PLUGINS;
	}
}