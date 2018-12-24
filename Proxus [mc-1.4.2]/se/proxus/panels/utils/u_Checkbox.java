package se.proxus.panels.utils;

import se.proxus.hacks.*;
import se.proxus.panels.*;

public class u_Checkbox extends Base_Util {

	public Base_Panel
	panel = null;

	public u_Checkbox(Base_Panel panel, int x2) {
		super(panel.name, 0, 0, x2, 11, 12, "");
		this.panel = panel;
	}

	@Override
	public void draw(int var1, int var2, int var3) {
		this.drawRect(this.x, this.y, this.x + this.x2, this.y + this.y2, Base_Panels.panelArray.contains(this.panel) ? 0x60007143 : 0x60960028);
		
		if(this.mouseInRec(var1, var2) && this.mc.currentScreen instanceof Base_Panels) {
			this.drawRect(this.x, this.y, this.x + this.x2, this.y + this.y2, 0x20000000);
		}
		
		this.drawCenteredString(this.mc.fontRenderer, this.panel.name, this.x + this.x2 / 2, this.y + 1.5F, 0xFFFFFFFF, false);
	}
	
	@Override
	public void mouseClicked(int var1, int var2) {
		if(this.mouseInRec(var1, var2)) {
			this.utils.playSound();

			if(!(Base_Panels.panelArray.contains(this.panel))) {
				Base_Panels.panelArray.add(this.panel);
			} else {
				Base_Panels.panelArray.remove(this.panel);
			}
		}
	}

	@Override
	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x + 1 && var2 >= this.y + 2 && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}
}