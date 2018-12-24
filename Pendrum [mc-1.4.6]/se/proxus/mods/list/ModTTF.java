package se.proxus.mods.list;

import net.minecraft.src.Tessellator;
import se.proxus.mods.*;

public class ModTTF extends BaseMod {

	public ModTTF() {
		super("TTF", new ModInfo("Renders the TTF in the chat.", "Oliver", "NONE", true), ModType.GUI, true);
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