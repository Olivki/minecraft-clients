package se.proxus.hacks;

import se.proxus.gui.screens.Console;
import se.proxus.panels.Base_Panels;

public class h_Console extends Base_Hack {

	public h_Console() {
		super('2', "Console", "Makes you open up the console.", "N/A", "RCONTROL");
	}

	@Override
	public void onEnabled() {
		this.ingame.guiArray.remove(this.ingame.guiArray.indexOf("§" + this.getCol() + this.getName().replace("_",  " ")));
		this.mc.displayGuiScreen(new Console());
		this.console.text = "";
		this.toggle();
	}

	@Override
	public void onDisabled() {
		
	}

	@Override
	public void onToggled() {
		
	}

	@Override
	public void onUpdate() {
		
	}
	
	@Override
	public String[] getModString() {
		return (new String[] {});
	}

	@Override
	public int[] getModInteger() {
		return (new int[] {});
	}

	@Override
	public float[] getModFloat() {
		return (new float[] {});
	}
	
	@Override
	public long[] getModLong() {
		return (new long[] {});
	}
	
	@Override
	public boolean[] getModBoolean() {
		return (new boolean[] {});
	}
}