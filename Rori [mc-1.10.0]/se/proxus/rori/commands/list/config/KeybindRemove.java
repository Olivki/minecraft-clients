package se.proxus.rori.commands.list.config;

import se.proxus.rori.commands.Command;
import se.proxus.rori.commands.CommandType;
import se.proxus.rori.mods.Mod;
import se.proxus.rori.mods.ModManager;
import se.proxus.rori.tools.Colours;
import se.proxus.rori.tools.Tools;

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