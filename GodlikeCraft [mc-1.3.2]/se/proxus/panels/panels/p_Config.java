package se.proxus.panels.panels;

import org.lwjgl.opengl.GL11;

import se.proxus.hacks.*;
import se.proxus.panels.*;
import se.proxus.panels.utils.*;

public class p_Config extends Base_Panel {

	public p_Config() {
		super("Config", 1, 1);
		
		this.addUtil(new u_Slider("Opacity", this.hacks.xray, Base_Options.XRAY_OPACITY, 256D, this.x2 - 6));
		this.addUtil(new u_Slider("Step height", this.hacks.step, Base_Options.STEP_HEIGHT, 8D, this.x2 - 6));
	}

	@Override
	public void draw(int var1, int var2) {
		super.draw(var1, var2);
		
		if(this.open) {
			for(int var3 = 0; var3 < this.utilArray.size(); var3++) {
				Base_Util var4 = (Base_Util)this.utilArray.get(var3);
				
				var4.setPosition(this.x + 2, this.y + this.y2 + 9 + var3 * var4.panelSpacing);
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