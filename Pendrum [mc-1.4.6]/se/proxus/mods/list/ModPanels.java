package se.proxus.mods.list;

import se.proxus.mods.*;
import se.proxus.panels.*;

public class ModPanels extends BaseMod {

	public ModPanels() {
		super("Panels", new ModInfo("Opens the panels.", "Oliver", "RSHIFT", true), ModType.NONE, true);
		this.getInfo().setMod(this);
		this.getConfig().loadConfig();
	}

	@Override
	public void onEnabled() {
		this.wrapper.getMinecraft().displayGuiScreen(this.panels);
		this.toggle();
	}

	@Override
	public void onDisabled() {
		
	}
}