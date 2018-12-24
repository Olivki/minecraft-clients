package se.proxus.panels;

import se.proxus.gui.*;

public abstract class Base_Util extends Methods {
	
	public int
	x = 0,
	y = 0,
	x2 = 0,
	y2 = 0,
	panelSpacing = 0;
	
	public String
	text = "";
	
	public boolean 
	state = false;
	
	public Base_Util(String text, int x, int y, int panelSpacing) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.x2 = 92;
		this.y2 = 12;
		this.panelSpacing = panelSpacing;
	}
	
	public Base_Util(String text, int x, int y, int x2, int y2, int panelSpacing) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.panelSpacing = panelSpacing;
	}

	public abstract void draw(int var1, int var2, int var3);
	
	public abstract void mouseClicked(int var1, int var2);
	
	protected void drawButtonRect(float x, float y, float x2, float y2, boolean var1, int var2, int var3) {
		this.drawHollowBorderedRect(x, y, x2, y2, 0xFF13161D);
		this.drawHollowBorderedRectCustom(x + 0.5F, y + 0.5F, x2 - 1, y2 - 1, var1 ? 0xFF1A587E : 0xFF4C4C4C, false);
		this.drawHollowBorderedRectCustom(x + 1, y + 1, x2 - 0.5F, y2 - 0.5F, var1 ? 0xFF093B5B : 0xFF2E2E2E, true);
		this.drawGradientRect(x + 1, y + 1, x2 - 1, y2 - 1, var1 ? 0xFF014C7D : 0xFF3F3F3F, var1 ? 0xFF013150 : 0xFF292929);
		
		if(this.mouseInRec(var2, var3) && this.mc.currentScreen instanceof Base_Panels) {
			this.drawRect(x, y, x2, y2, 0x40000000);
		}
	}
	
	public boolean mouseInRec(int var1, int var2) {
		return var1 >= this.x && var2 >= this.y && var1 <= this.x + this.x2 && var2 <= this.y + this.y2;
	}
	
	public void setPosition(int var1, int var2) {
		this.x = var1;
		this.y = var2;
	}
}