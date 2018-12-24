package se.proxus.hacks;

public class h_NoFall extends Base_Hack {
	
	private boolean IS_FALLING = false;

	public h_NoFall() {
		super('f', "NoFall", "Makes you avoid fall damage.", "Player", "N");
	}

	@Override
	public void onEnabled() {
		
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
		return (new boolean[] {this.IS_FALLING});
	}
}