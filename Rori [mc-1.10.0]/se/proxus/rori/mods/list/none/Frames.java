package se.proxus.rori.mods.list.none;

import se.proxus.rori.frames.FrameManager;
import se.proxus.rori.mods.Mod;
import se.proxus.rori.mods.ModCategory;

public class Frames extends Mod {

	public Frames() {
		super("Frames", ModCategory.NONE, true);
	}

	@Override
	public void init() {
		setValue("Description", "Opens the frames, cool stuff.", false);
		setValue("Keybind", "RSHIFT", false);
	}

	@Override
	public void onEnable() {
		getClient().getMinecraft().displayGuiScreen(FrameManager.getInstance());
		setValue("State", false, false);
		checkState();
	}

	@Override
	public void onDisable() {

	}
}