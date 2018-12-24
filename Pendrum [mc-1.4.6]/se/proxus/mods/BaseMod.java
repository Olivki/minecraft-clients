package se.proxus.mods;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.src.*;

import se.proxus.*;
import se.proxus.events.Event;

public abstract class BaseMod extends Pendrum {

	protected String name;

	protected boolean state;

	protected boolean hidden;

	protected ModType type;

	protected ModInfo info;

	protected ModConfig config;

	protected HashMap<String, Object> options;

	protected ModEvent event;

	public BaseMod(String name, ModInfo info, ModType type, boolean hidden) {
		this.setName(name);
		this.setType(type);
		this.setInfo(info);
		this.setHidden(hidden);
		this.setOptions(new HashMap<String, Object>());
		this.setConfig(new ModConfig(this, this.wrapper.getPendrum()));
		this.setEvent(new ModEvent(this));
		this.registerEvents();
	}

	public void registerEvents() {};

	public void toggle() {
		this.state =! this.state;

		if(this.state) {
			this.onEnable();
		} else {
			this.onDisable();
		}

		this.getConfig().saveConfig();

		this.utils.log("Mod", this.getName() + ": " + this.getState());
	}

	public void onEnable() {
		if(!(this.hidden)) {
			this.sett.guiArray.add(this.getType().getColor() + this.getName().replace("_", " "));
		}

		this.onEnabled();
	}

	public void onDisable() {
		if(!(this.hidden)) {
			this.sett.guiArray.remove(this.sett.guiArray.indexOf(this.getType().getColor() + this.getName().replace("_", " ")));
		}

		this.onDisabled();
	}

	public abstract void onEnabled();

	public abstract void onDisabled();

	public void onEvent(Event var0) {};

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getState() {
		return this.state;
	}

	public BaseMod setState(boolean state, boolean save) {
		this.state = state;

		if(save) {
			this.getConfig().saveConfig();
		}

		if(this.getState()) {
			this.onEnable();
		} else {
			this.onDisable();
		}

		return this;
	}

	public ModConfig getConfig() {
		return config;
	}

	public void setConfig(ModConfig config) {
		this.config = config;
	}

	public ModType getType() {
		return this.type;
	}

	public void setType(ModType type) {
		this.type = type;
	}

	public HashMap<String, Object> getOptions() {
		return this.options;
	}

	public Object getOption(String var0) {
		return this.options.get(var0);
	}

	public BaseMod setOption(String var0, Object var1, boolean var2) {
		this.options.put(var0, var1);

		if(var2) {
			this.config.saveConfig();
		}

		return this;
	}

	public ModInfo getInfo() {
		return this.info;
	}

	public void setInfo(ModInfo info) {
		this.info = info;
	}

	public ModEvent getEvent() {
		return this.event;
	}

	public void setEvent(ModEvent event) {
		this.event = event;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setOptions(HashMap<String, Object> options) {
		this.options = options;
	}
}