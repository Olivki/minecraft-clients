package se.proxus.owari.plugins;

import java.io.File;

import se.proxus.owari.Client;

public abstract class Plugin {

	private String name;
	private double version;
	private File dataFolder;
	private boolean loaded;

	protected Plugin() {
		setName(getClass().getAnnotation(PluginManifest.class).name());
		setVersion(getClass().getAnnotation(PluginManifest.class).version());
	}

	public abstract void load() throws Exception;

	public abstract void unload() throws Exception;

	public File getDataFolder() {
		return dataFolder;
	}

	public void setDataFolder(final File dataFolder) {
		this.dataFolder = dataFolder;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(final boolean loaded) {
		this.loaded = loaded;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(final double version) {
		this.version = version;
	}

	public Client getClient() {
		return Client.getInstance();
	}
}