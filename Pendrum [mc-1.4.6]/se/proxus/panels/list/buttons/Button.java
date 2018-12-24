package se.proxus.panels.list.buttons;

import se.proxus.*;
import se.proxus.gui.*;
import se.proxus.mods.*;
import se.proxus.panels.*;

public class Button extends BaseButton {

	public Button(String name, ButtonInfo info) {
		super(name, info);
	}

	@Override
	public void draw(int var0, int var1, TrueTypeFont var2) {
		/** COLORS START **/
		this.getColors().setColors(var0, var1);
		/** COLORS STOP **/
		
		/** DRAWING START **/
		this.drawBorderedRect(this.getInfo().getX() + this.getInfo().getWidth() - 38, this.getInfo().getY() + 1, this.getInfo().getX() + this.getInfo().getWidth(), this.getInfo().getY() + this.getInfo().getHeight() - 1, 1.5F, this.getColors().BUTTON_BORDER, this.getColors().BUTTON_INNER);
		
		var2.drawStringWithShadow(this.getInfo().getState() ? "Enabled" : "Disabled", this.getInfo().getX() + this.getInfo().getWidth() - 27 - this.pm.ttf.getStringWidth(this.getInfo().getState() ? "Enabled" : "Disabled") / 4, this.getInfo().getY() + 2.5F, this.getColors().BUTTON_NAME);
		
		var2.ttf.drawStringWithShadow(this.getName().replace("_", " "), this.getInfo().getX() - 2, this.getInfo().getY() + 2, this.getColors().BUTTON_NAME);
		/** DRAWING STOP **/
	}

	@Override
	public void mouseClicked(int var0, int var1, int var2) {
		/** CLICKING START **/
		if(this.isHovering(var0, var1) && var2 == 0) {
			Pendrum.utils.playSound();
			this.onClicked();
		}
		/** CLICKING STOP **/
	}
	
	public void onClicked() {};
	
	public boolean isHovering(int var0, int var1) {
		return var0 >= this.getInfo().getX() + this.getInfo().getWidth() - 38 && var1 >= this.getInfo().getY() + 1 && var0 <= this.getInfo().getX() + this.getInfo().getWidth() && var1 <= this.getInfo().getY() + this.getInfo().getHeight() - 1;
	}
}