package se.proxus.mods;

import se.proxus.*;

public class ModInfo {
	
	protected String description;
	
	protected String author;
	
	protected String key;
	
	protected boolean nocheat;
	
	protected BaseMod mod;
	
	public ModInfo(String description, String author, String key, boolean nocheat) {
		this.description = description;
		this.author = author;
		this.key = key.toUpperCase();
		this.nocheat = nocheat;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public boolean getNoCheat() {
		return this.nocheat;
	}
	
	public void setKey(String var0, boolean var1) {
		if(!(var0.equalsIgnoreCase(this.getKey()))) {
			this.key = var0;
			
			Pendrum.utils.log("Mod", this.mod.getName() + " key: " + var0);
			
			if(var1) {
				this.mod.config.saveConfig();
			}
		}
	}
	
	public void setMod(BaseMod var0) {
		this.mod = var0;
	}
}