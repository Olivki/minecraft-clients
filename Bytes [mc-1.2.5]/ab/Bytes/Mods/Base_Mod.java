package ab.Bytes.Mods;

import org.lwjgl.input.Keyboard;

import ab.Bytes.*;

public abstract class Base_Mod {

	protected Bytes bs = new Bytes();

	protected boolean enabled;
	protected String modName;
	protected int modKey;
	protected char modCol;

	public Base_Mod(char col, String name, int key) {
		modCol = col;
		modName = name;
		modKey = key;
	}

	public abstract void onEnabled();

	public abstract void onDisabled();

	protected void onEnable() {
		bs.ingame.guiArray.add("§" + getCol() + getName());
		enabled = true;
		onEnabled();
	}

	protected void onDisable() {
		bs.ingame.guiArray.remove("§" + getCol() + getName());
		enabled = false;
		onDisabled();
	}

	public void toggle() {
		enabled =! enabled;
		bs.bools.toggle(getName());

		if(enabled) {
			onEnable();
		} else {
			onDisable();	
		}
	}

	protected void onKeyEvent() {
		if(keyEvent(modKey)) {
			toggle();
			playSound(isEnabled());
		}
	}

	protected boolean keyEvent(int jew) {
		return Keyboard.getEventKey() == jew;
	}

	protected void playSound(boolean nazi) {
		bs.mc.sndManager.playSoundFX("random.click", 1.0F, nazi ? 0.6F : 1.1F);
	}

	public String getName() {
		return modName;
	}

	public int getKey() {
		return modKey;
	}
	
	public boolean isEnabled() {
		return bs.bools.getState(getName());
	}
	
	public char getCol() {
		return modCol;
	}
}