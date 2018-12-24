package se.proxus.hacks;

public class h_NoCheat extends Base_Hack {

	public h_NoCheat() {
		super('c', "NoCheat", "Makes it so certain hacks will change to play along with NoCheat.", "Server", "NONE");
	}

	@Override
	public void onEnabled() {
		this.utils.log("[Hacks] NoCheat mode activated, changing how certain hacks behave.");
	}

	@Override
	public void onDisabled() {
		this.utils.log("[Hacks] NoCheat mode deactivated, going to back to normal behavior.");
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