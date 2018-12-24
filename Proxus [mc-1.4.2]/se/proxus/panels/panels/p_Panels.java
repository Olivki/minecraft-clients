package se.proxus.panels.panels;

import org.lwjgl.opengl.GL11;

import se.proxus.hacks.*;
import se.proxus.panels.*;
import se.proxus.panels.utils.*;

public class p_Panels extends Base_Panel {

	public p_Panels() {
		super("Panels", 1, 1);

		for(Base_Panel var1 : Base_Panels.panelBeforeArray) {
			this.addUtil(new u_Checkbox(var1, this.x2 - 4));
		}
	}

	@Override
	public void draw(int var1, int var2, boolean var3) {
		super.draw(var1, var2, false);

		this.rectY = this.utilArray.size() * 12 + this.utilArray.size();

		if(this.open) {
			for(int var4 = 0; var4 < this.utilArray.size(); var4++) {
				Base_Util var5 = (Base_Util)this.utilArray.get(var4);

				var5.setPosition(this.x + 2, this.y + 1 + this.y2 + var4 * var5.panelSpacing);
				Base_Panels.startCut(this.x, this.y + this.y2, this.x + this.x2, this.y + this.y2 + this.rectY);
				var5.draw(var1, var2, this.scrolled);
				Base_Panels.stopCut();
				this.buttonsSet = true;
			}
		} else {
			this.buttonsSet = false;
		}
	}
}