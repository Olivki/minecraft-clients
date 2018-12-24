package se.proxus.panels.panels;

import org.lwjgl.opengl.GL11;

import se.proxus.hacks.*;
import se.proxus.panels.*;
import se.proxus.panels.utils.*;

public class p_Aura extends Base_Panel {

	public p_Aura() {
		super("Aura", 1, 1);

		for(Base_Hack var1 : this.hacks.hackArray) {
			if(var1.getSection().equalsIgnoreCase(this.name)) {
				this.addUtil(new u_Hack(var1));
			}
		}
	}

	@Override
	public void draw(int var1, int var2) {
		super.draw(var1, var2);
		
		if(this.open) {
			for(int var3 = 0; var3 < this.utilArray.size(); var3++) {
				Base_Util var4 = (Base_Util)this.utilArray.get(var3);
				
				var4.setPosition(this.x + 2, this.y + this.y2 + 1 + var3 * var4.y2);
				Base_Panels.startCut(this.x, this.y + this.y2, this.x + this.x2, this.y + this.y2 + this.rectY);
				var4.draw(var1, var2, this.scrolled);
				Base_Panels.stopCut();
				this.buttonsSet = true;
			}
		} else {
			this.buttonsSet = false;
		}
	}
}