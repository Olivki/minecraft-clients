package se.proxus.panels.utils;

import se.proxus.hacks.*;
import se.proxus.panels.Base_Panels;
import se.proxus.panels.Base_Util;

public class u_Button extends Base_Util {
	
	public String
	name = "";
	
	public boolean
	state = false;
	
	public u_Button(String name, boolean state, int x2) {
		super(name, 0, 0, x2, 9, 9);
		this.name = name;
		this.state = state;
	}

	@Override
	public void draw(int var1, int var2, int var3) {
		Base_Panels.ttf.drawStringS(this.name, this.x + 2, this.y - 0.5F);
		this.drawButtonRect(this.x, this.y, this.x + this.x2, this.y + this.y2, this.state, var1, var2);
	}

	@Override
	public void mouseClicked(int var1, int var2) {
		if(this.mouseInRec(var1, var2)) {
			this.utils.playSound();
		}
	}
	
	@Override
	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}
}