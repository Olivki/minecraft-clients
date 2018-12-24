package se.proxus.hacks;

public class h_Brightness extends Base_Hack {

	public float brightness = 0.0F;

	public h_Brightness() {
		super('e', "Brightness", "Makes your world brighter.", "World", "C");
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
		if(this.getState() && this.brightness < 10) {
			this.brightness += 0.15F;
		} if(!(this.getState()) && brightness > 0) {
			this.brightness -= 0.15F;
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
		return (new float[] {brightness});
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