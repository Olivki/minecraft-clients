package se.proxus.hacks.hacks;

import se.proxus.hacks.*;
import se.proxus.panels.*;

public class h_Panels extends Base_Hack {

	public h_Panels() {
		super('f', "Panels", "Opens the panels.", "RSHIFT", "N/A", true);
		this.hacks.addHack(this);
	}

	@Override
	public void onEnabled() {
		this.mc.displayGuiScreen(new Base_Panels());
		this.vars.GUI_ARRAY.remove(this.vars.GUI_ARRAY.indexOf("§" + this.getCol() + this.getName().replace("_",  " ")));
		this.toggle();
	}

	@Override
	public void onDisabled() {
		
	}

	@Override
	public void onUpdate() {
		
	}
}