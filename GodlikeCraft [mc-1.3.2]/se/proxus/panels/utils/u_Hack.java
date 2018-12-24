package se.proxus.panels.utils;

import se.proxus.hacks.*;
import se.proxus.panels.Base_Panels;
import se.proxus.panels.Base_Util;

public class u_Hack extends Base_Util {
	
	public Base_Hack
	hack = null;

	public u_Hack(Base_Hack hack) {
		super(hack.getName().replace("_", " "), 0, 0, 18, 9, 9);
		this.hack = hack;
	}

	@Override
	public void draw(int var1, int var2, int var3) {
		Base_Panels.ttf.drawStringS((this.hack.getState() ? "§f" : "§7") + text, this.x + 20, this.y - 0.5F);
		this.drawButtonRect(this.x, this.y + 1, this.x + this.x2, this.y + this.y2, this.hack.getState(), var1, var2);
	}

	@Override
	public void mouseClicked(int var1, int var2) {
		if(this.mouseInRec(var1, var2)) {
			this.utils.playSound();
			this.hack.toggle();
		}
	}
	
	@Override
	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}
}