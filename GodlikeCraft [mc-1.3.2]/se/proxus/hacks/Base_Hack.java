package se.proxus.hacks;

import java.util.HashMap;

import net.minecraft.src.Achievement;
import net.minecraft.src.Block;

import org.lwjgl.input.Keyboard;

import se.proxus.*;
import se.proxus.panels.Base_Panels;

public abstract class Base_Hack extends GodlikeCraft {
	
    private HashMap options;

	/** The abstract methods. **/
	/** The method that gets activated when the mod is enabled. **/
	public abstract void onEnabled();

	/** The methods gets activated when the mod is disabled. **/
	public abstract void onDisabled();

	/** The method that gets activated when then mod gets toggled. **/
	public abstract void onToggled();

	/** The method that gets activated on every tick. **/
	public abstract void onUpdate();
	
	/** The method that gets activated on every render tick. **/
	public void onRendered3D(){};
	
	/** The method for getting special naming for the panels. **/
	public String getPanelNaming() {return getName();};

	/** The method for getting custom Strings from the mod. **/
	public abstract String[] getModString();

	/** The method for getting custom Integers from the mod. **/
	public abstract int[] getModInteger();

	/** The method for getting custom Floats from the mod. **/
	public abstract float[] getModFloat();

	/** The method for getting custom Longs from the mod. **/
	public abstract long[] getModLong();

	/** The method for getting custom Booleans from the mod. **/
	public abstract boolean[] getModBoolean();

	/** The variables. **/
	/** The String that defines the name of the mod. (Can be accessed using the method getName().) **/
	protected String name; 

	/** The String that defines the description of the mod. (Can be accessed using the method getDescription().) **/
	protected String description;

	/** The String that defines the key of the mod. (Can be accessed using the method getKey() (Returns a integer.)) **/
	protected String key;
	
	/** The String that defines the section of the mod. (Can be accessed using the method getKey()) **/
	protected String section;

	/** The char that defines the colour of the mod. (Can be accessed using the method getCol().) **/
	protected char col;

	/** The boolean that defines the state of the mod. (Can be accessed using the method getState().) **/
	protected boolean state;

	/** 
	 * The constructor that defines most stuff. 
	 * @param Col - The colour of the mod.
	 * @param Name - The name of the mod.
	 * @param Description - The description of the mod.
	 * @param Key - The key for the mod. 
	 **/
	public Base_Hack(char col, String name, String description, String section, String key) {
		this.col = col;
		this.name = name;
		this.description = description;
		this.section = section;
		this.key = key;
		
        options = new HashMap();
	}

	/** The essential methods. **/
	/** The method that toggles the mod. **/
	public void toggle() {
		/** Toggles the boolean state. **/
		state =! state;
		
		/** Saves the hacks states. **/
		files.saveStates();

		/** Runs the method onToggled(). **/
		onToggled();

		if(state) {
			/** Runs the method onEnable(). **/
			onEnable();
		} else {
			/** Runs the method onDisable(). **/
			onDisable();
		}
	}

	/** The method that gets activated when the mod is enabled. **/
	protected void onEnable() {
		ingame.guiArray.add("§" + this.getCol() + this.getName().replace("_",  " "));

		onEnabled();
	}

	/** The method that gets activated when the mod is disabled. **/
	protected void onDisable() {
		ingame.guiArray.remove("§" + this.getCol() + this.getName().replace("_",  " "));

		onDisabled();
	}

	/** The setters. **/
	/**
	 * The method that sets the key for the mod to a new one.
	 * @param newKey - The new keybind that is being set.
	 **/
	public void setKey(int newKey, boolean var1) {
		if(newKey != this.getKey()) {
			this.utils.log("[Hacks] \"" + getName() + "\", old key \"" + Keyboard.getKeyName(getKey()).toUpperCase() + "\" new key \"" + Keyboard.getKeyName(newKey).toUpperCase() + "\".");

			if(var1) {
				this.utils.addChat("\"" + getName() + "\", old key \"" + Keyboard.getKeyName(getKey()).toUpperCase() + "\" new key \"" + Keyboard.getKeyName(newKey).toUpperCase() + "\".");
			}

			this.key = Keyboard.getKeyName(newKey).toUpperCase();
		}
	}

	/** The getters. **/
	/** The method for getting the name of the mod. **/
	public String getName() {
		return name;
	}

	/** The method for getting the description of the mod. **/
	public String getDescription() {
		return description;
	}
	
	/** The method for getting the section of the mod. **/
	public String getSection() {
		return section;
	}

	/** The method for getting the key of the mod. **/
	public int getKey() {
		return Keyboard.getKeyIndex(key.toUpperCase());
	}

	/** The method for getting the color of the mod. **/
	public char getCol() {
		return col;
	}

	/** The method for getting the current state of the mod. **/
	public boolean getState() {
		return state;
	}

	/** Misc methods. **/
	/**
	 * The method for playing the click sound when toggled.
	 * @param flag - The boolean that changes the pitch of the sound depending on its state.
	 **/
	public void playSound(boolean flag) {
		this.mc.sndManager.playSoundFX("random.click", 1.0F, state ? 0.6F : 1.1F);
	}
	
	/** Setting the state. **/
	public void setState(boolean var1) {
		this.state = var1;
		
		if(state) {
			onEnable();
		} else {
			onDisable();
		}
	}
	
	/** Options. **/
    public Object getOption(Base_Options hackoptions) {
        return options.get(hackoptions);
    }

    public void setOption(Base_Options hackoptions, Object obj) {
        options.put(hackoptions, obj);
    }
}