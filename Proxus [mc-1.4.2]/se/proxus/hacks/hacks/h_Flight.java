package se.proxus.hacks.hacks;

import se.proxus.hacks.*;

public class h_Flight extends Base_Hack {
	
	public float 
	CUR_BRIGHT = 0.0F;
	
	public h_Flight() {
		super('7', "xRay", "Makes you see valuable blocks.", "NONE", "World", true);
		this.hacks.addHack(this);
	}

	@Override
	public void onEnabled() {
		this.updateRenders();
	}

	@Override
	public void onDisabled() {
		this.updateRenders();
	}

	@Override
	public void onUpdate() {
		if(this.getState() && this.CUR_BRIGHT < 10) {
			this.CUR_BRIGHT += 0.15F;
		} if(!(this.getState()) && CUR_BRIGHT > 0) {
			this.CUR_BRIGHT -= 0.15F;
		}
	}
	
    public void updateRenders() {
        int var1 = (int)mc.thePlayer.posX, var2 = (int)mc.thePlayer.posY, var3 = (int)mc.thePlayer.posZ;
        this.mc.renderGlobal.markBlockRangeNeedsUpdate(var1 - 200, var2 - 200, var3 - 200, var1 + 200, var2 + 200, var3 + 200);
    }
}