package se.proxus.hacks;

import java.util.Random;

public class h_Flood extends Base_Hack {

	private long CURRENT_MS = 1000L, LAST_MSG = -1L, CUR_TRESHOLD = 0L;

	public h_Flood() {
		super('2', "Flood", "Makes you flood.", "Server", "NONE");
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
			CUR_TRESHOLD = System.nanoTime() / 1000000;

			if (CUR_TRESHOLD - LAST_MSG >= CURRENT_MS || LAST_MSG == -1) {
				if(!this.vars.spamArray.isEmpty()) {
					this.utils.sendChat(this.vars.spamArray.get((new Random()).nextInt(this.vars.spamArray.size())) + (new Random()).nextInt(100));
				}
				
				LAST_MSG = System.nanoTime() / 1000000;
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