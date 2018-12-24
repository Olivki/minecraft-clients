package se.proxus.hacks;

import java.util.*;

import se.proxus.*;

public abstract class Base_Hack extends Main {

	private HashMap HACK_OPTIONS;

	protected int HACK_HEX_VALUE;

	protected String HACK_NAME, HACK_DESCRIPTION, HACK_KEY, HACK_TYPE;

	protected boolean HACK_STATE, HACK_NOCHEAT;

	protected char HACK_COL;

	public abstract void onEnabled();

	public abstract void onDisabled();

	public abstract void onUpdate();

	public void onPostMotion() {};

	public void onRendered3D() {};

	public void onRendered2D(int var1, int var2) {};

	public boolean onCommand(String var1) {return true;};

	public Base_Hack(char HACK_COL, String HACK_NAME, String HACK_DESCRIPTION, String HACK_KEY, String HACK_TYPE, boolean HACK_NOCHEAT) {
		this.HACK_NAME = HACK_NAME;
		this.HACK_DESCRIPTION = HACK_DESCRIPTION;
		this.HACK_KEY = HACK_KEY;
		this.HACK_TYPE = HACK_TYPE;
		this.HACK_NOCHEAT = HACK_NOCHEAT;
		this.HACK_COL = HACK_COL;
		this.HACK_OPTIONS = new HashMap();
	}

	/** Essential methods. **/
	public void toggle() {
		this.HACK_STATE =! this.HACK_STATE;
		this.utils.log("[Hacks] " + this.getName() + " : " + this.getState());

		if(this.getState()) {
			this.onEnable();
		} else {
			this.onDisable();
		}

		this.files.saveStates();
	}

	protected void onEnable() {
		this.vars.GUI_ARRAY.add("§" + this.getCol() + this.getName().replace("_", " "));
		this.onEnabled();
	}

	protected void onDisable() {
		this.vars.GUI_ARRAY.remove("§" + this.getCol() + this.getName().replace("_", " "));
		this.onDisabled();
	}

	/** Getters. **/
	public String getName() {
		return this.HACK_NAME;
	}

	public String getDescription() {
		return this.HACK_DESCRIPTION;
	}

	public String getKey() {
		return this.HACK_KEY;
	}

	public String getType() {
		return this.HACK_TYPE;
	}

	public boolean getState() {
		return this.HACK_STATE;
	}

	public boolean getNoCheat() {
		return this.HACK_NOCHEAT;
	}

	public char getCol() {
		return this.HACK_COL;
	}

	/** Stuff. **/
	public String getComponents() {
		return "§" + this.getCol() + this.getName().replace("_",  " ");
	}

	public void setColor(char var1) {
		int var2 = this.vars.getGuiArrayIndex("§" + this.getCol() + this.getName().replace("_",  " "));

		if(this.vars.GUI_ARRAY.contains(this.getComponents())) {
			this.vars.GUI_ARRAY.set(var2, "§" + var1 + this.getName().replace("_",  " "));
		}
		
		this.HACK_COL = var1;
	}

	public void setName(String var1) {
		int var2 = this.vars.getGuiArrayIndex("§" + this.getCol() + this.getName().replace("_",  " "));
		
		if(this.vars.GUI_ARRAY.contains(this.getComponents())) {
			this.vars.GUI_ARRAY.set(var2, "§" + this.getCol() + this.getName().replace("_",  " "));
		}
		
	}

	/** Setting the state. **/
	public void setState(boolean var1) {
		this.HACK_STATE = var1;

		if(HACK_STATE) {
			this.onEnable();
		} else {
			this.onDisable();
		}

		this.files.saveStates();
	}

	/**
	 * The method that sets the key for the mod to a new one.
	 * @param newKey - The new keybind that is being set.
	 **/
	public void setKey(String newKey, boolean var1) {
		if(newKey != this.getKey()) {
			this.utils.log("[Hacks] \"" + getName() + "\", old key \"" + this.getKey().toUpperCase() + "\" new key \"" + newKey.toUpperCase() + "\".");

			if(var1) {
				this.utils.addMessage(getName() + "\", old key \"" + this.getKey().toUpperCase() + "\" new key \"" + newKey.toUpperCase() + "\".");
			}

			this.HACK_KEY = newKey.toUpperCase();
			this.files.saveKeys();
		}
	}

	/** Options. **/
	public Object getOption(Base_Options hackoptions) {
		return HACK_OPTIONS.get(hackoptions);
	}

	public void setOption(Base_Options hackoptions, Object obj) {
		HACK_OPTIONS.put(hackoptions, obj);
	}
}