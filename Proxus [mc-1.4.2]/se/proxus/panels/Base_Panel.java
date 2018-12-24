package se.proxus.panels;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import se.proxus.gui.*;
import se.proxus.panels.utils.*;

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
	scrolledV = 0,
	mouseClicks = 0,
	scrollY = 0,
	extraId = 0;

	public String
	name = "";

	public boolean
	dragging = false,
	open = false,
	buttonsSet = false,
	pinned = false,
	scrolling = false,
	extraOpen = false;

	public ArrayList<Base_Util>
	utilArray = new ArrayList<Base_Util>();
	
	public u_Textfield
	selectedField = null;

	public Base_Panel(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.x2 = 110;
		this.y2 = 12;
	}

	public Base_Panel(String name, int x, int y, int x2, int y2) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void draw(int var1, int var2, boolean var3) {
		this.mouseDragged(var1, var2);

		this.drawRect(this.x - 0.5F, this.y - 0.5F, this.x + this.x2 + 0.5F, this.y + this.y2, 0x60000000);

		if(this.mc.currentScreen instanceof Base_Panels) {
			this.drawRect(this.x + this.x2 - 9, this.y + 2, this.x + this.x2 - 1,  this.y + this.y2 - 2, this.pinned ? 0x80007143 : 0x80960028);
		}

		if(var1 >= this.x + this.x2 - 9 && var2 >= this.y + 2 && var1 <= this.x + this.x2 - 1 && var2 <= this.y + this.y2 - 2 && this.mc.currentScreen instanceof Base_Panels) {
			this.drawRect(this.x + this.x2 - 9, this.y + 2, this.x + this.x2 - 1,  this.y + this.y2 - 2, 0x20000000);
		}

		if(this.open) {
			this.drawRect(this.x - 0.5F, this.y + this.y2, this.x + this.x2 + 0.5F, this.y + this.y2 + (this.open ? this.rectY : 0), 0x40000000);
			/*this.drawRect(this.x + this.x2 - 8.0F, this.y + this.y2 + 1.0F, this.x + this.x2 - 2.0F, this.y + this.y2 + this.rectY - 1.5F, 0x40000000);
			this.drawRect(this.x + this.x2 - 7.5F, this.y + this.y2 + 1.5F + this.scrolledV, this.x + this.x2 - 2.5F, this.y + this.y2 + 5.0F + this.scrolledV, this.scrolling ? 0x60007143 : 0x60960028);*/
		}

		this.mc.fontRenderer.drawString(this.name, this.x + 1, this.y + 2, 0xFFFFFFFF);
	}

	public void mouseDragged(int var1, int var2) {
		if(Mouse.isButtonDown(0) && this.dragging) {
			this.x = var1 - this.posX;
			this.y = var2 - this.posY;
			//this.files.savePanelSettings();
		} else {
			this.dragging = false;
		} /*if(Mouse.isButtonDown(0) && this.scrolling) {
			int size = this.utilArray.size() * 12 + 1;
			this.scrolledV = var2 - ((int)(size / this.rectY - 1.5F) * this.scrollY);
			
			this.utils.log(this.scrolled + " || " + this.rectY);
			
			if(this.scrolledV < 0) {
				this.scrolledV = 0;
			} if(this.scrolledV > 33) {
				this.scrolledV = 33;
			}
			
			this.scrolled = var2 - this.scrolledV;
		} else {
			this.scrolling = false;
		}*/
	}
	
	public void mouseClicked(int var1, int var2) {
		if(this.mouseInRec(var1, var2)) {
			this.posX = var1 - this.x;
			this.posY = var2 - this.y;
			this.mouseClicks++;
			this.utils.playSound();
			this.dragging = true;
			Base_Panels.sendPanelToTop(this);
			Base_Panels.current = this;
			
			if(this.mouseClicks == 2) {
				this.open =! this.open;
				this.mouseClicks = 0;
			}
		} else {
			this.mouseClicks = 0;
		}

		if(var1 >= this.x + this.x2 - 9 && var2 >= this.y + 2 && var1 <= this.x + this.x2 - 1 && var2 <= this.y + this.y2 - 2) {
			this.pinned =! this.pinned;
			this.utils.playSound();
			Base_Panels.sendPanelToTop(this);
			this.mouseClicks = 0;
		}
		
		//this.x + this.x2 - 8.0F, this.y + this.y2 + 1.0F, this.x + this.x2 - 2.0F, this.y + this.y2 + this.rectY - 1.5F
		
		/*if(var1 >= this.x + this.x2 - 8.0F && var2 >= this.y + this.y2 + 1.0F && var1 <= this.x + this.x2 - 2.0F && var2 <= this.y + this.y2 + this.rectY - 2.5F && this.utilArray.size() >= 3) {
			this.scrollY = var2 - this.scrolled;
			//this.scrolled = var2 - this.y + this.y2 - this.rectY;
			this.scrolling = true;
			this.utils.playSound();
			Base_Panels.sendPanelToTop(this);
			this.mouseClicks = 0;
		}*/

		for(Base_Util var3 : this.utilArray) {
			if(this.open && this.buttonsSet) {
				var3.mouseClicked(var1, var2);
			}
		}
	}

	public void handleMouseInput() {
		try {
			/*if(Base_Panels.current == this) {
				int var1 = Mouse.getEventDWheel();

				if(this.open) {
					if(var1 != 0) {
						if(var1 > 1) {
							var1 = 1;
						} if(var1 < -1) {
							var1 = -1;
						}

						var1 *= 7;
						
						this.scrolled -= var1;

						if(this.scrolled <= 0) {
							this.scrolled = 0;
						} if(this.scrolled > this.utilArray.size() + 10) {
							this.scrolled = this.utilArray.size() + 10;
						}
					}
				}
			}*/
		} catch (Exception e) {

		}
	}
	
	public void keyTyped(char var1, int var2) {
		for(Base_Util var3 : this.utilArray) {
			var3.keyTyped(var1, var2);
		}
	}

	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 - 11 && var2 <= this.y + this.y2;
	}

	public boolean mouseInRec(int var1, int var2, int x, int y, int x2, int y2) {
		return var1 >= x && var2 >= y && var1 <= x + x2 && var2 <= y + y2;
	}

	public void addUtil(Base_Util var1) {
		if(!(this.utilArray.contains(var1))) {
			this.utilArray.add(var1);
		}
	}
	
	public void onEntered(String var1, String var2, u_Textfield var3) {};
	
	public void buttonPressed(u_Button var1, int var2) {};
}