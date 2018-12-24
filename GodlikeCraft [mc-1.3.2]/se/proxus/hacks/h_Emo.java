package se.proxus.hacks;

import net.minecraft.src.*;

public class h_Emo extends Base_Hack {

	private long CUR_TIME_DELAY = 0L, LAST_ATTACK_MS = 72L, LAST_ATTACK_TIME = -1L;
	private int CUR_AIMBOT_MODE = 2;
	private boolean IS_CRITS_ON = false, IS_AURA_RUNNING = false;

	public h_Emo() {
		super('7', "Emo", "Makes you hit yourself.", "Aura", "NONE");
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
		if(getState()) {
			CUR_TIME_DELAY = System.nanoTime() / 1000000;
			
			if(this.mc.thePlayer.hurtTime <= 0 && (CUR_TIME_DELAY - LAST_ATTACK_TIME >= LAST_ATTACK_MS || LAST_ATTACK_TIME == -1)) {
				this.mc.thePlayer.swingItem();
				mc.playerController.attackEntity(this.mc.thePlayer, this.mc.thePlayer);
				LAST_ATTACK_TIME = System.nanoTime() / 1000000;
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
		return (new long[] {CUR_TIME_DELAY, LAST_ATTACK_TIME});
	}

	@Override
	public boolean[] getModBoolean() {
		return (new boolean[] {});
	}
}