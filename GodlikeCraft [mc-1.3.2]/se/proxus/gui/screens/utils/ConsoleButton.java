package se.proxus.gui.screens.utils;

import net.minecraft.src.*;
import se.proxus.gui.Methods;
import se.proxus.gui.screens.Console;
import se.proxus.panels.Base_Panels;

public class ConsoleButton extends Methods {

	public int id, x, y, x2, y2;
	public boolean enabled;
	public String text;
	
	public ConsoleButton(int id, String text, int x, int y, int x2, boolean enabled) {
		this.id = id;
		this.text = text;
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = 10;
		this.enabled = enabled;
	}
	
	public void draw(int var1, int var2) {
		drawButtonRect(x, y, x + x2, y + y2, enabled, var1, var2);
		Base_Panels.ttf.drawStringS(text, x + 1, y - 2);
	}
	
	private void drawButtonRect(float x, float y, float x2, float y2, boolean var1, int var2, int var3) {
		this.drawHollowBorderedRect(x, y, x2, y2, 0xFF13161D);
		this.drawHollowBorderedRectCustom(x + 0.5F, y + 0.5F, x2 - 1, y2 - 1, var1 ? 0xFF1A587E : 0xFF4C4C4C, false);
		this.drawHollowBorderedRectCustom(x + 1, y + 1, x2 - 0.5F, y2 - 0.5F, var1 ? 0xFF093B5B : 0xFF2E2E2E, true);
		this.drawGradientRect(x + 1, y + 1, x2 - 1, y2 - 1, var1 ? 0xFF014C7D : 0xFF3F3F3F, var1 ? 0xFF013150 : 0xFF292929);
		
		if(this.mouseInRec(var2, var3) && this.mc.currentScreen instanceof Console) {
			this.drawRect(x, y, x2, y2, 0x40000000);
		}
	}
	
    public boolean mouseInRec(int var1, int var2) {
    	return var1 > x && var2 > y && var1 < x + x2 && var2 < y + y2;
    }
}