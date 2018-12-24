package se.proxus.hacks;

public class h_Sprint extends Base_Hack {

	public h_Sprint() {
		super('c', "Sprint", "Makes you sprint.", "Player", "NONE");
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
		if(this.getState()) {
			if((this.mc.thePlayer.movementInput.moveForward > 0.0F) && !(this.mc.thePlayer.isSneaking())) {
				this.mc.thePlayer.setSprinting(true);
			}
		}
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