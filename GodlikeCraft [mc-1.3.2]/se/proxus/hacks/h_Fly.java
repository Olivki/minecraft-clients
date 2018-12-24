package se.proxus.hacks;

public class h_Fly extends Base_Hack {

	public h_Fly() {
		super('6', "Flight", "Makes you fly.", "Player", "R");
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		this.mc.thePlayer.capabilities.isFlying = false;
	}

	@Override
	public void onToggled() {
		if(this.getState()) {
			this.mc.thePlayer.capabilities.isFlying = true;
		}
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