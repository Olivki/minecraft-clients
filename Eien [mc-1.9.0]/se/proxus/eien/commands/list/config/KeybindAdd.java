package se.proxus.eien.commands.list.config;

import se.proxus.eien.commands.Command;
import se.proxus.eien.commands.CommandType;
import se.proxus.eien.mods.Mod;
import se.proxus.eien.mods.ModManager;
import se.proxus.eien.tools.Colours;
import se.proxus.eien.tools.Tools;

public class KeybindAdd extends Command {

	public KeybindAdd() {
		super("keybind", ".keybind <add> [mod name] <key>", "Binds the key of the selected mod.",
				CommandType.CONFIG);
	}

	@Override
	public void onCommand(final String message, final String... args) {
		// Make it so that you can't have the same bind as any other mod, k
		if (args[0].equalsIgnoreCase("add")) {
			Mod mod = ModManager.getInstance().getModByShortenedName(args[1]);
			mod.setValue("Keybind", args[2].toUpperCase(), true);
			Tools.addChatMessage("Bound '" + Colours.CLIENT_COLOUR + mod.getName() + Colours.GREY
					+ "' to '" + Colours.CLIENT_COLOUR + args[2].toUpperCase() + Colours.GREY + "'");
		}
	}
}