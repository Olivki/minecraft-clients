package se.proxus.mods;

import se.proxus.utils.*;
import net.minecraft.src.*;

public enum ModType {
	
	COMBAT(0, Colors.RED, "Combat"),
	WORLD(1, Colors.GREY, "World"),
	PLAYER(2, Colors.GOLD, "Player"),
	RENDER(3, Colors.YELLOW, "Render"),
	SERVER(4, Colors.INDIGO, "Server"),
	GUI(5, Colors.WHITE, "Gui"),
	NONE(6, Colors.BLACK, "None"),
	MISC(7, Colors.STRIKE, "Misc");
	
	int id;
	
	String color;
	
	String name;
	
	private ModType(int id, String color, String name) {
		this.id = id;
		this.color = color;
		this.name = name;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getName() {
		return this.name;
	}
	
	/** Checks if the type is equal to yolo. **/
	public boolean[] getType() {
		return (new boolean[] {
			this == this.COMBAT,
			this == this.WORLD,
			this == this.PLAYER,
			this == this.RENDER,
			this == this.SERVER,
			this == this.GUI,
			this == this.NONE,
			this == this.MISC
		});
	}
}