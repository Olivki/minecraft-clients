package se.proxus.DreamPvP.Mods;

import org.lwjgl.input.Keyboard;

import se.proxus.DreamPvP.DreamPvP;

public abstract class Base_Mod {
	
	public abstract void onEnabled();
	
	public abstract void onDisabled();
	
	protected DreamPvP dp = new DreamPvP();
	
	protected int key;
	protected String name, desc, menu, extra;
	protected char col;
	protected boolean enabled;
	
	public Base_Mod(char col, String name, String desc, int key, String menu, String extra) {
		this.col = col;
		this.name = name;
		this.desc = desc;
		this.key = key;
		this.menu = menu;
		this.extra = extra;
	}
	
	protected void onKeyPressed() {
		if(getEventKey(getKey())) {
			toggle();
			playSound(getState());
		}
	}
	
	public void toggle() {
		enabled =! enabled;
		dp.bools.toggle(getName());
		
		if(enabled) {
			onEnable();
		} else {
			onDisable();
		}
		
		//dp.files.saveModStates();
	}
	
	protected void onEnable() {
		dp.ingame.guiArray.add(getName());
		onEnabled();
	}
	
	protected void onDisable() {
		dp.ingame.guiArray.remove(getName());
		onDisabled();
	}
	
	/** Setters. **/
	public void setKey(int newKey) {
		dp.utils.log("The keybind for the mod \"" + getName() + "\" has been changed from \"" + Keyboard.getKeyName(getKey()) + "\" to \"" + Keyboard.getKeyName(newKey) + "\".");
		key = newKey;
	}
	
	public void setState(boolean newState) {
		dp.utils.log("The state for the mod \"" + getName() + "\" has changed from \"" + getState() + "\" to \"" + newState + "\".");
		dp.bools.setState(getName(), newState);
	}
	
	/** Getters. **/
	public char getCol() {
		return col;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String getMenu() {
		return menu;
	}
	
	public String getExtra() {
		return extra;
	}
	
	public int getKey() {
		return key;
	}
	
	public boolean getState() {
		return dp.bools.getState(getName());
	}
	
	/** Misc methods. **/
	protected boolean getEventKey(int key) {
		return Keyboard.getEventKey() == key;
	}
	
	protected void playSound(boolean mod) {
		dp.mc.sndManager.playSoundFX("random.click", 1.0F, mod ? 0.6F : 1.1F);
	}
}