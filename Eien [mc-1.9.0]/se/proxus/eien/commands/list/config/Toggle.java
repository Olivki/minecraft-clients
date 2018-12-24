package se.proxus.eien.commands.list.config;

import se.proxus.eien.commands.Command;
import se.proxus.eien.commands.CommandType;
import se.proxus.eien.mods.Mod;
import se.proxus.eien.mods.Mod.ModController;
import se.proxus.eien.mods.ModManager;
import se.proxus.eien.tools.Tools;

public class Toggle extends Command {

	public Toggle() {
		super("toggle", ".toggle [mod name]", "Toggles the selected mod.", CommandType.CONFIG);
	}

	@Override
	public void onCommand(final String message, final String... args) {
		Mod mod = ModManager.getInstance().getMod(args[0]);

		if (mod.getController().equals(ModController.TOGGLE)) {
			mod.toggle();
			Tools.addChatMessage(mod.getName() + " is now "
					+ (mod.getState() ? "enabled." : "disabled."));
		} else {
			Tools.addChatMessage("The mod doesn't have a valid controller.");
		}
	}
}