package se.proxus.panels;

import se.proxus.gui.*;

public abstract class Base_Util extends Methods {

	public int
	x = 0,
	y = 0,
	x2 = 0,
	y2 = 0,
	panelSpacing = 0,
	hoverTime = 0;

	public String
	text = "",
	description = "";

	public boolean 
	state = false,
	showTag = false;

	public Base_Util(String text, int x, int y, int panelSpacing, String description) {
		this.text = text;
		this.description = description;
		this.x = x;
		this.y = y;
		this.x2 = 92;
		this.y2 = 12;
		this.panelSpacing = panelSpacing;
	}

	public Base_Util(String text, int x, int y, int x2, int y2, int panelSpacing, String description) {
		this.text = text;
		this.description = description;
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.panelSpacing = panelSpacing;
	}

	public abstract void draw(int var1, int var2, int scrolled);

	public abstract void mouseClicked(int var1, int var2);

	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}

	public void setPosition(int var1, int var2) {
		this.x = var1;
		this.y = var2;
	}

	public void keyTyped(char var1, int var2) {};
}