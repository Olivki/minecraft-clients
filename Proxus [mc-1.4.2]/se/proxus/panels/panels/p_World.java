package se.proxus.panels.panels;

import org.lwjgl.opengl.GL11;

import se.proxus.hacks.*;
import se.proxus.panels.*;
import se.proxus.panels.utils.*;

public class p_World extends Base_Panel {

	public p_World() {
		super("World", 1, 1);

		/*for(Base_Hack var1 : this.hacks.hackArray) {
			if(var1.getType().equalsIgnoreCase(this.name)) {
				this.addUtil(new u_Hack(var1, this.x2 - 4, this));
			}
		}*/
		
		this.addUtil(new u_Hack(this.hacks.HACK_MINER, this.x2 - 12, this, Base_Panels.miner));
		this.addUtil(new u_Hack(this.hacks.HACK_XRAY, this.x2 - 12, this, Base_Panels.xray));
		this.addUtil(new u_Hack(this.hacks.HACK_BRIGHTNESS, this.x2 - 12, this, Base_Panels.brightness));
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
					this.drawRect(this.x + this.x2 + 1, var5.y, this.x + this.x2 + 4 + this.mc.fontRenderer.getStringWidth(var5.description) + 1, var5.y + var5.y2, 0x40000000);
					this.mc.fontRenderer.drawString(var5.description, this.x + this.x2 + 2.5F, var5.y + 1.5F, 0xFFFFFFFF);
				}
				
				this.buttonsSet = true;
			}
		} else {
			this.buttonsSet = false;
		}
	}
}