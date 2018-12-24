package se.proxus.owari.plugins;

import se.proxus.owari.Client;
import se.proxus.owari.commands.Command;
import se.proxus.owari.mods.Mod;

public enum PluginType {

	MOD("Mod", Mod.class), COMMAND("Command", Command.class), MISC("Misc", Client.class);

	private String name;
	private Class<?> parent;

	private PluginType(final String name, final Class<?> parent) {
		setName(name);
		setParent(parent);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Class<?> getParent() {
		return parent;
	}

	public void setParent(final Class<?> parent) {
		this.parent = parent;
	}
}