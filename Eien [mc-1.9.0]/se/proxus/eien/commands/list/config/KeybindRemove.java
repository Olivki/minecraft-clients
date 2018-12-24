package se.proxus.eien.commands.list.config;

import se.proxus.eien.commands.Command;
import se.proxus.eien.commands.CommandType;
import se.proxus.eien.mods.Mod;
import se.proxus.eien.mods.ModManager;
import se.proxus.eien.tools.Colours;
import se.proxus.eien.tools.Tools;

public class KeybindRemove extends Command {

	public KeybindRemove() {
		super("keybind", ".keybind <remove> [mod name]", "Removes the key of the selected mod.",
				CommandType.CONFIG);
	}

	@Override
	public void onCommand(final String message, final String... args) {
		if (args[0].equalsIgnoreCase("remove")) {
			Mod mod = ModManager.getInstance().getModByShortenedName(args[1]);
			mod.setValue("Keybind", "NONE", true);
			Tools.addChatMessage("Bound '" + mod.getName() + "' to '" + Colours.CLIENT_COLOUR
					+ "NONE" + Colours.GREY + "'");
		}
	}
}