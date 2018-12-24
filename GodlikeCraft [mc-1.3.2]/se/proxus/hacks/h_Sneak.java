package se.proxus.hacks;

public class h_Sneak extends Base_Hack {

	public h_Sneak() {
		super('2', "Sneak", "Makes you sneak.", "Player", "Z");
	}

	@Override
	public void onEnabled() {
		mc.thePlayer.setSneaking(true);
	}

	@Override
	public void onDisabled() {
		mc.thePlayer.setSneaking(false);
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