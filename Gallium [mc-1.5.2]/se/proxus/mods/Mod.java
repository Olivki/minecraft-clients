package se.proxus.mods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.commands.CommandType;
import se.proxus.events.EventListener;
import se.proxus.tools.Colours;
import se.proxus.tools.StringTools;

public abstract class Mod implements EventListener {

	public enum ModController {
		NONE, TOGGLE, COMMAND;
	}

	private final HashMap<Integer, ModValue> loadedSettings = new HashMap<Integer, ModValue>();
	private String name;
	private String arrayName;
	private String key;
	private String description;
	private int colour;
	private boolean state;
	private boolean hidden;
	public ModCategory category;
	private ModController controller;
	private Gallium client;

	public Mod(final String name, final ModCategory category,
			final ModController controller, final boolean hidden,
			final Gallium client) {
		setClient(client);
		setKey("NONE", false);
		setDescription("This mod currently doesn't have a description.");
		setName(name);
		setHidden(hidden);
		setCategory(category);
		Random rng = getClient().getRNG();
		setArrayName(name);
		setController(controller);
		init();
		try {
			loadSettings();
			Set set = getLoadedSettings().entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				final Map.Entry entry = (Map.Entry) iterator.next();
				final ModValue value = (ModValue) entry.getValue();
				if (value.getName().equalsIgnoreCase("N/A")
						|| value.getName().replace(getName() + " ", "")
						.equalsIgnoreCase("N/A")
						|| value.getName().contains("N/A"))
					continue;
				if (value.getValue() instanceof Boolean)
					getClient().getCommands().registerCommand(
							new Command(getName().replace(" ", "")
									.toLowerCase(), "."
											+ getName().replace(" ", "").toLowerCase()
											+ " ["
											+ value.getName()
											.replace(getName() + " ", "")
											.replace(" ", "").toLowerCase()
											+ "]", "Toggles the state for the "
													+ value.getName() + ".", getClient(),
													CommandType.SETTING) {
								@Override
								public void onCommand(final String message,
										final String... args) {
									if (args[0].equalsIgnoreCase(value
											.getName()
											.replace(getName() + " ", "")
											.replace(" ", ""))) {
										registerSetting(
												(Integer) entry.getKey(),
												(Object) !(Boolean) getSetting((Integer) entry
														.getKey()),
														value.getName().replace(
																getName() + " ", ""),
																0.0D, true, true, true);
										getClient()
										.getPlayer()
										.addMessage(
												Colours.GREY
												+ value.getName()
												+ " has been set to "
												+ Colours.CLIENT_COLOUR
												+ getSetting((Integer) entry
														.getKey())
														+ ".");
									}
								}
							});
				if (value.getValue() instanceof Integer
						|| value.getValue() instanceof Double
						|| value.getValue() instanceof Long
						|| value.getValue() instanceof Float)
					getClient().getCommands().registerCommand(
							new Command(getName().replace(" ", "")
									.toLowerCase(), "."
											+ getName().replace(" ", "").toLowerCase()
											+ " ["
											+ value.getName()
											.replace(getName() + " ", "")
											.split(" ")[0].replace(" ", "")
											.toLowerCase() + "] (value)",
											"Sets the value for the " + value.getName()
											+ ".", getClient(),
											CommandType.SETTING) {
								@Override
								public void onCommand(final String message,
										final String... args) {
									if (args[0].equalsIgnoreCase(value
											.getName()
											.replace(getName() + " ", "")
											.split(" ")[0].replace(" ", "")
											.toLowerCase()))
										if (StringTools.isNumber(args[1])) {
											if (checkValues(
													Float.parseFloat(args[1]),
													0.0F, value.getMax())) {
												setAmtInOptions(args[1]);
												getClient()
												.getPlayer()
												.addMessage(
														Colours.GREY
														+ value.getName()
														+ " has been set to "
														+ Colours.CLIENT_COLOUR
														+ args[1]
																+ ".");
											} else
												getClient()
												.getPlayer()
												.addMessage(
														Colours.GREY
														+ "Value is either to low or to high.");
										} else
											getClient()
											.getPlayer()
											.addMessage(
													Colours.GREY
													+ "Please enter a number.");
								}

								public void setAmtInOptions(final Object obj) {
									try {
										if (getSetting((Integer) entry.getKey()) instanceof Double)
											registerSetting(
													(Integer) entry.getKey(),
													Double.parseDouble((String) obj),
													value.getName()
													.replace(
															getName()
															+ " ",
															""), value
															.getMax(), true,
															true, true);
										else if (getSetting((Integer) entry
												.getKey()) instanceof Float)
											registerSetting(
													(Integer) entry.getKey(),
													Float.parseFloat((String) obj),
													value.getName()
													.replace(
															getName()
															+ " ",
															""), value
															.getMax(), true,
															true, true);
										else if (getSetting((Integer) entry
												.getKey()) instanceof Integer)
											registerSetting(
													(Integer) entry.getKey(),
													Integer.parseInt((String) obj),
													value.getName()
													.replace(
															getName()
															+ " ",
															""), value
															.getMax(), true,
															true, true);
										else if (getSetting((Integer) entry
												.getKey()) instanceof Long)
											registerSetting(
													(Integer) entry.getKey(),
													Long.parseLong((String) obj),
													value.getName()
													.replace(
															getName()
															+ " ",
															""), value
															.getMax(), true,
															true, true);
									} catch (Exception e) {
										e.printStackTrace();
										return;
									}
								}
							});
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public Mod(final String name, final ModCategory category,
			final boolean hidden, final Gallium client) {
		this(name, category, ModController.TOGGLE, hidden, client);
	}

	public abstract void init();

	public abstract void onEnable();

	public abstract void onDisable();

	public void toggle() {
		if (getController() == ModController.TOGGLE) {
			setState(!getState());
			checkState();
			getClient().getLogger().log(Level.INFO,
					getName() + " has been set to " + getState());
			saveSettings();
		}
	}

	public void checkState() {
		if (getState())
			onEnable();
		else
			onDisable();
	}

	public void loadSettings() {
		File file = new File(getClient().getMods().getDirectory(),
				File.separator + getName() + ".gcfg" + File.separator);
		String line = "";
		if (file.exists()) {
			BufferedReader fileReader;
			try {
				fileReader = new BufferedReader(new FileReader(file));
				try {
					while ((line = fileReader.readLine()) != null) {
						if (line.split(":")[0].equalsIgnoreCase("Key"))
							setKey(line.split(":")[1], false);
						if (line.split(":")[0].equalsIgnoreCase("State")) {
							setState(Boolean.parseBoolean(line.split(":")[1]));
							if (getState())
								onEnable();
						}
						Set set = getLoadedSettings().entrySet();
						Iterator iterator = set.iterator();
						while (iterator.hasNext()) {
							Map.Entry entry = (Map.Entry) iterator.next();
							if (line.split(":")[0].equalsIgnoreCase("Setting "
									+ entry.getKey()))
								registerSetting(
										Integer.parseInt(line.split(":")[0]
												.substring("Setting ".length())),
												getFixedObject(line.split(":")[1]),
												line.split(":")[2],
												(Double) getFixedObject(line.split(":")[3]),
												false, false, true);
						}
					}
					fileReader.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else
			saveSettings();
	}

	public void saveSettings() {
		try {
			File file = new File(getClient().getMods().getDirectory(),
					File.separator + getName() + ".gcfg" + File.separator);
			PrintWriter fileWriter = new PrintWriter(file);
			fileWriter.println("Key:" + getKey());
			fileWriter.println("State:" + getState());
			Set set = getLoadedSettings().entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				fileWriter
				.println("Setting "
						+ entry.getKey()
						+ ":"
						+ setFixedObject(((ModValue) entry.getValue())
								.getValue())
								+ ":"
								+ ((ModValue) entry.getValue()).getName()
								+ ":"
								+ setFixedObject(((ModValue) entry.getValue())
										.getMax()));
			}
			fileWriter.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public Object getFixedObject(final String var0) {
		Object var1 = null;

		try {
			if (var0.endsWith("F"))
				var1 = Float.parseFloat(var0.replace("F", ""));
			if (var0.endsWith("I"))
				var1 = Integer.parseInt(var0.replace("I", ""));
			if (var0.endsWith("B"))
				var1 = Boolean.parseBoolean(var0.replace("B", ""));
			if (var0.endsWith("L"))
				var1 = Long.parseLong(var0.replace("L", ""));
			if (var0.endsWith("D"))
				var1 = Double.parseDouble(var0.replace("D", ""));
			if (var0.endsWith("-STRING-"))
				var1 = var0.replace("-STRING-", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return var1;
	}

	public String setFixedObject(final Object var0) {
		String var1 = null;

		try {
			if (var0 instanceof Float)
				var1 = var0 + "F";
			if (var0 instanceof Integer)
				var1 = var0 + "I";
			if (var0 instanceof Boolean)
				var1 = var0 + "B";
			if (var0 instanceof Long)
				var1 = var0 + "L";
			if (var0 instanceof Double)
				var1 = var0 + "D";
			if (var0 instanceof String)
				var1 = var0 + "-STRING-";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return var1;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
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

	public String getKey() {
		return key;
	}

	public void setKey(final String key, final boolean shouldSave) {
		this.key = key.toUpperCase();
		if (shouldSave)
			saveSettings();
	}

	public boolean getState() {
		return state;
	}

	public void setState(final boolean state) {
		this.state = state;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(final boolean hidden) {
		this.hidden = hidden;
	}

	public Object registerSetting(final int id, final Object value,
			final boolean shouldSave, final boolean shouldLog) {
		getLoadedSettings().put(id,
				new ModValue("N/A", value, 1.0D).setId(id).setMod(this));
		if (shouldLog)
			getClient().getLogger().log(Level.INFO,
					"Registered the setting: " + id + ":" + value);
		if (shouldSave)
			saveSettings();
		return value;
	}

	public Object registerSetting(final int id, final Object value,
			final String name, final double max,
			final boolean shouldAppendName, final boolean shouldSave,
			final boolean shouldLog) {
		String settingsName = shouldAppendName ? getName() + " " : "";
		getLoadedSettings().put(
				id,
				new ModValue(settingsName + name, value, max).setId(id).setMod(
						this));
		if (shouldLog)
			getClient().getLogger().log(Level.INFO,
					"Registered the setting: " + id + ":" + value + ":" + name);
		if (shouldSave)
			saveSettings();
		return value;
	}

	public Object getSetting(final int id) {
		return getLoadedSettings().get(id).getValue();
	}

	public HashMap<Integer, ModValue> getLoadedSettings() {
		return loadedSettings;
	}

	public Gallium getClient() {
		return client;
	}

	public void setClient(final Gallium client) {
		this.client = client;
	}

	public String getArrayName() {
		return arrayName;
	}

	public void setArrayName(final String arrayName) {
		this.arrayName = arrayName;
	}

	public int getColour() {
		return colour;
	}

	public void setColour(final int colour) {
		this.colour = colour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}