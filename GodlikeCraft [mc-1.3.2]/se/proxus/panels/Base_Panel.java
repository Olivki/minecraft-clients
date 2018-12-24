package se.proxus.panels;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import se.proxus.gui.*;

public abstract class Base_Panel extends Methods {

	public int 
	posX = 0,
	posY = 0,
	x = 0,
	y = 0,
	x2 = 0,
	y2 = 0,
	rectY = 0,
	scrolled = 0,
	mouseClicks = 0;

	public String
	name = "";

	public boolean
	dragging = false,
	open = false,
	buttonsSet = false;

	public ArrayList<Base_Util>
	utilArray = new ArrayList<Base_Util>();

	public Base_Panel(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.x2 = 92;
		this.y2 = 12;
	}

	public Base_Panel(String name, int x, int y, int x2, int y2) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void draw(int var1, int var2) {
		this.mouseDragged(var1, var2);
		
		this.drawBorderedRect(this.x - 0.5F, this.y - 0.5F, this.x + this.x2 + 0.5F, this.y + 12 + (this.open ? this.rectY : 0) + 0.5F, 0xFF000000, 0x40000000);
		this.drawGradientRect(this.x, this.y, this.x + this.x2, this.y + this.y2, 0xFF94918B, 0xFF6E6C68);
		this.drawRect(this.x, this.y + this.y2 - 1.5F, this.x + this.x2, this.y + this.y2, 0xFF565552);
		Base_Panels.ttf.drawString("§0" + this.name, this.x + 2, this.y);
		
		this.rectY = this.utilArray.size() * ((Base_Util)this.utilArray.get(0)).panelSpacing + 3;
	}

	public void mouseDragged(int var1, int var2) {
		if(Mouse.isButtonDown(0) && this.dragging) {
			this.x = var1 - this.posX;
			this.y = var2 - this.posY;
			this.files.savePanelSettings();
		} else {
			this.dragging = false;
		}
	}

	public void mouseClicked(int var1, int var2) {
		if(this.mouseInRec(var1, var2)) {
			this.posX = var1 - this.x;
			this.posY = var2 - this.y;
			this.mouseClicks++;
			this.utils.playSound();
			this.dragging = true;
			Base_Panels.sendPanelToTop(this);
			
			if(this.mouseClicks == 2) {
				this.open =! this.open;
				this.mouseClicks = 0;
			}
		} else {
			this.mouseClicks = 0;
		}

		for(Base_Util var3 : this.utilArray) {
			if(this.open && this.buttonsSet) {
				var3.mouseClicked(var1, var2);
			}
		}
	}

	public void handleMouseInput() {
		try {
			int var1 = Mouse.getEventDWheel();

			if(this.open) {
				if (var1 != 0) {
					if (var1 > 1) {
						var1 = 1;
					}	if (var1 < -1) {
						var1 = -1;
					}

					this.scrolled -= var1;
					
					if(this.scrolled <= 0) {
						this.scrolled = 0;
					} if(this.scrolled > this.utilArray.size()) {
						this.scrolled = this.utilArray.size();
					}
				}
			}
		} catch (Exception e) {

		}
	}

	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}

	public void addUtil(Base_Util var1) {
		if(!(this.utilArray.contains(var1))) {
			this.utilArray.add(var1);
		}
	}
}