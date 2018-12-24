package se.proxus.mods.list;

import net.minecraft.src.Tessellator;
import se.proxus.events.Event;
import se.proxus.events.player.EventUpdate;
import se.proxus.mods.*;

public class ModxRay extends BaseMod {

	public float brightness = 0.0F;

	public ModxRay() {
		super("xRay", new ModInfo("Makes you see blocks.", "Oliver", "NONE", true), ModType.WORLD, false);
		this.setOption("xRay_opacity", Integer.valueOf(155), false);
		this.setOption("xRay_brightness", Float.valueOf(this.brightness), false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void registerEvents() {
		this.getEvent().registerEvent(EventUpdate.class);
	}

	@Override
	public void onEnabled() {
		Tessellator.opacity = ((Integer)this.getOption("xRay_opacity")).intValue();
		this.renderBlocks();
	}

	@Override
	public void onDisabled() {
		Tessellator.opacity = 255;
		this.renderBlocks();
	}

	@Override
	public void onEvent(Event var0) {
		if(var0 instanceof EventUpdate) {
			if(this.getState() && this.brightness < 10) {
				this.brightness += 0.15F;
				this.setOption("xRay_brightness", Float.valueOf(this.brightness), true);
			} if(!(this.getState()) && this.brightness > 0) {
				this.brightness -= 0.15F;
				this.setOption("xRay_brightness", Float.valueOf(this.brightness), true);
			}
		}
	}

	private void renderBlocks() {
		int var0 = (int)this.wrapper.getPlayer().posX;
		int var1 = (int)this.wrapper.getPlayer().posY;
		int var2 = (int)this.wrapper.getPlayer().posZ;
		this.wrapper.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(var0 - 200, var1 - 200, var2 - 200, var0 + 200, var1 + 200, var2 + 200);
	}
}