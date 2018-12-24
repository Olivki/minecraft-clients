package se.proxus.hacks;

public class h_Step extends Base_Hack {

	public h_Step() {
		super('6', "Step", "Makes your step height higher.", "Player", "O");
		this.setOption(Base_Options.STEP_HEIGHT, Float.valueOf(1.5F));
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		this.mc.thePlayer.stepHeight = 0.5F;
	}

	@Override
	public void onToggled() {
		
	}

	@Override
	public void onUpdate() {
		if(this.getState()) {
			if(((Float)getOption(Base_Options.STEP_HEIGHT)).floatValue() == 0) {
				this.mc.thePlayer.stepHeight = 0.5F;
	        } else {
	        	this.mc.thePlayer.stepHeight = ((Float)getOption(Base_Options.STEP_HEIGHT)).floatValue();
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