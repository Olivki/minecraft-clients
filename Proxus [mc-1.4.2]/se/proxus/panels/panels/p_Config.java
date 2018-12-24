package se.proxus.panels.panels;

import org.lwjgl.opengl.GL11;

import se.proxus.hacks.*;
import se.proxus.panels.*;
import se.proxus.panels.utils.*;

public class p_Config extends Base_Panel {
	
	public u_Textfield
	NAME = null,
	VERSION = null;

	public p_Config() {
		super("Config", 1, 1);

		this.addUtil(NAME = new u_Textfield("Name", this.vars.CLIENT_NAME, this.x2 - 4, this, false));
		this.addUtil(VERSION = new u_Textfield("Version", this.vars.CLIENT_VERSION, this.x2 - 4, this, false));
		this.addUtil(new u_Button("Open config.", false, this.x2 - 4, this, 0));
		this.addUtil(new u_Button("Load config.", false, this.x2 - 4, this, 1));
		this.addUtil(new u_Button("Save config.", false, this.x2 - 4, this, 2));
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
				this.buttonsSet = true;
			}
		} else {
			this.buttonsSet = false;
		}
	}
	
	@Override
	public void onEntered(String var1, String var2, u_Textfield var3) {
		if(var3 == this.NAME) {
			this.vars.CLIENT_NAME = var2;
			this.files.saveConfig();
		} if(var3 == this.VERSION) {
			this.vars.CLIENT_VERSION = var2;
			this.files.saveConfig();
		}
	}
	
	@Override
	public void buttonPressed(u_Button var1, int var2) {
		switch(var1.id) {
		case 0 :
			this.utils.openFile(this.files.config.getPath());
			break;
			
		case 1 :
			this.files.loadConfig();
			break;
			
		case 2 :
			this.files.saveConfig();
			break;
		}
	}
}