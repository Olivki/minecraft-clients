package se.proxus.DreamPvP.Utils.Placeholders;

public class Key {
	
	private String name, command;
	private int key;
	
	public Key(String name, String command, int key) {
		this.name = name;
		this.command = command;
		this.key = key;
	}
	
	/** Getters. **/
	public String getCommand() {
		return command;
	}
	
	public String getName() {
		return name;
	}
	
	public int getKey() {
		return key;
	}
	
	/** Setters. **/
	public void setKey(int newKey) {
		key = newKey;
	}
}