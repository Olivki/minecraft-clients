package se.proxus.hacks;

public class h_Miner extends Base_Hack {

	public h_Miner() {
		super('c', "Speedy Gonzales", "Makes you mine faster.", "World", "M");
		this.setOption(Base_Options.MINING_DELAY, Integer.valueOf(5));
		this.setOption(Base_Options.MINE_SPEED, Float.valueOf(0.3F));
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
		return (new boolean[] {});
	}
}