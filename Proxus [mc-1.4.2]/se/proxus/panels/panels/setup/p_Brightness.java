package se.proxus.panels.panels.setup;

import org.lwjgl.opengl.GL11;

import se.proxus.hacks.*;
import se.proxus.panels.*;
import se.proxus.panels.utils.*;

public class p_Brightness extends Base_Panel {
	
	public u_Textfield
	colour = null;

	public p_Brightness() {
		super("Brightness setup", 1, 1);

		this.addUtil(colour = new u_Textfield("Colour", "" + this.hacks.HACK_XRAY.getCol(), this.x2 - 4, this, false, 0, "Changes the hacks colour."));
		this.addUtil(new u_Slider("Brightness", this.hacks.HACK_BRIGHTNESS, Base_Options.BRIGHTNESS_LEVEL, 20D, this.x2 - 4));
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
	
	@Override
	public void onEntered(String var1, String var2, u_Textfield var3) {
		if(var3 == this.colour) {
			this.hacks.HACK_XRAY.setColor(this.colour.text.charAt(0));
			this.files.saveHackConfig();
		}
	}
}