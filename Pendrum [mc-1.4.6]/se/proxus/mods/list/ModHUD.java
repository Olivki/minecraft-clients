package se.proxus.mods.list;

import net.minecraft.src.Tessellator;
import se.proxus.mods.*;

public class ModHUD extends BaseMod {
	
	public float brightness = 0.0F;

	public ModHUD() {
		super("HUD", new ModInfo("Renders a special HUD.", "Oliver", "NONE", true), ModType.GUI, true);
		this.getInfo().setMod(this);
		this.setState(true, false);
		this.getConfig().loadConfig();
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		
	}
}