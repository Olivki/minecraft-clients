package se.proxus.owari.mods;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import se.proxus.owari.Client;
import se.proxus.owari.commands.Command;
import se.proxus.owari.commands.CommandManager;
import se.proxus.owari.commands.CommandType;
import se.proxus.owari.config.Config;
import se.proxus.owari.config.Value;
import se.proxus.owari.events.EventListener;
import se.proxus.owari.tools.Colours;
import se.proxus.owari.tools.StringTools;
import se.proxus.owari.tools.Tools;

public abstract class Mod implements EventListener {

	public enum ModController {
		NONE, TOGGLE, COMMAND;
	}

	private final List<Value> loadedValues = new LinkedList<Value>();
	private Config config;
	private ModCategory category;
	private ModController controller;
	private Client client;

	public Mod(final String name, final ModCategory category, final ModController controller,
			final boolean hidden) {
		try {
			setConfig(new Config(name, ModManager.getInstance().getConfigs()));
			addValue("Name", name, "The mods name.", false, false);
			addValue("Description", "N/A", "The mods description.", false, false);
			addValue("External Path", "N/A", "If the mod is external,"
					+ " then specify the path here.", false, false);
			addValue("Keybind", "NONE", "The mods keybind.", true, false);
			addValue("State", false, "The mods state.", true, false);
			addValue("Hidden", hidden, "If the mod is hidden in the UI ArrayList.", false, false);
			setCategory(category);
			setController(controller);
			init();
			loadConfig();
			addCommands();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCommands() {
		for (final Value value : getValues()) {
			CommandManager commands = CommandManager.getInstance();
			if (!value.isEditable() || value.getName().equalsIgnoreCase("State")
					|| value.getName().equalsIgnoreCase("Keybind")) {
				continue;
			}
			final String commandName = getName().replace(" ", "").toLowerCase();
			final String shortenedValueName = value.getName().contains(" ") ? value.getName()
					.substring(0, value.getName().indexOf(" ")).toLowerCase() : value.getName()
					.toLowerCase().replace(" ", "");
			final String normalValueName = value.getName().replace(" ", "").toLowerCase();
			final String commandValue = value.getName().length() > 10 ? shortenedValueName
					: normalValueName;
			if (value.getValue() instanceof String) {
				commands.registerCommand(new Command(commandName, "." + commandName + " ["
						+ commandValue + "] (value)", "Changes the value for the '"
						+ Colours.CLIENT_COLOUR + value.getName() + Colours.GREY + "' value.",
						CommandType.CONFIG) {
					@Override
					public void onCommand(final String message, final String... args) {
						if (args[0].equalsIgnoreCase(commandValue)) {
							String text = message.substring(commandName.length()
									+ commandValue.length() + 2);
							if (value.getMax() > 0.0D) {
								if (checkValues(text.length(), 0.0F, value.getMax())) {
									setValue(value.getName(), text, true);
									Tools.addChatMessage(Colours.CLIENT_COLOUR + value.getName()
											+ Colours.GREY + " has been set to "
											+ Colours.CLIENT_COLOUR + text + Colours.GREY + ".");
								} else {
									Tools.addChatMessage("Your text is " + Colours.CLIENT_COLOUR
											+ text.length() + Colours.GREY
											+ " characters long, the max is "
											+ Colours.CLIENT_COLOUR + (int) value.getMax()
											+ Colours.GREY + ".");
								}
							} else {
								setValue(value.getName(), text, true);
								Tools.addChatMessage(Colours.CLIENT_COLOUR + value.getName()
										+ Colours.GREY + " has been set to "
										+ Colours.CLIENT_COLOUR + text + Colours.GREY + ".");
							}
						}
					}
				});
			}
			if (value.getValue() instanceof Boolean) {
				commands.registerCommand(new Command(commandName, "." + commandName + " ["
						+ commandValue + "]", "Changes the state for the '" + Colours.CLIENT_COLOUR
						+ value.getName() + Colours.GREY + "' value.", CommandType.CONFIG) {
					@Override
					public void onCommand(final String message, final String... args) {
						if (args[0].equalsIgnoreCase(commandValue)) {
							setValue(value.getName(), !value.getBoolean(), true);
							Tools.addChatMessage(Colours.CLIENT_COLOUR + value.getName()
									+ Colours.GREY + " has been set to " + Colours.CLIENT_COLOUR
									+ value.getBoolean() + Colours.GREY + ".");
						}
					}
				});
			}
			if (Tools.isNumber(value.getValue())) {
				commands.registerCommand(new Command(commandName, "." + commandName + " ["
						+ commandValue + "] (value)", "Changes the value for the '"
						+ Colours.CLIENT_COLOUR + value.getName() + Colours.GREY + "' value.",
						CommandType.CONFIG) {
					@Override
					public void onCommand(final String message, final String... args) {
						if (args[0].equalsIgnoreCase(commandValue)) {
							if (StringTools.isNumber(args[1])) {
								if (checkValues(Float.parseFloat(args[1]), 0.0F, value.getMax())) {
									setAmtInValue(args[1], value.getName());
									Tools.addChatMessage(Colours.CLIENT_COLOUR + value.getName()
											+ Colours.GREY + " has been set to "
											+ Colours.CLIENT_COLOUR + args[1] + Colours.GREY + ".");
								} else {
									Tools.addChatMessage(Colours.GREY
											+ "Value is either to low or to high.");
								}
							} else {
								Tools.addChatMessage(Colours.GREY + "Please enter a number.");
							}
						}
					}
				});
			}
		}
	}

	public Mod(final String name, final ModCategory category, final boolean hidden) {
		this(name, category, ModController.TOGGLE, hidden);
	}

	public abstract void init();

	public abstract void onEnable();

	public abstract void onDisable();

	public void toggle() {
		if (getController() == ModController.TOGGLE) {
			setValue("State", !getState(), false);
			checkState();
			getClient().getLogger().info(getName() + " has been set to " + getState());
			try {
				saveConfig();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void checkState() {
		if (getState()) {
			onEnable();
		} else {
			onDisable();
		}
	}

	public void saveConfig() throws IOException {
		getConfig().saveConfig();
	}

	public void loadConfig() throws Exception {
		getConfig().loadConfig();
	}

	public Value addValue(final String name, final Object value, final String description,
			final double max, final boolean editable, final boolean log) {
		return getConfig().addValue(name, value, description, editable, log);
	}

	public Value addValue(final String name, final Object value, final String description,
			final boolean editable, final boolean log) {
		return addValue(name, value, description, 0.0D, editable, log);
	}

	public Value setValue(final String name, final Object value, final boolean shouldSave) {
		return getConfig().setValue(name, value, shouldSave);
	}

	public Value getValue(final String name) {
		return getConfig().getValue(name);
	}

	public void setAmtInValue(final Object value, final String name) {
		getConfig().setAmtInValue(value, name);
	}

	public String getName() {
		return getValue("Name").getString();
	}

	public String getDescription() {
		return getValue("Description").getString();
	}

	public String getKeybind() {
		return getValue("Keybind").getString();
	}

	public boolean getState() {
		return getValue("State").getBoolean();
	}

	public boolean isHidden() {
		return getValue("Hidden").getBoolean();
	}

	public ModCategory getCategory() {
		return category;
	}

	public void setCategory(final ModCategory category) {
		this.category = category;
	}

	public ModController getController() {
		return controller;
	}

	public void setController(final ModController controller) {
		this.controller = controller;
	}

	public List<Value> getValues() {
		return getConfig().getValues();
	}

	public Client getClient() {
		return Client.getInstance();
	}

	public void setClient(final Client client) {
		this.client = client;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(final Config config) {
		this.config = config;
	}
}