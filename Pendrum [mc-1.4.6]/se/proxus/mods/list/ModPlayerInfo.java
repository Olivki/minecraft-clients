package se.proxus.mods.list;

import net.minecraft.src.Tessellator;
import se.proxus.mods.*;

public class ModPlayerInfo extends BaseMod {

	public ModPlayerInfo() {
		super("Player_Info", new ModInfo("Makes you see other peoples gear.", "Oliver", "NONE", true), ModType.RENDER, false);
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