package se.proxus.panels;

import net.minecraft.src.*;
import se.proxus.gui.*;

public abstract class BaseButton extends Methods {
	
	protected String name;

	protected ButtonInfo info;
	
	protected ButtonColors colors;
	
	public BaseButton(String name, ButtonInfo info) {
		this.setName(name);
		this.setInfo(info);
		this.setColors(new ButtonColors(this.getInfo(), this));
	}
	
	public abstract void draw(int var0, int var1, TrueTypeFont var2);
	
	public abstract void mouseClicked(int var0, int var1, int var2);
	
	public boolean isHovering(int var0, int var1) {
		return var0 >= this.getInfo().getX() && var1 >= this.getInfo().getY() && var0 <= this.getInfo().getX() + this.getInfo().getWidth() && var1 <= this.getInfo().getY() + this.getInfo().getHeight();
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ButtonInfo getInfo() {
		return this.info;
	}

	public void setInfo(ButtonInfo info) {
		this.info = info;
	}

	public ButtonColors getColors() {
		return this.colors;
	}

	public void setColors(ButtonColors colors) {
		this.colors = colors;
	}
}