package se.proxus.mods.list;

import net.minecraft.src.Tessellator;
import se.proxus.events.Event;
import se.proxus.events.player.EventUpdate;
import se.proxus.mods.*;

public class ModBrightness extends BaseMod {

	public float brightness = 0.0F;

	public ModBrightness() {
		super("Brightness", new ModInfo("Makes the world brighter.", "Oliver", "NONE", true), ModType.WORLD, false);
		this.setOption("Brightness_brightness", Float.valueOf(this.brightness), false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void registerEvents() {
		this.getEvent().registerEvent(EventUpdate.class);
	}

	@Override
	public void onEnabled() {

	}

	@Override
	public void onDisabled() {

	}

	@Override
	public void onEvent(Event var0) {
		if(var0 instanceof EventUpdate) {
			if(this.getState() && this.brightness < 10) {
				this.brightness += 0.15F;
				this.setOption("Brightness_brightness", Float.valueOf(this.brightness), false);
			} if(!(this.getState()) && brightness > 0) {
				this.brightness -= 0.15F;
				this.setOption("Brightness_brightness", Float.valueOf(this.brightness), false);
			}
		}
	}
}