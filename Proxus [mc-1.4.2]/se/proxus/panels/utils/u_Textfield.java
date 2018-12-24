package se.proxus.panels.utils;

import net.minecraft.src.ChatAllowedCharacters;
import se.proxus.hacks.*;
import se.proxus.panels.Base_Panel;
import se.proxus.panels.Base_Panels;
import se.proxus.panels.Base_Util;

public class u_Textfield extends Base_Util {

	public String
	name = "",
	text = "",
	tick = "";

	public boolean
	selected = false,
	shouldClear = false;

	public Base_Panel
	panel = null;
	
	public int
	updateTick = 0,
	textLenght = 12;

	public u_Textfield(String name, int x2, Base_Panel panel, boolean shouldClear) {
		super(name, 0, 0, x2, 11, 9, "");
		this.panel = panel;
		this.shouldClear = shouldClear;
	}
	
	public u_Textfield(String name, int x2, Base_Panel panel, boolean shouldClear, int textLenght) {
		super(name, 0, 0, x2, 11, 9, "");
		this.panel = panel;
		this.shouldClear = shouldClear;
		this.textLenght = textLenght;
	}
	
	public u_Textfield(String name, int x2, Base_Panel panel, boolean shouldClear, int textLenght, String description) {
		super(name, 0, 0, x2, 11, 9, description);
		this.panel = panel;
		this.shouldClear = shouldClear;
		this.textLenght = textLenght;
	}

	public u_Textfield(String name, String text, int x2, Base_Panel panel, boolean shouldClear) {
		super(name, 0, 0, x2, 11, 9, "");
		this.text = text;
		this.panel = panel;
		this.shouldClear = shouldClear;
	}
	
	public u_Textfield(String name, String text, int x2, Base_Panel panel, boolean shouldClear, int textLenght) {
		super(name, 0, 0, x2, 11, 9, "");
		this.text = text;
		this.panel = panel;
		this.shouldClear = shouldClear;
		this.textLenght = textLenght;
	}
	
	public u_Textfield(String name, String text, int x2, Base_Panel panel, boolean shouldClear, int textLenght, String description) {
		super(name, 0, 0, x2, 11, 9, description);
		this.text = text;
		this.panel = panel;
		this.shouldClear = shouldClear;
		this.textLenght = textLenght;
	}

	@Override
	public void draw(int var1, int var2, int var3) {
		this.drawRect(this.x, this.y, this.x + this.x2, this.y + this.y2 - 0.5F, 0x40000000);
		this.mc.fontRenderer.drawString(this.text + this.tick, this.x + 2, this.y + 1.5F, 0xFFFFFFFF);
		
		if(this.selected) {
			this.updateTick++;
		} else {
			this.updateTick = 0;
			this.tick = "";
		}
		
		if(this.mouseInRec(var1, var2)) {
			this.hoverTime++;
		} else {
			this.hoverTime = 0;
			this.showTag = false;
		}
		
		if(this.updateTick >= 20) {
			this.tick = "|";
		} if(this.updateTick >= 40) {
			this.tick = "";
		} if(this.updateTick >= 60) {
			this.updateTick = 0;
		} if(this.hoverTime >= 80) {
			this.showTag = true;
		}
	}

	@Override
	public void mouseClicked(int var1, int var2) {
		if(this.mouseInRec(var1, var2) && this.y >= this.panel.y + this.panel.y2 && this.y + this.y2 <= this.panel.y + this.panel.y2 + this.panel.rectY) {
			this.utils.playSound();
			this.selected = true;
		} else {
			this.selected = false;
		}
	}

	@Override
	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}

	@Override
	public void keyTyped(char var1, int var2) {
		if(this.selected) {
			if(var2 == 28) {
				String s = text.trim();

				if(s.length() > 0) {
					this.panel.onEntered(this.name, this.text, this);

					if(this.shouldClear) {
						this.text = "";
					}
				}

				return;
			}

			if(var2 == 14 && text.length() > 0) {
				text = text.substring(0, text.length() - 1);
			}

			if((ChatAllowedCharacters.allowedCharacters.indexOf(var1) >= 0 || var1 > ' ') && text.length() <= textLenght) {
				text += var1;
			}
		}
	}
}