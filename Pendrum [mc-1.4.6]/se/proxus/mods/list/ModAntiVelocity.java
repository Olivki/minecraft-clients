package se.proxus.mods.list;

import net.minecraft.src.MathHelper;
import se.proxus.mods.*;

public class ModAntiVelocity extends BaseMod {

	public ModAntiVelocity() {
		super("Anti_Velocity", new ModInfo("Prevents you from getting knocked back.", "Oliver", "NONE", true), ModType.COMBAT, false);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void onEnabled() {
		
	}

	@Override
	public void onDisabled() {
		
	}
}