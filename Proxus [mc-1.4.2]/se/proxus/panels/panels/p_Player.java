package se.proxus.panels.panels;

import org.lwjgl.opengl.GL11;

import se.proxus.hacks.*;
import se.proxus.panels.*;
import se.proxus.panels.utils.*;

public class p_Player extends Base_Panel {

	public p_Player() {
		super("Player", 1, 1);

		for(Base_Hack var1 : this.hacks.hackArray) {
			if(var1.getType().equalsIgnoreCase(this.name)) {
				this.addUtil(new u_Hack(var1, this.x2 - 4, this, false));
			}
		}
	}

	@Override
	public void draw(int var1, int var2, boolean var3) {
		super.draw(var1, var2, false);
		
		this.rectY = this.utilArray.size() * 12 + this.utilArray.size();
		
		if(this.open) {
			for(int var4 = 0; var4 < this.utilArray.size(); var4++) {
				Base_Util var5 = (Base_Util)this.utilArray.get(var4);
				
				var5.setPosition(this.x + 2, this.y + this.y2 + 1 + var4 * 12 - (this.utilArray.size() >= 3 ? this.scrolled : 0));
				Base_Panels.startCut(this.x, this.y + this.y2, this.x + this.x2, this.y + this.y2 + this.rectY);
				var5.draw(var1, var2, this.scrolled);
				Base_Panels.stopCut();
				
				if(var5.showTag) {
					this.drawRect(var5.x + var5.x2 + 4, var5.y, var5.x + var5.x2 + 4 + this.mc.fontRenderer.getStringWidth(var5.description) + 1, var5.y + var5.y2, 0x40000000);
					this.mc.fontRenderer.drawString(var5.description, var5.x + var5.x2 + 5, var5.y + 1.5F, 0xFFFFFFFF);
				}
				
				this.buttonsSet = true;
			}
		} else {
			this.buttonsSet = false;
		}
	}
}