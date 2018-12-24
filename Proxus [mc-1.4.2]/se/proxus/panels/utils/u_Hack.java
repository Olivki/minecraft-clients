package se.proxus.panels.utils;

import se.proxus.hacks.*;
import se.proxus.panels.*;

public class u_Hack extends Base_Util {

	public Base_Hack
	hack = null;

	public Base_Panel
	panel = null,
	extraPanel = null;

	public boolean
	hasExtra = false;

	public u_Hack(Base_Hack hack, int x2, Base_Panel panel, boolean hasExtra) {
		super(hack.getName().replace("_", " "), 0, 0, x2, 11, 10, hack.getDescription());
		this.hack = hack;
		this.panel = panel;
		this.hasExtra = hasExtra;
	}

	public u_Hack(Base_Hack hack, int x2, Base_Panel panel, Base_Panel extraPanel) {
		super(hack.getName().replace("_", " "), 0, 0, x2, 11, 10, hack.getDescription());
		this.hack = hack;
		this.panel = panel;
		this.hasExtra = true;
		this.extraPanel = extraPanel;
	}

	@Override
	public void draw(int var1, int var2, int var3) {
		this.drawRect(this.x, this.y, this.x + this.x2, this.y + this.y2, this.hack.getState() ? 0x60007143 : 0x60960028);

		if(this.mouseInRec(var1, var2) && this.mc.currentScreen instanceof Base_Panels && this.y >= this.panel.y + this.panel.y2 && this.y + this.y2 <= this.panel.y + this.panel.y2 + this.panel.rectY) {
			this.drawRect(this.x, this.y, this.x + this.x2, this.y + this.y2, 0x20000000);
			this.hoverTime++;
		} else {
			this.hoverTime = 0;
			this.showTag = false;
		} if(this.hoverTime >= 80) {
			this.showTag = true;
		}

		this.drawCenteredString(this.mc.fontRenderer, this.hack.getName(), this.x + this.x2 / 2, this.y + 1.5F, 0xFFFFFFFF, false);

		if(this.hasExtra) {
			if(this.mouseInRec(var1, var2, 0) && this.mc.currentScreen instanceof Base_Panels) {
				this.drawRect(this.x + this.x2 + 1, this.y, this.x + this.x2 + 8, this.y + this.y2, 0x20000000);
			}

			this.drawRect(this.x + this.x2 + 1, this.y, this.x + this.x2 + 8, this.y + this.y2, Base_Panels.panelArray.contains(this.extraPanel) ? 0x60007143 : 0x60960028);
			this.mc.fontRenderer.drawString((Base_Panels.panelArray.contains(this.extraPanel) ? "<" : ">"), this.x + this.x2 + 2.5F, this.y + 2, 0xFFFFFFFF);
		}
	}
	
	@Override
	public void mouseClicked(int var1, int var2) {
		if(this.mouseInRec(var1, var2) && this.y >= this.panel.y + this.panel.y2 && this.y + this.y2 <= this.panel.y + this.panel.y2 + this.panel.rectY) {
			this.utils.playSound();
			this.hack.toggle();
		}

		if(this.hasExtra) {
			if(this.mouseInRec(var1, var2, 0)) {
				this.utils.playSound();

				if(!(Base_Panels.panelArray.contains(this.extraPanel))) {
					Base_Panels.panelArray.add(Base_Panels.panelArray.size(), this.extraPanel);
				} else {
					Base_Panels.panelArray.remove(Base_Panels.panelArray.indexOf(this.extraPanel));
				}
			}
		}
	}

	@Override
	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}
	
	public boolean mouseInRec(int var1, int var2, int var3) {
		return var1 >= this.x + this.x2 + 1 && var2 >= this.y && var1 <= this.x + this.x2 + 8 && var2 <= this.y + this.y2;
	}

	public boolean mouseInRec(int var1, int var2, int x, int y, int x2, int y2) {
		return var1 >= x && var2 >= y && var1 <= x + x2 && var2 <= y + y2;
	}
}