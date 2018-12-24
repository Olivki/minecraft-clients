package se.proxus.hacks;

import net.minecraft.src.Packet3Chat;

public class h_KitPvP_Mode extends Base_Hack {

	private long CURRENT_MS = 3000L, LAST_SOUP = -1L, CUR_TRESHOLD = 0L;
	
	public h_KitPvP_Mode() {
		super('e', "KitPvP_mode", "Makes you send the chat for a new kit.", "Aura", "NONE");
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
			
			if (CUR_TRESHOLD - LAST_SOUP >= CURRENT_MS || LAST_SOUP == -1) {
				mc.getSendQueue().addToSendQueue(new Packet3Chat(getModString()[0]));
				LAST_SOUP = System.nanoTime() / 1000000;
			}
		}
	}
	
	@Override
	public String[] getModString() {
		return (new String[] {this.vars.kitPvPString});
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
		return (new long[] {CURRENT_MS, LAST_SOUP, CUR_TRESHOLD});
	}
	
	@Override
	public boolean[] getModBoolean() {
		return (new boolean[] {});
	}
}