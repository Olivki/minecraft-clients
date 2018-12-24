package se.proxus.panels.utils;

import se.proxus.hacks.*;
import se.proxus.panels.Base_Panel;
import se.proxus.panels.Base_Panels;
import se.proxus.panels.Base_Util;

public class u_Extra extends Base_Util {
	
	public String
	name = "";
	
	public Base_Panel
	panel = null;
	
	public int 
	id = 0;
	
	public u_Extra(int x, int x2, Base_Util util, Base_Panel panel, int id) {
		super("", x, 1, x2, 11, 9, "");
		this.panel = panel;
		this.id = id;
	}

	@Override
	public void draw(int var1, int var2, int var3) {
		if(this.mouseInRec(var1, var2) && this.mc.currentScreen instanceof Base_Panels && this.y >= this.panel.y + this.panel.y2 && this.y + this.y2 <= this.panel.y + this.panel.y2 + this.panel.rectY) {
			this.drawRect(this.x, this.y, this.x + this.x2, this.y + this.y2, 0x20000000);
		}
		
		this.drawRect(this.x, this.y, this.x + this.x2, this.y + this.y2, Base_Panels.panelArray.contains(this.panel) ? 0x60007143 : 0x60960028);
	}
	
	@Override
	public void mouseClicked(int var1, int var2) {
		if(this.mouseInRec(var1, var2)) {
			this.utils.playSound();
			
			if(!(Base_Panels.panelArray.contains(this.panel))) {
				this.panel.extraId = this.id;
				Base_Panels.panelArray.add(this.panel);
			} else {
				Base_Panels.panelArray.remove(this.panel);
				this.panel.extraId = 0;
			}
		}
	}
	
	@Override
	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}
}