package se.proxus.panels.utils;

import se.proxus.hacks.*;
import se.proxus.panels.*;

public class u_Checkbox extends Base_Util {

	public Base_Panel
	panel = null;

	public u_Checkbox(Base_Panel panel) {
		super(panel.name, 0, 0, 7, 8, 9);
		this.panel = panel;
	}

	@Override
	public void draw(int var1, int var2, int var3) {
		this.drawBorderedRect(this.x + 1F, this.y + 2, this.x + 7F, this.y + 8, 0xFF000000, 0x40000000);
		Base_Panels.ttf.drawStringS((Base_Panels.panelArray.contains(this.panel) ? "§f" : "§7") + this.text, this.x + 10, this.y - 0.5F);

		if(Base_Panels.panelArray.contains(this.panel)) {
			this.mc.fontRenderer.drawString("\u2713", this.x + 2, this.y - 0.5F, 0xFFFFFFFF);
		}
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