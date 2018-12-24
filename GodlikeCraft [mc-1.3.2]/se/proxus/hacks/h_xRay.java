package se.proxus.hacks;

import net.minecraft.src.Tessellator;

public class h_xRay extends Base_Hack {

	public float brightness = 0.0F;

	public h_xRay() {
		super('7', "xRay", "Makes blocks transparent.", "World", "X");
		this.setOption(Base_Options.XRAY_OPACITY, Integer.valueOf(this.vars.opacity));
	}

	@Override
	public void onEnabled() {
		Tessellator.opacity = ((Integer)getOption(Base_Options.XRAY_OPACITY)).intValue();
		
		//this.mc.renderGlobal.loadRenderers();
		this.updateRenders();
	}

	@Override
	public void onDisabled() {
		Tessellator.opacity = 255;
		//this.mc.renderGlobal.loadRenderers();
		this.updateRenders();
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
	
    public void updateRenders() {
        int var1 = (int)mc.thePlayer.posX, var2 = (int)mc.thePlayer.posY, var3 = (int)mc.thePlayer.posZ;
        mc.renderGlobal.markBlockRangeNeedsUpdate(var1 - 200, var2 - 200, var3 - 200, var1 + 200, var2 + 200, var3 + 200);
    }
}