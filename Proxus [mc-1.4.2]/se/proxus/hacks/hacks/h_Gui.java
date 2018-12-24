package se.proxus.hacks.hacks;

import se.proxus.hacks.*;

public class h_Gui extends Base_Hack {

	public h_Gui() {
		super('f', "Gui", "Renders the Gui.", "NONE", "Gui", true);
		this.hacks.addHack(this);
	}

	@Override
	public void onEnabled() {
		this.vars.GUI_ARRAY.remove(this.vars.GUI_ARRAY.indexOf("§" + this.getCol() + this.getName().replace("_",  " ")));
	}

	@Override
	public void onDisabled() {
		
	}

	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public void onRendered2D(int var1, int var2) {
		if(this.getState()) {
			this.ingame.drawMain(this.vars.GUI_ARRAY, this.vars.CLIENT_NAME + " " + this.vars.CLIENT_VERSION, this.mc.fontRenderer, var1, var2);
		}
	}
}