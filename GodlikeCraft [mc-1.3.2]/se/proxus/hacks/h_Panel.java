package se.proxus.hacks;

import se.proxus.panels.Base_Panels;

public class h_Panel extends Base_Hack {

	public h_Panel() {
		super('2', "Panel", "Makes you open up the panels.", "N/A", "RSHIFT");
	}

	@Override
	public void onEnabled() {
		this.ingame.guiArray.remove(this.ingame.guiArray.indexOf("§" + this.getCol() + this.getName().replace("_",  " ")));
		this.mc.displayGuiScreen(new Base_Panels());
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